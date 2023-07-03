package com.oyoungy.ddd.infra.database;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "role_permission_relation")
public class RolePermissionDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer permissionId;

    private Integer roleId;

    private Date gmtCreate;
}
