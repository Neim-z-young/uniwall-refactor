package com.oyoungy.ddd.domain.entity;

import com.oyoungy.ddd.domain.vo.CategoryId;
import com.oyoungy.ddd.domain.vo.PostingId;
import com.oyoungy.enums.StateEnum;
import com.oyoungy.util.StringUtils;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

@Data
public class Posting {
    private PostingId id;
    private String theme;
    private String briefIntroduction;
    private String detailedIntroduction;
    private Long userId;
    private StateEnum state;
    private CategoryId categoryId;
    private Date gmtCreate;
    private Date gmtModified;

    public void init(){
        setId(null);
        setState(StateEnum.APPROVING);
        setGmtCreate(new Date());
        setGmtModified(new Date());
        Objects.requireNonNull(theme, "主题为空");
        Objects.requireNonNull(detailedIntroduction, "内容为空");
        Objects.requireNonNull(userId, "创建用户为空");
        Objects.requireNonNull(categoryId, "类别为空");
        setBriefIntroduction(StringUtils.cutStringHead(detailedIntroduction, 50, "..."));
    }

    public void delete(){
        Objects.requireNonNull(id, "id为空");
        setState(StateEnum.APPROVING);
    }
}
