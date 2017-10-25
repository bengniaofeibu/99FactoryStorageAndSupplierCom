package com.qiyuan.utils;


import com.qiyuan.entity.Result;
import com.qiyuan.enums.LockReponseEnum;

public class ResultUtil {
    public static Result success(Object object){
        Result<Object> result = new Result<>();
        result.setCode(200);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result success(){
        Result result = new Result();
        result.setCode(200);
        result.setMsg("成功");
        return result;
    }

    public static Result error(Integer code, String msg){
        Result<Object> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result error(String msg){
        Result<Object> result = new Result<>();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }
}
