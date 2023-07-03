package com.oyoungy.ddd.application.command;

import lombok.Data;

@Data
public class UserLoginCommand {
    private Long id;
    private String password;
}
