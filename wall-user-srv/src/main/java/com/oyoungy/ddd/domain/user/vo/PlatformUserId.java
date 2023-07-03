package com.oyoungy.ddd.domain.user.vo;

import lombok.Data;

@Data
public class PlatformUserId {
    private String openId;
    private String platformType;
}
