package com.oyoungy.ddd.infra.convertor;

import com.oyoungy.ddd.domain.entity.Category;
import com.oyoungy.ddd.domain.entity.InnerRemark;
import com.oyoungy.ddd.domain.entity.Posting;
import com.oyoungy.ddd.domain.entity.Remark;
import com.oyoungy.ddd.domain.vo.PostingId;
import com.oyoungy.ddd.infra.database.CategoryDO;
import com.oyoungy.ddd.infra.database.InnerRemarkDO;
import com.oyoungy.ddd.infra.database.PostingDO;
import com.oyoungy.ddd.infra.database.RemarkDO;
import com.oyoungy.enums.StateEnum;
import com.oyoungy.util.ConvertUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Mapper(uses = {ConvertUtils.class})
public interface DoConverter {
    DoConverter INSTANCE = Mappers.getMapper(DoConverter.class);

    @Mapping(source = "id", target = "id.id")
    Category toCategory(CategoryDO categoryDO);

    @Mapping(source = "id.id", target = "id")
    CategoryDO toCategoryDO(Category category);

    @Mapping(source = "id", target = "id.id")
    @Mapping(source = "postingId", target = "postingId.id")
    @Mapping(source = "remarkId", target = "remarkId.id")
    InnerRemark toInnerRemark(InnerRemarkDO innerRemarkDO);

    @Mapping(source = "id.id", target = "id")
    @Mapping(source = "postingId.id", target = "postingId")
    @Mapping(source = "remarkId.id", target = "remarkId")
    InnerRemarkDO toInnerRemarkDO(InnerRemark innerRemark);

    @Mapping(source = "id", target = "id.id")
    @Mapping(source = "postingId", target = "postingId.id")
    Remark toRemark(RemarkDO remarkDO);

    @Mapping(source = "id.id", target = "id")
    @Mapping(source = "postingId.id", target = "postingId")
    RemarkDO toRemarkDO(Remark remark);

    @Mapping(source = "id", target = "id.id")
    @Mapping(source = "categoryId", target = "categoryId.id")
    Posting toPosting(PostingDO postingDO);

    @Mapping(source = "id.id", target = "id")
    @Mapping(source = "categoryId.id", target = "categoryId")
    PostingDO toPostingDO(Posting posting);
}
