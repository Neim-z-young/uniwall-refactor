package com.oyoungy.ddd.application.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private Long id;

    private String nickname;

    private String email;

    private String phoneNumber;

    private String online;

    private Date lastLogin;

    private Date gmtCreate;

    private Date gmtModified;
}
