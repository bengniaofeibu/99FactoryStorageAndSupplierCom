package com.qiyuan.entity;

import com.qiyuan.Base.BaseRepMessge;

public class GetSMSDetailsReqMessage extends BaseRepMessge {


    public GetSMSDetailsReqMessage() {
        headPath=GET_SMS_DETAILS_PATH;
        reqUrl=SMS_URL;
    }

    @Override
    public String toString() {
        return "GetSMSDetailsReqMessage{" +
                "headPath='" + headPath + '\'' +
                ", reqUrl='" + reqUrl + '\'' +
                '}';
    }
}
