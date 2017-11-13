package com.qiyuan.entity;

import com.qiyuan.Base.BaseRepMessge;

public class SendSmSReqMessage extends BaseRepMessge {

    public SendSmSReqMessage() {
        headPath=SEND_SMS_LOCK_PATH;
        reqUrl=SMS_URL;
    }

    @Override
    public String toString() {
        return "SendSmSReqMessage{" +
                "headPath='" + headPath + '\'' +
                ", reqUrl='" + reqUrl + '\'' +
                '}';
    }
}
