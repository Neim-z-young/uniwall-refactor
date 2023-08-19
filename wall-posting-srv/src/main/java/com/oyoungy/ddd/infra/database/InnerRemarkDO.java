package com.oyoungy.ddd.infra.database;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Data
public class InnerRemarkDO {
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
            columnDefinition = "bigint unsigned"
    )
    private BigInteger remarkId;

    @Column(
            columnDefinition = "int unsigned"
    )
    private Long userId;

    @Column(
            columnDefinition = "tinyint"
    )
    private Integer state;

    private String content;

    private Date gmtCreate;

    private Date gmtModified;
}
