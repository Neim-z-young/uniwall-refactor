package com.oyoungy.ddd.domain.permission.service;

import com.oyoungy.ddd.domain.permission.entity.Role;
import com.oyoungy.ddd.domain.permission.repository.PermissionRepository;
import com.oyoungy.ddd.domain.permission.vo.RoleId;
import com.oyoungy.ddd.domain.user.vo.UserId;
import com.oyoungy.ddd.infra.database.UserRoleDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.*;

@Service
@Slf4j
public class PermissionAggServiceImpl implements PermissionAggService {
    @Autowired
    private PermissionRepository permissionRepository;

    public List<RoleId> defaultUserRole(){
        return new ArrayList<>();
    }

    public List<RoleId> defaultAdminRole(){
        return new ArrayList<>();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void createUserRoleRelation(UserId id, List<RoleId> roleIds){
        Set<RoleId> newRoleIds = new HashSet<>();
        for(RoleId roleId : roleIds){
            if(newRoleIds.contains(roleId)){
                continue;
            }
            permissionRepository.findOne(roleId).
                    ifPresentOrElse(
                            role -> newRoleIds.add(role.getId()),
                            () -> log.warn("角色id{}不存在", roleId));
        }
        permissionRepository.createUserRoleRelation(id, new ArrayList<>(newRoleIds));
    }

    @Override
    public List<RoleId> findUserRoles(UserId id) {
        return permissionRepository.findUserRoleId(id);
    }
}
