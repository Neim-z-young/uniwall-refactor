package com.oyoungy.ddd.infra.database;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;

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
    private Date gmtCreate;
    private Date gmtModified;
}
