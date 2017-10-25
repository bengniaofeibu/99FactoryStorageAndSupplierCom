/**
 * Copyright 2005 Jasper Systems, Inc. All rights reserved.
 *
 * This software code is the confidential and proprietary information of
 * Jasper Systems, Inc. ("Confidential Information"). Any unauthorized
 * review, use, copy, disclosure or distribution of such Confidential
 * Information is strictly prohibited.
 */
package com.qiyuan.terminalService;

import com.qiyuan.entity.Result;
import com.sun.xml.wss.XWSSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.SOAPException;
import java.io.IOException;

/**
 * @author Zhongling Li
 * @version $Id: //depot/jasper_dev/module/ProvisionApp/web/secure/apidoc/java/com/jasperwireless/ws/client/sample/ApiClientConstantService.java#1 $
 */
public interface ApiClientConstantService {


    Result callWebService(String iccid) throws SOAPException, IOException, XWSSecurityException, Exception;
}
