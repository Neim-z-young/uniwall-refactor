package com.oyoungy.ddd.infra.dao;

import com.oyoungy.ddd.infra.database.RolePermissionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionDAO extends JpaRepository<RolePermissionDO, Integer> {
}
