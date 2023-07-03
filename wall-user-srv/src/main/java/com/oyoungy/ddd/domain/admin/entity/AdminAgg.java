package com.oyoungy.ddd.domain.admin.entity;

import com.oyoungy.ddd.domain.admin.vo.AdminId;
import com.oyoungy.ddd.domain.permission.vo.RoleId;

import java.util.List;

public class AdminAgg {
    private AdminId id;
    private Admin admin;
    private List<RoleId> roles;
}
