package com.qiyuan.enums;

public enum ServiceNameEnum {

    FACTORY("factory"),

    TERMINAL("terminal"),

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
