package com.oyoungy.ddd.application.command;

import lombok.Data;

@Data
public class UserValidateCommand {
    private Long validateId;
    private Long id;
    private String role;
}
