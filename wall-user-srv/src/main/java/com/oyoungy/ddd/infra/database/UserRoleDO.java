package com.oyoungy.ddd.infra.database;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_role_relation")
public class UserRoleDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            columnDefinition = "int unsigned"
    )
    private Long id;

    @Column(
            columnDefinition = "int unsigned"
    )
    private Long userId;

    private Integer roleId;

    private Timestamp gmtCreate;
}
