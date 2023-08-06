package com.oyoungy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StateEnum {
    NONE(-1, "未知"),
    APPROVING(0, "审批中"),
    USABLE(1, "可用"),
    DELETED(2, "删除"),
    DENIED(3, "审批失败");

    private Integer value;
    private String msg;

    public static StateEnum of(Integer value){
        for(StateEnum state: values()){
            if(state.value.equals(value)){
                return state;
            }
        }
        throw new IllegalArgumentException("unknown StateEnum of " + value);
    }

}
