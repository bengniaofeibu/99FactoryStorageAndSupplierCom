package com.qiyuan.termianlServiceImpl;

import com.qiyuan.Base.BaseRepMessge;
import com.qiyuan.entity.GetSMSDetailsReqMessage;
import com.qiyuan.entity.LianTongSoapapiMessage;
import com.qiyuan.entity.Result;
import com.qiyuan.terminalService.GetSMSDetailsService;
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

import javax.annotation.Resource;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.soap.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


@Service("getSMSDetailsService")
public class GetSMSDetailsClientServiceImpl extends BaseRepMessge implements GetSMSDetailsService {


    private  static Log LOGGER= LogFactory.getLog(GetTerminalDetailsClientImplServiceImpl.class);


    /**
     * Constructor which initializes Soap Connection, messagefactory and ProcessorFactory
     *
     * @throws SOAPException
     * @throws MalformedURLException
     * @throws XWSSecurityException
     */
    public GetSMSDetailsClientServiceImpl()
            throws SOAPException, MalformedURLException, XWSSecurityException {
        connectionFactory = SOAPConnectionFactory.newInstance();
        messageFactory = MessageFactory.newInstance();
        processorFactory = XWSSProcessorFactory.newInstance();
        lianTongSoapapiMessage=new LianTongSoapapiMessage(new GetSMSDetailsReqMessage());
    }

    /**
     * This method creates a Terminal Request and sends back the SOAPMessage.
     * ICCID value is passed into this method
     *
     * @return SOAPMessage
     * @throws SOAPException
     */
    private SOAPMessage createTerminalRequest(String smsMsgId) throws SOAPException {
        SOAPMessage message = messageFactory.createMessage();
        message.getMimeHeaders().addHeader("SOAPAction",lianTongSoapapiMessage.getBaseRepMessge().getHeadPath());
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name terminalRequestName = createName(envelope,"GetSMSDetailsRequest");
        SOAPBodyElement terminalRequestElement = message.getSOAPBody()
                .addBodyElement(terminalRequestName);
        Name msgId = createName(envelope,"messageId");
        SOAPElement msgElement = terminalRequestElement.addChildElement(msgId);
        msgElement.setValue(LianTongSoapapiMessage.REQUEST_MESSAGE_ID);
        Name version =createName(envelope,"version");
        SOAPElement versionElement = terminalRequestElement.addChildElement(version);
        versionElement.setValue(LianTongSoapapiMessage.REQUEST_VERSION);
        Name license = createName(envelope,"licenseKey");
        SOAPElement licenseElement = terminalRequestElement.addChildElement(license);
        licenseElement.setValue(LianTongSoapapiMessage.REQUEST_LICENSE_KEY);
        Name smsMsgIds =createName(envelope,"smsMsgIds");
        SOAPElement smsMsgIdsElement = terminalRequestElement.addChildElement(smsMsgIds);
        Name smsMsgIdName = createName(envelope,"smsMsgId");
        SOAPElement smsMsgIdElement = smsMsgIdsElement.addChildElement(smsMsgIdName);
        smsMsgIdElement.setValue(smsMsgId);
        return message;
    }

    @Override
    public Result getSMSDetails(String iccid) throws  Exception {
        String terminalStatus="";
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
        Name terminalResponseName = createName(envelope,"GetSMSDetailsResponse");
        SOAPBodyElement terminalResponseElement = (SOAPBodyElement) message
                .getSOAPBody().getChildElements(terminalResponseName).next();
        Name terminals = createName(envelope,"smsMessages");
        Name terminal = createName(envelope,"smsMessage");
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
}
