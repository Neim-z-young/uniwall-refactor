package com.oyoungy.ddd.infra.dao;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.oyoungy.ddd.infra.database.UserInformationDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface UserInfoDAO extends JpaRepository<UserInformationDO, Long> {
    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @CanIgnoreReturnValue
    @Query(value = "update UserInformationDO set gender = :gender,  growth = :growth, credit = :credit, gmtModified = :modifyTime, gmtCreate = :createTime where userId = :userId")
    int update4CreateUser(@Param("userId") long userId, @Param("gender") int gender, @Param("growth") int growth, @Param("credit") int credit, @Param("modifyTime") Date modifyTime, @Param("createTime") Date createTime);

}
