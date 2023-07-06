package com.oyoungy.ddd.infra.dao;

import com.oyoungy.ddd.infra.database.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDAO extends JpaRepository<RoleDO, Integer> {
    Optional<String> findNameById(Integer id);
}
