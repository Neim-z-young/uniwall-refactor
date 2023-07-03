package com.oyoungy.ddd.infra.converter;


import com.oyoungy.ddd.domain.user.entity.PlatformUser;
import com.oyoungy.ddd.domain.user.entity.User;
import com.oyoungy.ddd.domain.user.vo.PlatformUserId;
import com.oyoungy.ddd.domain.user.vo.UserId;
import com.oyoungy.ddd.infra.database.PlatformUserDO;
import com.oyoungy.ddd.infra.database.UserDO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class DoConverterTest {

    @Test
    public void toUserDO() {
        User user = new User();
        user.setId(new UserId());
        user.getId().setId(1000L);
        user.setEmail("123456@163.com");
        user.setGmtCreate(new Date());
        UserDO userDO = DoConverter.INSTANCE.toUserDO(user);
        Assertions.assertEquals(user.getId().getId(), userDO.getId());
        Assertions.assertEquals(user.getGmtCreate(), userDO.getGmtCreate());
    }

    @Test
    public void toUser() {
        UserDO userDO = new UserDO();
        userDO.setId(1000L);
        userDO.setEmail("123456@163.com");
        userDO.setGmtCreate(new Date());
        userDO.setStatus((byte)1);
        User user = DoConverter.INSTANCE.toUser(userDO);
        Assertions.assertEquals(userDO.getId(), user.getId().getId());
        Assertions.assertEquals(userDO.getEmail(), user.getEmail());
        Assertions.assertEquals(userDO.getGmtCreate(), user.getGmtCreate());
        System.out.println(userDO.getStatus());
        System.out.println(user.getStatus());
        Assertions.assertEquals((int)userDO.getStatus(), (int)user.getStatus());
    }

    @Test
    public void toPlatformUserDO() {
        PlatformUserDO platformUserDO = new PlatformUserDO();
        platformUserDO.setOpenId("123");
        platformUserDO.setPlatformType("wechat");

        PlatformUser platformUser = DoConverter.INSTANCE.toPlatformUser(platformUserDO);
        Assertions.assertEquals(platformUser.getId().getOpenId(), platformUserDO.getOpenId());
    }

    @Test
    public void toPlatformUser() {
        PlatformUser platformUser = new PlatformUser();
        platformUser.setId(new PlatformUserId());
        platformUser.getId().setOpenId("123");
        platformUser.getId().setPlatformType("wechat");

        PlatformUserDO platformUserDO = DoConverter.INSTANCE.toPlatformUserDO(platformUser);
        Assertions.assertEquals(platformUserDO.getPlatformType(), platformUser.getId().getPlatformType());
    }
}