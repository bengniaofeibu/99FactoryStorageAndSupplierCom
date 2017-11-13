package com.qiyuan.entity;

import com.qiyuan.Base.BaseRepMessge;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: tonghengzhen
 * Date: 2017/10/20
 * Time: 10:07
 */
//@ConfigurationProperties(prefix ="GetTerminalDetailReqMessage")
public class GetTerminalDetailReqMessage extends BaseRepMessge {


    public GetTerminalDetailReqMessage() {
        headPath=GET_Terminal_Detail_PATH;
        reqUrl=TERMINAL_URL;
    }

    @Override
    public String toString() {
        return "GetTerminalDetailReqMessage{" +
                "headPath='" + headPath + '\'' +
                ", reqUrl='" + reqUrl + '\'' +
                '}';
    }
}
