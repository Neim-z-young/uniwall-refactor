package com.oyoungy.ddd.domain.user.entity;

import com.oyoungy.ddd.domain.user.vo.UserId;
import com.oyoungy.util.StringUtils;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Data
public class User {
    private UserId id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Integer status;
    private Integer online;
    private Date lastLogin;
    private Date gmtCreate;
    private Date gmtModified;

    public void init(){
        setId(null);
        setGmtCreate(new Date());
        setPhoneNumber(null);
        setStatus(1);
        setOnline(0);
        setLastLogin(null);
        setGmtModified(new Date());
        setGmtModified(new Date());
        setNickname(Optional.ofNullable(nickname).
                orElse(MessageFormat.format("user_{0}", StringUtils.generateRandomString(6))));
        Objects.requireNonNull(password);
        Objects.requireNonNull(email);
    }

    public void login(){
        if(id == null){
            throw new IllegalStateException("用户不存在");
        }
        setOnline(1);
        setLastLogin(new Date());
    }
}
