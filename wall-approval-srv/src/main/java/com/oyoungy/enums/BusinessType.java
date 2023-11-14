package com.oyoungy.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BusinessType {
    UNKNOWN(0, "未知业务"),
    CATEGORY(1, "类别相关业务"),
    POSTING(2, "帖子相关业务"),
    USER_OP(3, "用户相关业务"),
    ADMIN_OP(4, "管理员相关业务"),
    ;

    private Integer type;
    private String msg;
}
