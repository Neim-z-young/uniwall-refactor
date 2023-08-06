package com.oyoungy.ddd.infra.impl.domain.repository;

import com.oyoungy.ddd.domain.permission.entity.Permission;
import com.oyoungy.ddd.domain.permission.entity.Role;
import com.oyoungy.ddd.domain.permission.repository.PermissionRepository;
import com.oyoungy.ddd.domain.admin.vo.AdminId;
import com.oyoungy.ddd.domain.permission.vo.PermissionId;
import com.oyoungy.ddd.domain.permission.vo.RoleId;
import com.oyoungy.ddd.domain.user.vo.UserId;
import com.oyoungy.ddd.infra.converter.DoConverter;
import com.oyoungy.ddd.infra.dao.AdminRoleDAO;
import com.oyoungy.ddd.infra.dao.PermissionDAO;
import com.oyoungy.ddd.infra.dao.RoleDAO;
import com.oyoungy.ddd.infra.dao.UserRoleDAO;
import com.oyoungy.ddd.infra.database.AdminRoleDO;
import com.oyoungy.ddd.infra.database.PermissionDO;
import com.oyoungy.ddd.infra.database.RoleDO;
import com.oyoungy.ddd.infra.database.UserRoleDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class PermissionRepositoryImpl implements PermissionRepository {
    private DoConverter doConverter = DoConverter.INSTANCE;

    @Autowired
    private PermissionDAO permissionDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private AdminRoleDAO adminRoleDAO;

    @Override
    public Permission save(Permission permission) {
        PermissionDO permissionDO = doConverter.toPermissionDO(permission);
        permissionDO = permissionDAO.save(permissionDO);
        return doConverter.toPermission(permissionDO);
    }

    @Override
    public Optional<Permission> findOne(PermissionId id) {
        return permissionDAO.findById(id.getId()).map(doConverter::toPermission);
    }

    @Override
    public Role save(Role role) {
        RoleDO roleDO = doConverter.toRoleDO(role);
        return doConverter.toRole(roleDAO.save(roleDO));
    }

    @Override
    public Optional<Role> findOne(RoleId id) {
        return roleDAO.findById(id.getId()).map(doConverter::toRole);
    }

    @Override
    public List<RoleId> findUserRoleId(UserId id) {
        try(Stream<UserRoleDO> userRoleDOStream = userRoleDAO.findByUserId(id.getId())){
            return userRoleDOStream.
                    map(UserRoleDO::getRoleId).
                    map(doConverter::toRoleId).
                    collect(Collectors.toList());
        }
    }

    @Override
    public List<RoleId> findAdminRoleId(AdminId id) {
        try(Stream<AdminRoleDO> adminRoleDOStream = adminRoleDAO.findByAdminId(id.getId())){
            return adminRoleDOStream.
                    map(AdminRoleDO::getRoleId).
                    map(doConverter::toRoleId).
                    collect(Collectors.toList());
        }
    }

    @Override
    public void createUserRoleRelation(UserId id, List<RoleId> roleIds) {
        List<UserRoleDO> roleDOS = new ArrayList<>();
        for(RoleId roleId : roleIds){
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setUserId(id.getId());
            userRoleDO.setRoleId(roleId.getId());
            userRoleDO.setGmtCreate(new Timestamp(new Date().getTime()));
            roleDOS.add(userRoleDO);
        }
        userRoleDAO.saveAll(roleDOS);
    }

    @Override
    public Optional<String> getRoleName(RoleId id) {
        return roleDAO.findNameById(id.getId());
    }

}
