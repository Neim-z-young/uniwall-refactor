package com.oyoungy.ddd.domain.entity;

import com.oyoungy.ddd.domain.vo.PostingId;
import lombok.Data;

import java.util.List;

@Data
public class PostingAgg {
    private PostingId id;
    private Posting posting;
    private Category category;
    private List<Remark> remarks;
    private List<InnerRemark> innerRemarks;
}
