package com.oyoungy.ddd.domain.user.entity;

import com.oyoungy.ddd.domain.user.vo.UserId;
import com.oyoungy.ddd.domain.permission.vo.RoleId;
import lombok.Data;

import java.util.List;


@Data
public class UserAgg {
    private UserId id;
    private User user;
    private UserInfo userInfo;
    private List<PlatformUser> platformUsers;
    private List<RoleId> roles;

    public boolean checkUserValid(){
        return false;
    }
}
