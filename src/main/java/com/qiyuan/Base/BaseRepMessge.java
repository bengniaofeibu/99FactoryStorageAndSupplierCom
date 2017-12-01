package com.qiyuan.Base;

import com.qiyuan.entity.LianTongSoapapiMessage;
import com.sun.xml.wss.ProcessingContext;
import com.sun.xml.wss.XWSSProcessor;
import com.sun.xml.wss.XWSSProcessorFactory;
import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.impl.callback.PasswordCallback;
import com.sun.xml.wss.impl.callback.UsernameCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.soap.*;
import java.io.IOException;
import java.io.InputStream;
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

    protected Name createName(SOAPEnvelope envelope, String name) throws SOAPException {
        Name terminalRequestName = envelope.createName(name, LianTongSoapapiMessage.PREFIX, LianTongSoapapiMessage.NAME_SPACE_URI);
        return terminalRequestName;

    }

    /**
     * This method is used to add the security. This uses xwss:UsernameToken configuration and expects
     * Username and Password to be passes. SecurityPolicy.xml file should be in classpath.
     *
     * @param message
     * @param username
     * @param password
     * @return
     * @throws IOException
     * @throws XWSSecurityException
     */
    protected SOAPMessage secureMessage(SOAPMessage message, final String username, final String password)
            throws IOException, XWSSecurityException {

        CallbackHandler callbackHandler = callbacks -> {
            for (int i = 0; i < callbacks.length; i++) {
                if (callbacks[i] instanceof UsernameCallback) {
                    UsernameCallback callback = (UsernameCallback) callbacks[i];
                    callback.setUsername(username);
                } else if (callbacks[i] instanceof PasswordCallback) {
                    PasswordCallback callback = (PasswordCallback) callbacks[i];
                    callback.setPassword(password);
                } else {
                    throw new UnsupportedCallbackException(callbacks[i]);
                }
            }
        };
        InputStream policyStream = null;
        XWSSProcessor processor;
        try {
            policyStream = getClass().getClassLoader().getResourceAsStream("soap/securityPolicy.xml");
            processor = processorFactory.createProcessorForSecurityConfiguration(policyStream, callbackHandler);
        }
        finally {
            if (policyStream != null) {
                policyStream.close();
            }
        }
        ProcessingContext context = processor.createProcessingContext(message);
        return processor.secureOutboundMessage(context);
    }


    protected void  addPublicAttribute(SOAPEnvelope envelope,SOAPBodyElement terminalRequestElement) throws SOAPException {
        Name msgId = createName(envelope,"messageId");
        SOAPElement msgElement = terminalRequestElement.addChildElement(msgId);
        msgElement.setValue(LianTongSoapapiMessage.REQUEST_MESSAGE_ID);
        Name version =createName(envelope,"version");
        SOAPElement versionElement = terminalRequestElement.addChildElement(version);
        versionElement.setValue(LianTongSoapapiMessage.REQUEST_VERSION);
        Name license = createName(envelope,"licenseKey");
        SOAPElement licenseElement = terminalRequestElement.addChildElement(license);
        licenseElement.setValue(LianTongSoapapiMessage.REQUEST_LICENSE_KEY);
    }
}
