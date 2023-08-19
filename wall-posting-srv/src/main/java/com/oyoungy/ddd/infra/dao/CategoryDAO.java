package com.oyoungy.ddd.infra.dao;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.oyoungy.ddd.infra.database.CategoryDO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CategoryDAO extends JpaRepository<CategoryDO, Long> {
    List<CategoryDO> findByIdGreaterThanAndState(Long lowerBound, Integer state,Pageable pageable);

    List<CategoryDO> findByIdLessThanAndState(Long upperBound, Integer state, Pageable pageable);

    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @CanIgnoreReturnValue
    @Query(value = "update CategoryDO set state = :state where id = :categoryId")
    int updateState(@Param("categoryId") long categoryId, @Param("state") int state);
}
