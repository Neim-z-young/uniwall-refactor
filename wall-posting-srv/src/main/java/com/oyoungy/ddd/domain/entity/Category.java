package com.oyoungy.ddd.domain.entity;

import com.oyoungy.ddd.domain.vo.CategoryId;
import com.oyoungy.ddd.domain.vo.PostingId;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Category {
    private CategoryId id;
    private String category;
    private String introduction;
    private List<PostingId> topPostings;
    private Long createUserId;
    private Date gmtCreate;
    private Date gmtModified;
}
