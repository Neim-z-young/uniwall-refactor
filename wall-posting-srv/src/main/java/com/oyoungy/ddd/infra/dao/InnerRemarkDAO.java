package com.oyoungy.ddd.infra.dao;

import com.oyoungy.ddd.infra.database.InnerRemarkDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface InnerRemarkDAO extends JpaRepository<InnerRemarkDO, BigInteger> {

}
