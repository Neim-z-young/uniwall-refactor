package com.oyoungy.ddd.domain.permission.repository;

import com.oyoungy.ddd.domain.permission.entity.Permission;
import com.oyoungy.ddd.domain.permission.entity.Role;
import com.oyoungy.ddd.domain.admin.vo.AdminId;
import com.oyoungy.ddd.domain.permission.vo.PermissionId;
import com.oyoungy.ddd.domain.permission.vo.RoleId;
import com.oyoungy.ddd.domain.user.vo.UserId;
import com.oyoungy.domain.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends BaseRepository<Permission, PermissionId> {

    Role save(Role role);

    Optional<Role> findOne(RoleId id);

    List<RoleId> findUserRoleId(UserId id);

    List<RoleId> findAdminRoleId(AdminId id);

    void createUserRoleRelation(UserId id, List<RoleId> roleIds);
}
