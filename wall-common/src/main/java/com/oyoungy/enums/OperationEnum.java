package com.oyoungy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationEnum {
    CREATE("创建", 0),
    DELETE("删除", 1),
    MODIFY("修改", 2),
    RETRIEVE("查询", 3);


    private String msg;
    private Integer value;

    public static OperationEnum of(String msg){
        for(OperationEnum status: values()){
            if(status.msg.equals(msg)){
                return status;
            }
        }
        throw new IllegalArgumentException("unknown OperationEnum of " + msg);
    }
}
