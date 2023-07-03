package com.oyoungy.ddd.infra.converter;

import com.oyoungy.ddd.domain.admin.entity.Admin;
import com.oyoungy.ddd.domain.permission.entity.Permission;
import com.oyoungy.ddd.domain.permission.entity.Role;
import com.oyoungy.ddd.domain.user.entity.PlatformUser;
import com.oyoungy.ddd.domain.user.entity.User;
import com.oyoungy.ddd.domain.user.entity.UserInfo;
import com.oyoungy.ddd.domain.user.vo.PlatformUserId;
import com.oyoungy.ddd.domain.permission.vo.RoleId;
import com.oyoungy.ddd.infra.database.*;
import com.oyoungy.util.ConvertUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ConvertUtils.class)
public interface DoConverter {

    DoConverter INSTANCE = Mappers.getMapper(DoConverter.class);

    @Mapping(source = "id.id", target = "id")
    UserDO toUserDO(User user);

    @Mapping(source = "id", target = "id.id")
    User toUser(UserDO userDO);

    @Mapping(source = "id.openId", target = "openId")
    @Mapping(source = "id.platformType", target = "platformType")
    @Mapping(source = "user.id", target = "userId")
    PlatformUserDO toPlatformUserDO(PlatformUser platformUser);

    @Mapping(source = "openId", target = "id.openId")
    @Mapping(source = "platformType", target = "id.platformType")
    @Mapping(source = "userId", target = "user.id")
    PlatformUser toPlatformUser(PlatformUserDO platformUserDO);

    PlatformUserKeys toPlatformUserKeys(PlatformUserId platformUserId);

    PlatformUserId toPlatformUserId(PlatformUserKeys platformUserkeys);

    @Mapping(source = "id.id", target = "userId")
    UserInformationDO toUserInformationDO(UserInfo userInfo);

    @Mapping(source = "userId", target = "id.id")
    UserInfo toUserInfo(UserInformationDO userInformationDO);

    @Mapping(source = "id.id", target = "id")
    AdminDO toAdminDO(Admin admin);

    @Mapping(source = "id", target = "id.id")
    Admin toAdmin(AdminDO adminDO);

    @Mapping(source = "id.id", target = "id")
    PermissionDO toPermissionDO(Permission permission);

    @Mapping(source = "id", target = "id.id")
    Permission toPermission(PermissionDO permissionDO);

    @Mapping(source = "id.id", target = "id")
    RoleDO toRoleDO(Role role);

    @Mapping(source = "id", target = "id.id")
    Role toRole(RoleDO roleDO);

    RoleId toRoleId(Integer id);

}
