/**
 * Copyright 2005 Jasper Systems, Inc. All rights reserved.
 *
 * This software code is the confidential and proprietary information of
 * Jasper Systems, Inc. ("Confidential Information"). Any unauthorized
 * review, use, copy, disclosure or distribution of such Confidential
 * Information is strictly prohibited.
 */
package com.qiyuan.termianlServiceImpl;


import com.qiyuan.Base.BaseRepMessge;
import com.qiyuan.entity.GetSMSDetailsReqMessage;
import com.qiyuan.entity.Result;
import com.qiyuan.terminalService.ApiClientConstantService;
import com.qiyuan.entity.GetTerminalDetailReqMessage;
import com.qiyuan.entity.LianTongSoapapiMessage;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: tonghengzhen
 * Date: 2017/10/20
 * Time: 10:07
 */

@Service("apiClientConstantService")
public class GetTerminalDetailsClientImplServiceImpl extends BaseRepMessge implements ApiClientConstantService{

    private  static Log LOGGER= LogFactory.getLog(GetTerminalDetailsClientImplServiceImpl.class);



    /**
     * Constructor which initializes Soap Connection, messagefactory and ProcessorFactory
     *
     * @throws SOAPException
     * @throws MalformedURLException
     * @throws XWSSecurityException
     */
    public GetTerminalDetailsClientImplServiceImpl()
            throws SOAPException, MalformedURLException, XWSSecurityException {
        connectionFactory = SOAPConnectionFactory.newInstance();
        messageFactory = MessageFactory.newInstance();
        processorFactory = XWSSProcessorFactory.newInstance();
        lianTongSoapapiMessage=new LianTongSoapapiMessage(new GetTerminalDetailReqMessage());
    }

    /**
     * This method creates a Terminal Request and sends back the SOAPMessage.
     * ICCID value is passed into this method
     *
     * @return SOAPMessage
     * @throws SOAPException
     */
    private SOAPMessage createTerminalRequest(String iccid) throws SOAPException {
        SOAPMessage message = messageFactory.createMessage();
        message.getMimeHeaders().addHeader("SOAPAction",lianTongSoapapiMessage.getBaseRepMessge().getHeadPath());
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name terminalRequestName =createName(envelope,"GetTerminalDetailsRequest");
        SOAPBodyElement terminalRequestElement = message.getSOAPBody()
                .addBodyElement(terminalRequestName);
        addPublicAttribute(envelope,terminalRequestElement);//添加公共的属性值
        Name iccids = createName(envelope,"iccids");
        SOAPElement iccidsElement = terminalRequestElement.addChildElement(iccids);
        Name iccidName = createName(envelope,"iccid");
        SOAPElement iccidElement = iccidsElement.addChildElement(iccidName);
        iccidElement.setValue(iccid);
        return message;
    }

    public Result callWebService(String iccid) throws Exception {
        SOAPMessage request = createTerminalRequest(iccid);
        request = secureMessage(request,LianTongSoapapiMessage.USER_NAME, LianTongSoapapiMessage.PASS_WORD);
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
        Name terminalResponseName = createName(envelope,"GetTerminalDetailsResponse");
        SOAPBodyElement terminalResponseElement = (SOAPBodyElement) message
                .getSOAPBody().getChildElements(terminalResponseName).next();
        Name terminals =createName(envelope,"terminals");
        Name terminal = createName(envelope,"terminal");
        SOAPBodyElement terminalsElement = (SOAPBodyElement) terminalResponseElement.getChildElements(terminals).next();
        SOAPBodyElement terminalElement = (SOAPBodyElement) terminalsElement.getChildElements(terminal).next();
        NodeList list = terminalElement.getChildNodes();
        Node n = null;
        for (int i = 0; i < list.getLength(); i ++) {
            n = list.item(i);
            if ("status".equalsIgnoreCase(n.getLocalName())){
                break;
            }
        }

       return  n.getTextContent();
    }
}

