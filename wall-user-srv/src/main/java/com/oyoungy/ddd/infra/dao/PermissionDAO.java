package com.oyoungy.ddd.infra.dao;

import com.oyoungy.ddd.infra.database.PermissionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionDAO extends JpaRepository<PermissionDO, Integer> {
}
