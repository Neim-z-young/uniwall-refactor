package com.oyoungy.ddd.application.event;

import lombok.Data;

@Data
public class CategoryApprovingEvent {
    private Long categoryId;
    private String category;
    private String approvingMsg;
    private String operation;
    private String state;
    private Long approvingUserId;
}
