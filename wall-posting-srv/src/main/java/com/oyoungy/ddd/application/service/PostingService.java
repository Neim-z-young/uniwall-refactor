package com.oyoungy.ddd.application.service;

import com.oyoungy.ddd.application.assembler.PostingAssembler;
import com.oyoungy.ddd.application.command.AddPostingCommand;
import com.oyoungy.ddd.application.dto.PageDTO;
import com.oyoungy.ddd.application.dto.PostingBriefDTO;
import com.oyoungy.ddd.application.dto.PostingQueryDTO;
import com.oyoungy.ddd.application.dto.PostingStateDTO;
import com.oyoungy.ddd.application.event.CategoryApprovingEvent;
import com.oyoungy.ddd.application.event.PostingApprovedEvent;
import com.oyoungy.ddd.application.event.PostingApprovingEvent;
import com.oyoungy.ddd.application.query.PageQuery;
import com.oyoungy.ddd.domain.entity.Posting;
import com.oyoungy.ddd.domain.repository.PostingRepository;
import com.oyoungy.ddd.domain.vo.PostingId;
import com.oyoungy.enums.OperationEnum;
import com.oyoungy.enums.StateEnum;
import com.oyoungy.exceptions.WallNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@Slf4j
public class PostingService {
    private PostingAssembler assembler = PostingAssembler.INSTANCE;

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private StreamBridge streamBridge;

    public PostingQueryDTO queryPostingById(BigInteger id) throws WallNotFoundException {
        PostingId postingId = new PostingId();
        postingId.setId(id);
        return postingRepository.findOne(postingId).map(assembler::toPostingQueryDTO).
                orElseThrow(()-> new WallNotFoundException(MessageFormat.format("帖子{0}不存在", id)));
    }

    public PageDTO<PostingBriefDTO, BigInteger> queryPostingByCategory(Long categoryId, PageQuery<BigInteger> pageQuery){
        PostingId bound = new PostingId();
        List<PostingBriefDTO> postingBriefs = new ArrayList<>();
        BigInteger lowerBound = null;
        BigInteger upperBound = null;
        if(pageQuery.getLowerBound() != null){
            bound.setId(pageQuery.getLowerBound());
            lowerBound = pageQuery.getLowerBound();
            upperBound = lowerBound;

            for(Posting c : postingRepository.findNextPage(bound, pageQuery.getPageSize())){
                PostingBriefDTO postingBriefDTO = assembler.toPostingBriefDTO(c);
                if(postingBriefDTO.getId().compareTo(upperBound) > 0){
                    upperBound = postingBriefDTO.getId();
                }
                postingBriefs.add(postingBriefDTO);
            }
        }else{
            bound.setId(pageQuery.getUpperBound());
            lowerBound = pageQuery.getUpperBound();
            upperBound = lowerBound;

            for(Posting c : postingRepository.findPrevPage(bound, pageQuery.getPageSize())){
                PostingBriefDTO postingBriefDTO = assembler.toPostingBriefDTO(c);
                if(postingBriefDTO.getId().compareTo(lowerBound) < 0){
                    lowerBound = postingBriefDTO.getId();
                }
                postingBriefs.add(postingBriefDTO);
            }
        }

        PageDTO<PostingBriefDTO, BigInteger> pageDTO = new PageDTO<>();
        pageDTO.setObjects(postingBriefs);
        pageDTO.setPageSize(pageQuery.getPageSize());
        pageDTO.setLowerBound(lowerBound);
        pageDTO.setUpperBound(upperBound);
        return pageDTO;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PostingStateDTO addPosting(AddPostingCommand addPostingCommand){
        Posting posting = assembler.toPosting(addPostingCommand);
        posting.init();
        posting = postingRepository.save(posting);

        PostingApprovingEvent postingAddingEvent = new PostingApprovingEvent();
        postingAddingEvent.setApprovingMsg("待创建");
        postingAddingEvent.setPostingId(posting.getId().getId());
        postingAddingEvent.setCategoryId(posting.getCategoryId().getId());
        postingAddingEvent.setOperation(OperationEnum.CREATE.getMsg());
        postingAddingEvent.setState(StateEnum.APPROVING.getMsg());
        postingAddingEvent.setApprovingUserId(null);
        streamBridge.send("posting-event", postingAddingEvent);
        return assembler.toPostingStateDTO(posting);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void deletePosting(BigInteger id) throws WallNotFoundException {
        PostingId postingId = new PostingId();
        postingId.setId(id);
        Optional<Posting> postingOp = postingRepository.findOne(postingId);
        Posting posting = postingOp.
                orElseThrow(() -> new WallNotFoundException(MessageFormat.format("帖子id:{0}不存在", id)));
        posting.approving();
        postingRepository.updateState(posting);
        PostingApprovingEvent postingDeletingEvent = new PostingApprovingEvent();
        postingDeletingEvent.setApprovingMsg("待删除");
        postingDeletingEvent.setPostingId(posting.getId().getId());
        postingDeletingEvent.setCategoryId(posting.getCategoryId().getId());
        postingDeletingEvent.setOperation(OperationEnum.DELETE.getMsg());
        postingDeletingEvent.setState(StateEnum.APPROVING.getMsg());
        postingDeletingEvent.setApprovingUserId(null);
        log.info("send posting deleting event");
        streamBridge.send("posting-event", postingDeletingEvent);
    }

    @Bean
    public Consumer<PostingApprovingEvent> approvingPosting(){
        return event -> {
            log.info("received posting approving event:{}", event);
        };
    }

    @Bean
    public Consumer<PostingApprovedEvent> approvedPosting(){
        return event -> {
            log.info("received event:{}", event);
            try {
                approved(event);
            } catch (WallNotFoundException e) {
                log.error("EventHandleError:{}", e.getMessage());
            }
        };
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void approved(PostingApprovedEvent event) throws WallNotFoundException {
        PostingId postingId = new PostingId();
        postingId.setId(event.getPostingId());
        Optional<Posting> postingOp = postingRepository.findOne(postingId);
        Posting posting = postingOp.
                orElseThrow(() -> new WallNotFoundException(MessageFormat.format("帖子id:{0}不存在", postingId.getId())));
        if(!posting.getState().equals(StateEnum.APPROVING)){
            log.info("expired event:{}", event);
            return;
        }
        OperationEnum operationEnum = OperationEnum.of(event.getOperation());
        if(operationEnum.equals(OperationEnum.DELETE)){
            posting.deleted();
        }else if(operationEnum.equals(OperationEnum.CREATE)){
            posting.created();
        }
        if(StateEnum.of(event.getState()).equals(StateEnum.DENIED)){
            posting.approvedFailed();
        }
        postingRepository.updateState(posting);
    }
}
