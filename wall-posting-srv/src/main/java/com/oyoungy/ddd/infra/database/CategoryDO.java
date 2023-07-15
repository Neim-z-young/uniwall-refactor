package com.oyoungy.ddd.infra.database;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
////@Entity
public class CategoryDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            columnDefinition = "int unsigned"
    )
    private Long id;
    private String category;
    private String introduction;
    private String topPostings;
    @Column(
            columnDefinition = "int unsigned"
    )
    private Long createUserId;
    private Date gmtCreate;
    private Date gmtModified;
}
