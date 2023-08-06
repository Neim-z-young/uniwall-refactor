package com.oyoungy.ddd.infra.dao;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.oyoungy.ddd.infra.database.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface UserDAO extends JpaRepository<UserDO, Long> {
    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @CanIgnoreReturnValue
    @Query(value = "update UserDO set online = :online, lastLogin = :lastLogin where id = :userId")
    int update4Login(@Param("userId") long userId, @Param("online") int online, @Param("lastLogin") Date lastLogin);

}
