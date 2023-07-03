package com.oyoungy.ddd.infra.database;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "admin_role_relation")
public class AdminRoleDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            columnDefinition = "int unsigned"
    )
    private Long id;
    @Column(
            columnDefinition = "int unsigned"
    )
    private Long adminId;
    private Integer roleId;
    private Date gmtCreate;
}
