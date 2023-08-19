package com.oyoungy.ddd.domain.user.entity;

import com.oyoungy.ddd.domain.user.vo.UserId;
import com.oyoungy.enums.OnlineEnum;
import com.oyoungy.enums.StatusEnum;
import com.oyoungy.util.StringUtils;
import lombok.Data;

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
    private StatusEnum status;
    private OnlineEnum online;
    private Date lastLogin;
    private Date gmtCreate;
    private Date gmtModified;

    public void init(){
        setId(null);
        setGmtCreate(new Date());
        setPhoneNumber(null);
        setStatus(StatusEnum.USABLE);
        setOnline(OnlineEnum.OFFLINE);
        setLastLogin(null);
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
        setOnline(OnlineEnum.ONLINE);
        setLastLogin(new Date());
    }
}
