package com.qiyuan.enums;

public enum SupplierEnum implements EnumService{

    GETBICKSUPPLIERNAME_OK(0,"获取厂家成功"),

    GETBICKSUPPLIERNAME_UNFOUND(10011,"查询不到此车辆号"),

    CHANGELOCK_OK(okCode,"换锁绑定成功"),

    CHANGELOC_FAIL(10012,"换锁绑定失败");

    public  int code;

    public  String message;


    SupplierEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
