package com.oyoungy.ddd.infra.database;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Data
public class PostingDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            columnDefinition = "bigint unsigned"
    )
    private BigInteger id;
    private String theme;
    private String briefIntroduction;
    private String detailedIntroduction;
    @Column(
            columnDefinition = "int unsigned"
    )
    private Long userId;
    @Column(
            columnDefinition = "tinyint"
    )
    private Integer state;
    @Column(
            columnDefinition = "int unsigned"
    )
    private Long categoryId;
    private Date gmtCreate;
    private Date gmtModified;
}
