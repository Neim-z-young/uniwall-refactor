package com.oyoungy.ddd.application.command;

import lombok.Data;

@Data
public class AddPostingCommand {
    private String theme;
    private String detailedIntroduction;
    private Long userId;
    private Long categoryId;
}
