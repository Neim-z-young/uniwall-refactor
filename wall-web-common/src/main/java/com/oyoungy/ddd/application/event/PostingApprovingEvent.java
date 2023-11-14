package com.oyoungy.ddd.application.event;

import lombok.Data;

import java.math.BigInteger;

@Data
public class PostingApprovingEvent {
    private BigInteger postingId;
    private Long categoryId;
    private String approvingMsg;
    private String operation;
    private String state;
    private Long approvingUserId;
}
