package com.qiyuan.enums;

public enum FactoryEnum implements EnumService{

    BICYCLE_NUM_ERROR(failCode,"没有此类型车辆编号"),

    BICYCLE_NUM_NOT_BINDING(failCode,"此设备没绑定"),

    GET_SIM_NO_INFO_OK(okCode,"获取设备信息成功"),

    NOT_FOUND_SIM_NO_OK(failCode,"无此设备信息"),

    SIM_NO_ALREADY_BINDING(failCode,"此设备已绑定"),

    SIM_NO_ERROR(failCode,"非法设备ID"),

    BARCODE_URL_ERROR(failCode,"非法二维码"),

    CMD_ERROR(failCode,"非法指令"),

    GET_BICK_SUPPLIER_NAME_OK(okCode,"获取厂家成功"),

    GET_BICK_SUPPLIER_NAME_UNFOUND(failCode,"该车辆不存在"),

    CHANGE_LOCK_OK(okCode,"换锁绑定成功"),

    OPEN_LOCK_OK(okCode,"指令发送成功"),

    OPEN_LOCK_FAIL(failCode,"指令发送失败"),

    DEVICE_CANNOT_SEND_SMS(failCode,"该车辆的ICCID不支持发短信"),

    GET_CANCELLATION_LOCK_INFO_OK(okCode,"获取注销锁信息成功"),

    GET_CANCELLATION_LOCK_INFO_FAIL(failCode,"获取注销锁信息失败"),

    GET_BIKE_INFO_FAIL(failCode,"车辆未被注销"),

    USER_NOT_FOUND_FAIL(failCode,"用户不存在");


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
