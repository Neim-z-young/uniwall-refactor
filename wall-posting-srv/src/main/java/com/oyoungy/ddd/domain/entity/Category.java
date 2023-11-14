package com.oyoungy.ddd.domain.entity;

import com.oyoungy.ddd.application.domain.entity.ApprovableEntity;
import com.oyoungy.ddd.domain.vo.CategoryId;
import com.oyoungy.enums.StateEnum;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public class Category extends ApprovableEntity<CategoryId> {
    private String category;
    private String introduction;
    private Long createUserId;
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


}
