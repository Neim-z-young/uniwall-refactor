package com.oyoungy.ddd.application.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryDTO {
    private Long id;
    private String category;
    private String introduction;
    private String state;
    private Long createUserId;
    private Date gmtCreate;
    private Date gmtModified;
}
