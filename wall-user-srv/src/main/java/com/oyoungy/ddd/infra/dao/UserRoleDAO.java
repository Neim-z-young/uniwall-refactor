package com.oyoungy.ddd.infra.dao;

import com.oyoungy.ddd.infra.database.UserRoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface UserRoleDAO extends JpaRepository<UserRoleDO, Long> {
    Stream<UserRoleDO> findByUserId(Long userId);
}
