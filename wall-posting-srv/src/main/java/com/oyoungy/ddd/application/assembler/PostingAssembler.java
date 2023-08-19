package com.oyoungy.ddd.application.assembler;

import com.oyoungy.ddd.application.command.AddCategoryCommand;
import com.oyoungy.ddd.application.command.AddPostingCommand;
import com.oyoungy.ddd.application.dto.CategoryDTO;
import com.oyoungy.ddd.application.dto.PostingBriefDTO;
import com.oyoungy.ddd.application.dto.PostingStateDTO;
import com.oyoungy.ddd.application.dto.PostingQueryDTO;
import com.oyoungy.ddd.domain.entity.Category;
import com.oyoungy.ddd.domain.entity.Posting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostingAssembler {
    PostingAssembler INSTANCE = Mappers.getMapper(PostingAssembler.class);

    @Mapping(source = "id.id", target = "id")
    @Mapping(source = "state.msg", target = "state")
    CategoryDTO toCategoryDTO(Category category);

    Category toCategory(AddCategoryCommand addCategoryCommand);

    @Mapping(source = "id.id", target = "id")
    PostingBriefDTO toPostingBriefDTO(Posting posting);

    @Mapping(source = "categoryId", target = "categoryId.id")
    Posting toPosting(AddPostingCommand addPostingCommand);

    @Mapping(source = "id.id", target = "id")
    @Mapping(source = "state.msg", target = "state")
    @Mapping(source = "categoryId.id", target = "categoryId")
    PostingStateDTO toPostingStateDTO(Posting posting);

    @Mapping(source = "id.id", target = "id")
    @Mapping(source = "categoryId.id", target = "categoryId")
    PostingQueryDTO toPostingQueryDTO(Posting posting);
}
