package com.oyoungy.ddd.infra.database;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Data
public class RemarkDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            columnDefinition = "bigint unsigned"
    )
    private BigInteger id;
    @Column(
            columnDefinition = "bigint unsigned"
    )
    private BigInteger postingId;
    @Column(
            columnDefinition = "int unsigned"
    )
    private Long userId;

    private String content;

    @Column(
            columnDefinition = "tinyint"
    )
    private Integer state;

    private Date gmtCreate;

    private Date gmtModified;
}
