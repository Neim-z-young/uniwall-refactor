package com.oyoungy.ddd.infra.database;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlatformUserKeys implements Serializable {
    private String openId;
    private String platformType;
}
