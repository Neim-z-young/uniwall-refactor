package com.oyoungy.ddd.domain.entity;

import com.oyoungy.ddd.domain.vo.InnerRemarkId;
import com.oyoungy.ddd.domain.vo.PostingId;
import com.oyoungy.ddd.domain.vo.RemarkId;
import com.oyoungy.enums.StateEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Remark {
    private RemarkId id;
    private PostingId postingId;
    private String content;
    private Long userId;
    private Date gmtCreate;
    private Date gmtModified;
    private StateEnum state;
    private List<InnerRemarkId> innerRemarkIds;
}
