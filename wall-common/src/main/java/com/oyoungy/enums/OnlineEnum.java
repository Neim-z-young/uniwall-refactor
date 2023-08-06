package com.oyoungy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OnlineEnum {
    NONE(0, "未知"),
    ONLINE(1, "在线"),
    OFFLINE(2, "离线");

    private Integer value;
    private String msg;

    public static OnlineEnum of(Integer value){
        for(OnlineEnum onlineEnum: values()){
            if(onlineEnum.getValue().equals(value)){
                return onlineEnum;
            }
        }
        throw new IllegalArgumentException("unknown OnlineEnum of " + value);
    }
}
