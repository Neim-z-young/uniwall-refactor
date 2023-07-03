package com.oyoungy.ddd.infra.database;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class UserInformationDO {
    @Id
    @Column(
            columnDefinition = "int unsigned"
    )
    private Long userId;
    private Byte gender;
    private Integer growth;
    private Integer credit;
    private Date gmtCreate;
    private Date gmtModified;
}
