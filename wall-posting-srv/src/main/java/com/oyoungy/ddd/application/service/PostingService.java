package com.oyoungy.ddd.application.service;

import com.oyoungy.ddd.application.assembler.PostingAssembler;
import com.oyoungy.ddd.application.command.AddPostingCommand;
import com.oyoungy.ddd.application.dto.PageDTO;
import com.oyoungy.ddd.application.dto.PostingBriefDTO;
import com.oyoungy.ddd.application.dto.PostingQueryDTO;
import com.oyoungy.ddd.application.dto.PostingStateDTO;
import com.oyoungy.ddd.application.query.PageQuery;
import com.oyoungy.ddd.domain.entity.Posting;
import com.oyoungy.ddd.domain.repository.PostingRepository;
import com.oyoungy.ddd.domain.vo.PostingId;
import com.oyoungy.exceptions.WallNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostingService {
    private PostingAssembler assembler = PostingAssembler.INSTANCE;

    @Autowired
    private PostingRepository postingRepository;

    public PostingQueryDTO queryPostingById(BigInteger id){
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
        return assembler.toPostingStateDTO(posting);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void deletePosting(BigInteger id){
        PostingId postingId = new PostingId();
        postingId.setId(id);
        Optional<Posting> postingOp = postingRepository.findOne(postingId);
        Posting posting = postingOp.
                orElseThrow(() -> new WallNotFoundException(MessageFormat.format("帖子id:{0}不存在", id)));
        posting.delete();
        postingRepository.updateState(posting);
        //TODO send event;
    }
}
