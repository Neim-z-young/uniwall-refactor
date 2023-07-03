package com.oyoungy.ddd.domain.user.entity;

import com.oyoungy.ddd.domain.user.vo.PlatformUserId;
import com.oyoungy.ddd.domain.user.vo.UserId;
import lombok.Data;

import java.sql.Date;

@Data
public class PlatformUser {
    private PlatformUserId id;
    private UserId user;
    private Date gmtCreate;
    private Date gmtModified;

}
