package com.oyoungy.ddd.domain.user.entity;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserInfoTest {

    @Test
    public void incrementGrowthTestMinus() {
        UserInfo userInfo = new UserInfo();
        userInfo.setGrowth(1000);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userInfo.incrementGrowth(-100));
    }

    @Test
    public void incrementGrowthTestOverflow() {
        UserInfo userInfo = new UserInfo();
        userInfo.setGrowth(1000);
        Assertions.assertThrows(IllegalStateException.class, () -> userInfo.incrementGrowth(Integer.MAX_VALUE));
    }
}