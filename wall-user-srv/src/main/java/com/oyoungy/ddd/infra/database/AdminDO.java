package com.oyoungy.ddd.infra.database;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class AdminDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            columnDefinition = "int unsigned"
    )
    private Long id;

    private String nickname;

    private String password;

    private String email;

    private String phoneNumber;

    private Byte status;

    private Byte online;

    private Date lastLogin;

    private Date gmtCreate;

    private Date gmtModified;
}