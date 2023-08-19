package com.oyoungy.ddd.application.dto;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
public class PostingBriefDTO {
    private BigInteger id;
    private String theme;
    private String briefIntroduction;
    private Long userId;
    private Date gmtCreate;
}
