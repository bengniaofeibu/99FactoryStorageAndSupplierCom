package com.qiyuan.enums;


/**
 *  接口入口名称
 */
public enum ServiceNameEnum {

    //锁
    FACTORY("factory"),

    //车厂
    SUPPLIER("supplier");


    private  String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ServiceNameEnum(String name) {
        this.name = name;
    }
}
