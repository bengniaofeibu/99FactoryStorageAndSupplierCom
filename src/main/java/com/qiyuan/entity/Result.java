package com.qiyuan.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Result<T> {
    @ApiModelProperty(required = true, name = "code", value = "响应码")
    private int code = 200;
    @ApiModelProperty(required = true, name = "msg", value = "响应消息")
    private String msg = "成功";
    @ApiModelProperty(required = true, name = "data", value = "数据体")
    private T data = null;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
