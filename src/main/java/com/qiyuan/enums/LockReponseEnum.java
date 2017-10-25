package com.qiyuan.enums;

/**
 * Created by IntelliJ IDEA.
 * User: tonghengzhen
 * Date: 2017/10/20
 * Time: 12:17
 */
public enum LockReponseEnum {

    OK("0","获取锁状态成功");



    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    LockReponseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
