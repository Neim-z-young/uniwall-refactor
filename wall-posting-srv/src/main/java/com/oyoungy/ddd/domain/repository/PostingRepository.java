package com.oyoungy.ddd.domain.repository;

import com.oyoungy.ddd.domain.entity.Posting;
import com.oyoungy.ddd.domain.vo.PostingId;

import java.util.List;

public interface PostingRepository extends BaseRepository<Posting, PostingId> {
    List<Posting> findNextPage(PostingId bound, Integer pageSize);

    List<Posting> findPrevPage(PostingId bound, Integer pageSize);

    boolean updateState(Posting category);
}
