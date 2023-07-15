package com.oyoungy.ddd.domain.entity;

import com.oyoungy.ddd.domain.vo.CategoryId;
import com.oyoungy.ddd.domain.vo.PostingId;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
public class Posting {
    private PostingId id;
    private String theme;
    private String briefIntroduction;
    private String detailedIntroduction;
    private Long userId;
    private BigInteger approvalId;
    private Byte state;
    private CategoryId categoryId;
    private Date gmtCreate;
    private Date gmtModified;
}
