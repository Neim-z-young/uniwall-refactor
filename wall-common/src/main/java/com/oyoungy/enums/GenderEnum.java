package com.oyoungy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  GenderEnum {
    NONE(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");

    private Integer value;
    private String msg;

    public static GenderEnum of(Integer value){
        for(GenderEnum state: values()){
            if(state.value.equals(value)){
                return state;
            }
        }
        throw new IllegalArgumentException("unknown GenderEnum of " + value);
    }
}
