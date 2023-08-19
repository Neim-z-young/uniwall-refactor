package com.oyoungy.ddd.infra.dao;

import com.oyoungy.ddd.infra.database.RemarkDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface RemarkDAO extends JpaRepository<RemarkDO, BigInteger> {
}
