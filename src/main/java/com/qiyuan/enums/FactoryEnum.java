package com.qiyuan.enums;

public enum FactoryEnum implements EnumService{

    BICYCLE_NUM_ERROR(failCode,"没有此类型车辆编号"),

    BICYCLE_NUM_NOT_BINDING(failCode,"此设备没绑定"),

    BARCODE_URL_ERROR(failCode,"非法二维码"),

    CMD_ERROR(failCode,"非法指令"),

    GET_BICK_SUPPLIER_NAME_OK(okCode,"获取厂家成功"),

    GET_BICK_SUPPLIER_NAME_UNFOUND(failCode,"查询不到此车辆号"),

    CHANGE_LOCK_OK(okCode,"换锁绑定成功"),

    CHANGE_LOC_FAIL(failCode,"换锁绑定失败"),

    OPEN_LOCK_OK(okCode,"指令发送成功"),

    OPEN_LOCK_FAIL(failCode,"指令发送失败"),

    GET_BICYLE_LOCK_OK(okCode,"获取锁的状态成功"),

    GET_BICYLE_LOCK_FAIL(failCode,"获取锁的状态失败");


    public  String code;

    public  String message;


    FactoryEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
