package com.oyoungy.ddd.application.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class PostingQueryDTO {
    private BigInteger id;
    private String theme;
    private String detailedIntroduction;
    private Long userId;
    private Long categoryId;
}
