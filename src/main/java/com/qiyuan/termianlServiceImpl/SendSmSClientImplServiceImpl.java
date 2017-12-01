package com.qiyuan.termianlServiceImpl;

import com.qiyuan.Base.BaseRepMessge;
import com.qiyuan.entity.GetTerminalDetailReqMessage;
import com.qiyuan.entity.LianTongSoapapiMessage;
import com.qiyuan.entity.Result;
import com.qiyuan.entity.SendSmSReqMessage;
import com.qiyuan.terminalService.SendSMSService;
import com.qiyuan.utils.ResultUtil;
import com.sun.xml.wss.ProcessingContext;
import com.sun.xml.wss.XWSSProcessor;
import com.sun.xml.wss.XWSSProcessorFactory;
import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.impl.callback.PasswordCallback;
import com.sun.xml.wss.impl.callback.UsernameCallback;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.soap.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Service("sendSMSService")
public class SendSmSClientImplServiceImpl extends BaseRepMessge implements SendSMSService {

    private  static Log LOGGER= LogFactory.getLog(SendSmSClientImplServiceImpl.class);


    /**
     * Constructor which initializes Soap Connection, messagefactory and ProcessorFactory
     *
     * @throws SOAPException
     * @throws MalformedURLException
     * @throws XWSSecurityException
     */
    public SendSmSClientImplServiceImpl()
            throws SOAPException, MalformedURLException, XWSSecurityException {
        connectionFactory = SOAPConnectionFactory.newInstance();
        messageFactory = MessageFactory.newInstance();
        processorFactory = XWSSProcessorFactory.newInstance();
        lianTongSoapapiMessage=new LianTongSoapapiMessage(new SendSmSReqMessage());
    }

    /**
     * This method creates a Terminal Request and sends back the SOAPMessage.
     * ICCID value is passed into this method
     *
     * @return SOAPMessage
     * @throws SOAPException
     */
    private SOAPMessage createTerminalRequest(String sentToIccId,String messageText,byte tpvp) throws SOAPException {
        SOAPMessage message = messageFactory.createMessage();
        message.getMimeHeaders().addHeader("SOAPAction",lianTongSoapapiMessage.getBaseRepMessge().getHeadPath());
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name terminalRequestName = createName(envelope,"SendSMSRequest");
        SOAPBodyElement terminalRequestElement = message.getSOAPBody()
                .addBodyElement(terminalRequestName);
        addPublicAttribute(envelope,terminalRequestElement);//添加公共的属性值
        Name sentToIccIdName = createName(envelope,"sentToIccid");
        SOAPElement sentToIccIdElement = terminalRequestElement.addChildElement(sentToIccIdName);
        sentToIccIdElement.setValue(sentToIccId);
        Name messageTextName = createName(envelope,"messageText");
        SOAPElement messageTextElement= terminalRequestElement.addChildElement(messageTextName);
        messageTextElement.setValue(messageText);
        Name tpvpName = createName(envelope,"tpvp");
        SOAPElement tpvpElement = terminalRequestElement.addChildElement(tpvpName);
        tpvpElement.setValue(Byte.toString(tpvp));
        return message;
    }

    public Result sendSMSService(String sentToIccId, String messageText, byte tpvp) throws Exception {
        SOAPMessage request = createTerminalRequest(sentToIccId,messageText,tpvp);
        request = secureMessage(request, LianTongSoapapiMessage.USER_NAME,LianTongSoapapiMessage.PASS_WORD);
        LOGGER.debug("request :"+request.getContentDescription());

        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, new URL(lianTongSoapapiMessage.getBaseRepMessge().getReqUrl()));
        LOGGER.debug("response :"+response.getContentDescription());
        if (!response.getSOAPBody().hasFault()) {
            return ResultUtil.success(writeTerminalResponse(response));
        } else {
            SOAPFault fault = response.getSOAPBody().getFault();
            return  ResultUtil.error(new Integer(fault.getFaultCode()),fault.getFaultString().toString());
        }
    }

    /**
     * Gets the terminal response.
     *
     * @param message
     * @throws SOAPException
     */
    private String writeTerminalResponse(SOAPMessage message) throws SOAPException {
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name terminalResponseName = createName(envelope,"SendSMSResponse");
        SOAPBodyElement terminalResponseElement = (SOAPBodyElement) message
                .getSOAPBody().getChildElements(terminalResponseName).next();
        Name terminal =createName(envelope,"smsMsgId");
        SOAPBodyElement terminalsElement = (SOAPBodyElement) terminalResponseElement.getChildElements(terminal).next();
        NodeList list = terminalsElement.getChildNodes();
        Node n = null;
        for (int i = 0; i < list.getLength(); i ++) {
            n = list.item(i);
            if ("smsMsgId".equalsIgnoreCase(n.getLocalName())){
                break;
            }
        }

        return  n.getTextContent();
    }
}
