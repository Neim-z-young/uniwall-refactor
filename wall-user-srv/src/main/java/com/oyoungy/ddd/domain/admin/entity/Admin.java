package com.oyoungy.ddd.domain.admin.entity;

import com.oyoungy.ddd.domain.admin.vo.AdminId;
import lombok.Data;

import java.util.Date;

@Data
public class Admin {
    private AdminId id;
    private String nickname;
    private String password;
    private String email;
    private String phoneNumber;
    private Integer status;
    private Integer online;
    private Date lastLogin;
    private Date gmtCreate;
    private Date gmtModified;
}
