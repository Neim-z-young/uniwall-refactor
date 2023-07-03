package com.oyoungy.ddd.domain.permission.entity;

import com.oyoungy.ddd.domain.permission.vo.RoleId;
import lombok.Data;

import java.util.List;

@Data
public class RoleAgg {
    private RoleId id;
    private Role role;
    private List<Permission> permissions;
}
