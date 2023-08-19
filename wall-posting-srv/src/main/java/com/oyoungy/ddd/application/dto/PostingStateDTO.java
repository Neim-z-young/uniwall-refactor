package com.oyoungy.ddd.application.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class PostingStateDTO {
    private BigInteger id;
    private String theme;
    private String state;
    private Long userId;
    private Long categoryId;
}
