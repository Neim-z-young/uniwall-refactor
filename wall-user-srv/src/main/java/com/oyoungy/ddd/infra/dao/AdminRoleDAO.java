package com.oyoungy.ddd.infra.dao;

import com.oyoungy.ddd.infra.database.AdminRoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface AdminRoleDAO extends JpaRepository<AdminRoleDO, Long> {
    Stream<AdminRoleDO> findByAdminId(Long adminId);
}
