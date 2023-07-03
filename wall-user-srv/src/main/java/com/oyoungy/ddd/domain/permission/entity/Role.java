package com.oyoungy.ddd.domain.permission.entity;

import com.oyoungy.ddd.domain.permission.vo.RoleId;
import lombok.Data;

import java.util.Date;

@Data
public class Role {
    private RoleId id;
    private String name;
    private String introduction;
    private Date gmtCreate;
    private Date gmtModified;
}
