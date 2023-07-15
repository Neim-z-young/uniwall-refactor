package com.oyoungy.ddd.infra.convertor;

import com.oyoungy.ddd.domain.entity.Category;
import com.oyoungy.ddd.domain.vo.PostingId;
import com.oyoungy.ddd.infra.database.CategoryDO;
import com.oyoungy.util.ConvertUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Mapper(uses = ConvertUtils.class)
public interface DoConverter {
    DoConverter INSTANCE = Mappers.getMapper(DoConverter.class);

    @Mapping(source = "id", target = "id.id")
    @Mapping(source = "topPostings", target = "topPostings", qualifiedByName = "stringToPostings")
    Category toCategory(CategoryDO categoryDO);

    @Mapping(source = "id.id", target = "id")
    @Mapping(source = "topPostings", target = "topPostings", qualifiedByName = "postingsToString")
    CategoryDO toCategoryDO(Category category);



    @Named("stringToPostings")
    default List<PostingId> stringToPostings(String postings){
        List<PostingId> res = new ArrayList<>();
        if(postings == null){
            return res;
        }
        String[] strArr = postings.split(";");
        for(String s : strArr){
            try {
                BigInteger id = new BigInteger(s);
                PostingId postingId = new PostingId();
                postingId.setId(id);
                res.add(postingId);
            }catch (NumberFormatException ignore){
            }
        }
        return res;
    }

    @Named("postingsToString")
    default String postingsToString(List<PostingId> postings){
        if(postings == null){
            return "";
        }
        return postings.stream().
                map(PostingId::getId).
                map(BigInteger::toString).
                reduce((s1, s2) -> s1 + ";" + s2).orElse("");
    }

}
