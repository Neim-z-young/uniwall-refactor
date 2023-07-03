package com.oyoungy.ddd.infra.database;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Data
@Entity
@IdClass(PlatformUserKeys.class)
public class PlatformUserDO {
    @Id
    private String openId;
    @Id
    private String platformType;
    @Column(
            columnDefinition = "int unsigned"
    )
    private Long userId;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
}
