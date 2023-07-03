package com.oyoungy.response;

public enum  HttpCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    NOT_FOUND(404, "未找到对象"),
    UNAUTHORIZED(401, "暂未登录或token失效"),
    FORBIDDEN(403, "没有相关权限");

    private int code;
    private String message;

    HttpCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
