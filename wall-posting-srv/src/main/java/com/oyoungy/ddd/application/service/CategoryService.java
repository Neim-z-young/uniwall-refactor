package com.oyoungy.ddd.application.service;

import com.oyoungy.ddd.application.assembler.PostingAssembler;
import com.oyoungy.ddd.application.command.AddCategoryCommand;
import com.oyoungy.ddd.application.dto.CategoryDTO;
import com.oyoungy.ddd.application.dto.PageDTO;
import com.oyoungy.ddd.application.event.CategoryApprovingEvent;
import com.oyoungy.ddd.application.query.PageQuery;
import com.oyoungy.ddd.domain.entity.Category;
import com.oyoungy.ddd.domain.repository.CategoryRepository;
import com.oyoungy.ddd.domain.vo.CategoryId;
import com.oyoungy.enums.OperationEnum;
import com.oyoungy.enums.StateEnum;
import com.oyoungy.exceptions.WallNotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class CategoryService {
    private PostingAssembler assembler = PostingAssembler.INSTANCE;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StreamBridge streamBridge;

    public PageDTO<CategoryDTO, Long> queryCategory(PageQuery<Long> pageQuery){
        CategoryId bound = new CategoryId();
        List<CategoryDTO> categories = new ArrayList<>();
        Long lowerBound = null;
        Long upperBound = null;
        if(pageQuery.getLowerBound() != null){
            bound.setId(pageQuery.getLowerBound());
            lowerBound = pageQuery.getLowerBound();
            upperBound = lowerBound;

            for(Category c : categoryRepository.findNextPage(bound, pageQuery.getPageSize())){
                CategoryDTO categoryDTO = assembler.toCategoryDTO(c);
                if(categoryDTO.getId() > upperBound){
                    upperBound = categoryDTO.getId();
                }
                categories.add(categoryDTO);
            }
        }else{
            bound.setId(pageQuery.getUpperBound());
            lowerBound = pageQuery.getUpperBound();
            upperBound = lowerBound;

            for(Category c : categoryRepository.findPrevPage(bound, pageQuery.getPageSize())){
                CategoryDTO categoryDTO = assembler.toCategoryDTO(c);
                if(categoryDTO.getId() < lowerBound){
                    lowerBound = categoryDTO.getId();
                }
                categories.add(categoryDTO);
            }
        }


        PageDTO<CategoryDTO, Long> pageDTO = new PageDTO<>();
        pageDTO.setObjects(categories);
        pageDTO.setPageSize(pageQuery.getPageSize());
        pageDTO.setLowerBound(lowerBound);
        pageDTO.setUpperBound(upperBound);
        return pageDTO;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public CategoryDTO addCategory(AddCategoryCommand addCategoryCommand){
        Category category = assembler.toCategory(addCategoryCommand);
        category.init();
        category = categoryRepository.save(category);

        CategoryApprovingEvent categoryAddingEvent = new CategoryApprovingEvent();
        categoryAddingEvent.setApprovingMsg("待创建");
        categoryAddingEvent.setCategory(category.getCategory());
        categoryAddingEvent.setOperation(OperationEnum.CREATE.getMsg());
        categoryAddingEvent.setState(StateEnum.APPROVING.getMsg());
        categoryAddingEvent.setCategoryId(category.getId().getId());
        categoryAddingEvent.setApprovingUserId(null);
        streamBridge.send("category-event", categoryAddingEvent);
        return assembler.toCategoryDTO(category);
    }


    public void deleteCategory(Long id){
        CategoryId categoryId = new CategoryId();
        categoryId.setId(id);
        Optional<Category> categoryOp = categoryRepository.findOne(categoryId);
        Category category = categoryOp.
                orElseThrow(() -> new WallNotFoundException(MessageFormat.format("类别id:{0}不存在", id)));
        category.approving();
        categoryRepository.updateState(category);
        CategoryApprovingEvent categoryDeletingEvent = new CategoryApprovingEvent();
        categoryDeletingEvent.setApprovingMsg("待删除");
        categoryDeletingEvent.setCategory(category.getCategory());
        categoryDeletingEvent.setOperation(OperationEnum.DELETE.getMsg());
        categoryDeletingEvent.setState(StateEnum.APPROVING.getMsg());
        categoryDeletingEvent.setCategoryId(category.getId().getId());
        categoryDeletingEvent.setApprovingUserId(null);
        log.info("send deleting event");
        streamBridge.send("category-event", categoryDeletingEvent);
    }

    @Bean
    public Consumer<CategoryApprovingEvent> approvingCategory(){
        return event -> {
            log.info("received event:{}", event);
            approved(event);
        };
    }

    @SneakyThrows
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void approved(CategoryApprovingEvent event){
        CategoryId categoryId = new CategoryId();
        categoryId.setId(event.getCategoryId());
        Optional<Category> categoryOp = categoryRepository.findOne(categoryId);
        Category category = categoryOp.
                orElseThrow(() -> new WallNotFoundException(MessageFormat.format("类别id:{0}不存在", categoryId.getId())));
        if(!category.getState().equals(StateEnum.APPROVING)){
            log.info("expired event:{}", event);
            return;
        }
        OperationEnum operationEnum = OperationEnum.of(event.getOperation());
        if(operationEnum.equals(OperationEnum.DELETE)){
            category.deleted();
        }else if(operationEnum.equals(OperationEnum.CREATE)){
            category.created();
        }
        categoryRepository.updateState(category);
    }
}
