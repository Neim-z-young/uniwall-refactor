package com.oyoungy.ddd.infra.dao;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.oyoungy.ddd.infra.database.PostingDO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Repository
public interface PostingDAO extends JpaRepository<PostingDO, BigInteger> {
    List<PostingDO> findByIdGreaterThanAndState(BigInteger lowerBound, Integer state, Pageable pageable);

    List<PostingDO> findByIdLessThanAndState(BigInteger upperBound, Integer state, Pageable pageable);

    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @CanIgnoreReturnValue
    @Query(value = "update PostingDO set state = :state where id = :postingId")
    int updateState(@Param("postingId") BigInteger postingId, @Param("state") int state);
}
