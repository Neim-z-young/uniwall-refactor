package com.oyoungy.ddd.domain.permission.entity;

import com.oyoungy.ddd.domain.permission.vo.PermissionId;
import lombok.Data;

import java.util.Date;

@Data
public class Permission {
    private PermissionId id;
    private String permission;
    private String introduction;
    private Date gmtCreate;
    private Date gmtModified;
}
