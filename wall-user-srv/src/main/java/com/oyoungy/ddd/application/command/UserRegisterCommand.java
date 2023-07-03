package com.oyoungy.ddd.application.command;

import lombok.Data;

@Data
public class UserRegisterCommand {
    private String email;
    private String nickname;
    private String password;
}
