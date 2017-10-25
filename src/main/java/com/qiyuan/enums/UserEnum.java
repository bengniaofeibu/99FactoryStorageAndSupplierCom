package com.qiyuan.enums;

public enum UserEnum {
    UNKONW_ERROR(500, "未知错误"),
    SUCCESS(200, "成功"),
    NOT_FOUND(404, "未找到"),
    TOO_SHORT(1000, "太短了"),
    DUPLICATE_USER_NAME(1002, "用户名已存在"),
    NOT_CHANGED(1003, "用户名数据未发生改变"),
    ;

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    UserEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
