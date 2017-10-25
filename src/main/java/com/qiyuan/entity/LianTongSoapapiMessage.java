package com.qiyuan.entity;

import com.qiyuan.Base.BaseRepMessge;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: tonghengzhen
 * Date: 2017/10/20
 * Time: 9:59
 */
@Component
@ConfigurationProperties(prefix = "LianTongSoapapiMessage")
public class LianTongSoapapiMessage {
   private String userName;
   private String passWord;
   private String headPath;
   private String requestLicenseKey;
   private String reqUrl;
   private String namespaceUri;
   private String prefix;
   private GetTerminalDetailReqMessage getTerminalDetailReqMessage;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String assWord) {
        this.passWord = assWord;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getRequestLicenseKey() {
        return requestLicenseKey;
    }

    public void setRequestLicenseKey(String requestLicenseKey) {
        this.requestLicenseKey = requestLicenseKey;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getNamespaceUri() {
        return namespaceUri;
    }

    public void setNamespaceUri(String namespaceUri) {
        this.namespaceUri = namespaceUri;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    public GetTerminalDetailReqMessage getGetTerminalDetailReqMessage() {
        return getTerminalDetailReqMessage;
    }

    public void setGetTerminalDetailReqMessage(GetTerminalDetailReqMessage getTerminalDetailReqMessage) {
        this.getTerminalDetailReqMessage = getTerminalDetailReqMessage;
    }

    @Override
    public String toString() {
        return "LianTongSoapapiMessage{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", headPath='" + headPath + '\'' +
                ", requestLicenseKey='" + requestLicenseKey + '\'' +
                ", reqUrl='" + reqUrl + '\'' +
                ", namespaceUri='" + namespaceUri + '\'' +
                ", prefix='" + prefix + '\'' +
                ", getTerminalDetailReqMessage=" + getTerminalDetailReqMessage +
                '}';
    }

    public StringBuilder getHeadPathValue(){
         return  new StringBuilder(getHeadPath());
    }
}
