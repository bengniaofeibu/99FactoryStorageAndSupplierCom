package com.qiyuan.Base;

import com.qiyuan.entity.LianTongSoapapiMessage;
import com.sun.xml.wss.XWSSProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.soap.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: tonghengzhen
 * Date: 2017/10/20
 * Time: 10:07
 */
@Component
public abstract class BaseRepMessge implements Serializable{

    private static  final  String BATH_PATH="http://api.jasperwireless.com/ws/service";

    private static  final  String BATH_URL="https://api.10646.cn/ws/service";

    protected static  final  String SMS_URL=BATH_URL+"/Sms";

    protected static  final  String TERMINAL_URL=BATH_URL+"/terminal;";

    protected static final String GET_SMS_DETAILS_PATH=BATH_PATH+"/sms/GetSMSDetails";

    protected static final String SEND_SMS_LOCK_PATH=BATH_PATH+"/sms/SendSMS";

    protected static final String GET_Terminal_Detail_PATH=BATH_PATH+"/terminal/GetTerminalDetails";


    protected LianTongSoapapiMessage lianTongSoapapiMessage;

    protected SOAPConnectionFactory connectionFactory;

    protected MessageFactory messageFactory;

    protected XWSSProcessorFactory processorFactory;

    protected   String  headPath;
    protected  String  reqUrl;

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    protected static Name createName(SOAPEnvelope envelope, String name) throws SOAPException {
        Name terminalRequestName = envelope.createName(name, LianTongSoapapiMessage.PREFIX, LianTongSoapapiMessage.NAME_SPACE_URI);
        return terminalRequestName;

    }
}
