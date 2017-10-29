/**
 * Copyright 2005 Jasper Systems, Inc. All rights reserved.
 *
 * This software code is the confidential and proprietary information of
 * Jasper Systems, Inc. ("Confidential Information"). Any unauthorized
 * review, use, copy, disclosure or distribution of such Confidential
 * Information is strictly prohibited.
 */
package com.qiyuan.termianlServiceImpl;


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
public class GetTerminalDetailsClientImplServiceImpl implements ApiClientConstantService {

    private  static Log LOGGER= LogFactory.getLog(GetTerminalDetailsClientImplServiceImpl.class);

    @Autowired
    private LianTongSoapapiMessage lianTongSoapapiMessage;

    private SOAPConnectionFactory connectionFactory;
    private MessageFactory messageFactory;
    private XWSSProcessorFactory processorFactory;

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
        message.getMimeHeaders().addHeader("SOAPAction",lianTongSoapapiMessage.getHeadPathValue()
                .append(lianTongSoapapiMessage.getGetTerminalDetailReqMessage().getHeadValue()).toString());
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name terminalRequestName = envelope.createName("GetTerminalDetailsRequest", lianTongSoapapiMessage.getPrefix(), lianTongSoapapiMessage.getNamespaceUri());
        SOAPBodyElement terminalRequestElement = message.getSOAPBody()
                .addBodyElement(terminalRequestName);
        Name msgId = envelope.createName("messageId", lianTongSoapapiMessage.getPrefix(), lianTongSoapapiMessage.getNamespaceUri());
        SOAPElement msgElement = terminalRequestElement.addChildElement(msgId);
        msgElement.setValue(lianTongSoapapiMessage.getGetTerminalDetailReqMessage().getRequestMessageId());
        Name version = envelope.createName("version", lianTongSoapapiMessage.getPrefix(), lianTongSoapapiMessage.getNamespaceUri());
        SOAPElement versionElement = terminalRequestElement.addChildElement(version);
        versionElement.setValue(lianTongSoapapiMessage.getGetTerminalDetailReqMessage().getRequestVersion());
        Name license = envelope.createName("licenseKey", lianTongSoapapiMessage.getPrefix(), lianTongSoapapiMessage.getNamespaceUri());
        SOAPElement licenseElement = terminalRequestElement.addChildElement(license);
        licenseElement.setValue(lianTongSoapapiMessage.getRequestLicenseKey());
        Name iccids = envelope.createName("iccids", lianTongSoapapiMessage.getPrefix(), lianTongSoapapiMessage.getNamespaceUri());
        SOAPElement iccidsElement = terminalRequestElement.addChildElement(iccids);
        Name iccidName = envelope.createName("iccid", lianTongSoapapiMessage.getPrefix(), lianTongSoapapiMessage.getNamespaceUri());
        SOAPElement iccidElement = iccidsElement.addChildElement(iccidName);
        iccidElement.setValue(iccid);
        return message;
    }

    @Override
    public Result callWebService(String iccid) throws SOAPException, IOException, XWSSecurityException, Exception {
        String terminalStatus="";
        SOAPMessage request = createTerminalRequest(iccid);
        request = secureMessage(request, lianTongSoapapiMessage.getUserName(), lianTongSoapapiMessage.getPassWord());
        LOGGER.debug("request :"+request.getContentDescription());

        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, new URL(lianTongSoapapiMessage.getReqUrl()));
        LOGGER.debug("response :"+response.getContentDescription());
        if (!response.getSOAPBody().hasFault()) {
            terminalStatus=writeTerminalResponse(response);
        } else {
            SOAPFault fault = response.getSOAPBody().getFault();
//            setResult(response, resultMap);
            return  ResultUtil.error(new Integer(fault.getFaultCode()),fault.getFaultString().toString());
        }
        return ResultUtil.success(terminalStatus);
    }

    /**
     * Gets the terminal response.
     *
     * @param message
     * @throws SOAPException
     */
    private String writeTerminalResponse(SOAPMessage message) throws SOAPException {
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name terminalResponseName = envelope.createName("GetTerminalDetailsResponse", lianTongSoapapiMessage.getPrefix(), lianTongSoapapiMessage.getNamespaceUri());
        SOAPBodyElement terminalResponseElement = (SOAPBodyElement) message
                .getSOAPBody().getChildElements(terminalResponseName).next();
        String terminalValue = terminalResponseElement.getTextContent();
        Name terminals = envelope.createName("terminals", lianTongSoapapiMessage.getPrefix(), lianTongSoapapiMessage.getNamespaceUri());
        Name terminal = envelope.createName("terminal", lianTongSoapapiMessage.getPrefix(), lianTongSoapapiMessage.getNamespaceUri());
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
    private SOAPMessage secureMessage(SOAPMessage message, final String username, final String password)
            throws IOException, XWSSecurityException {
        CallbackHandler callbackHandler = new CallbackHandler() {
            @Override
            public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
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
            }
        };
        InputStream policyStream = null;
        XWSSProcessor processor = null;
//        try {
//            policyStream = getClass().getClassLoader().getResourceAsStream("soap/securityPolicy.xml");
//            processor = processorFactory.createProcessorForSecurityConfiguration(policyStream, callbackHandler);
//        }
//        finally {
//            if (policyStream != null) {
//                policyStream.close();
//            }
//        }
        ProcessingContext context = processor.createProcessingContext(message);
        return processor.secureOutboundMessage(context);
    }

    /**
     * Main program. Usage : TerminalClient <username> <password>
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // Apitest URL. See "Get WSDL Files" in the API documentation for Production URL.
        String url = "https://api.10646.cn/ws/service/terminal";
//        if (args.length != 4) {
//            System.out.println("usage: GetTerminalDetailsClientImplServiceImpl <license-key> <username> <password> <iccid>");
//            System.exit(-1);
//        }
        GetTerminalDetailsClientImplServiceImpl terminalClient = new GetTerminalDetailsClientImplServiceImpl();
//        terminalClient.callWebService("89860617030074266530");
    }
}

