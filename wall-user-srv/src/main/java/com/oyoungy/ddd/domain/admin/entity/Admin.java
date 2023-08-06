package com.oyoungy.ddd.domain.admin.entity;

import com.oyoungy.ddd.domain.admin.vo.AdminId;
import com.oyoungy.enums.OnlineEnum;
import com.oyoungy.enums.StatusEnum;
import lombok.Data;

import java.util.Date;

@Data
public class Admin {
    private AdminId id;
    private String nickname;
    private String password;
    private String email;
    private String phoneNumber;
    private StatusEnum status;
    private OnlineEnum online;
    private Date lastLogin;
    private Date gmtCreate;
    private Date gmtModified;
}
