package com.oyoungy.ddd.application.command;

import lombok.Data;

@Data
public class AddCategoryCommand {
    private String category;
    private String introduction;
    private Long createUserId;
}
