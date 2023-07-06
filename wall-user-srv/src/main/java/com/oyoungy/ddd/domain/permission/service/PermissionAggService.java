package com.oyoungy.ddd.domain.permission.service;

import com.oyoungy.ddd.domain.permission.vo.RoleId;
import com.oyoungy.ddd.domain.user.vo.UserId;

import java.util.List;

public interface PermissionAggService {
    List<RoleId> defaultUserRole();

    List<RoleId> defaultAdminRole();

    void createUserRoleRelation(UserId id, List<RoleId> roleIds);

    List<RoleId> findUserRoles(UserId id);
}
