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
@ConfigurationProperties(prefix ="GetTerminalDetailReqMessage")
public class GetTerminalDetailReqMessage extends BaseRepMessge {
    private  String  headValue;
    private  String  requestMessageId;
    private  String  requestVersion;

    public String getHeadValue() {
        return headValue;
    }

    public void setHeadValue(String headValue) {
        this.headValue = headValue;
    }

    public String getRequestMessageId() {
        return requestMessageId;
    }

    public void setRequestMessageId(String requestMessageId) {
        this.requestMessageId = requestMessageId;
    }

    public String getRequestVersion() {
        return requestVersion;
    }

    public void setRequestVersion(String requestVersion) {
        this.requestVersion = requestVersion;
    }




    @Override
    public String toString() {
        return "GetTerminalDetailReqMessage{" +
                "headValue='" + headValue + '\'' +
                ", requestMessageId='" + requestMessageId + '\'' +
                ", requestVersion='" + requestVersion + '\'' +
                '}';
    }
}
