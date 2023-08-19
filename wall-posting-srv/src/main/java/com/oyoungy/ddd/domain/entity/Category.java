package com.oyoungy.ddd.domain.entity;

import com.oyoungy.ddd.domain.vo.CategoryId;
import com.oyoungy.ddd.domain.vo.PostingId;
import com.oyoungy.enums.StateEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
public class Category {
    private CategoryId id;
    private String category;
    private String introduction;
    private Long createUserId;
    private StateEnum state;
    private Date gmtCreate;
    private Date gmtModified;

    public void init(){
        setId(null);
        setState(StateEnum.APPROVING);
        setGmtCreate(new Date());
        setGmtModified(new Date());
        Objects.requireNonNull(category, "类别为空");
        Objects.requireNonNull(createUserId, "创建用户为空");
    }

    public void deleted(){
        Objects.requireNonNull(id, "id为空");
        setState(StateEnum.DELETED);
    }

    public void approving(){
        Objects.requireNonNull(id, "id为空");
        setState(StateEnum.APPROVING);
    }

    public void created(){
        Objects.requireNonNull(id, "id为空");
        setState(StateEnum.USABLE);
    }
}
