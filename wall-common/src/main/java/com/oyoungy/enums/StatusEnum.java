package com.oyoungy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    NONE(-1, "未知"),
    USABLE(0, "使用中"),
    UNAVAILABLE(1, "禁用中");

    private Integer value;
    private String msg;

    public static StatusEnum of(Integer value){
        for(StatusEnum status: values()){
            if(status.value.equals(value)){
                return status;
            }
        }
        throw new IllegalArgumentException("unknown StatusEnum of " + value);
    }
}
