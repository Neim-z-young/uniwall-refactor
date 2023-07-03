package com.oyoungy.ddd.infra.dao;

import com.oyoungy.ddd.infra.database.PlatformUserDO;
import com.oyoungy.ddd.infra.database.PlatformUserKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformUserDAO extends JpaRepository<PlatformUserDO, PlatformUserKeys> {
}
