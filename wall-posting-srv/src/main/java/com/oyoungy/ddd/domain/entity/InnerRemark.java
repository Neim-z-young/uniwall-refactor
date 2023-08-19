package com.oyoungy.ddd.domain.entity;

import com.oyoungy.ddd.domain.vo.InnerRemarkId;
import com.oyoungy.ddd.domain.vo.PostingId;
import com.oyoungy.ddd.domain.vo.RemarkId;
import com.oyoungy.enums.StateEnum;
import lombok.Data;

import java.util.Date;

@Data
public class InnerRemark {
    private InnerRemarkId id;
    private PostingId postingId;
    private RemarkId remarkId;
    private String content;
    private Long userId;
    private StateEnum state;
    private Date gmtCreate;
    private Date gmtModified;
}
