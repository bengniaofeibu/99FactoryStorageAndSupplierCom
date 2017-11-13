package com.qiyuan.terminalService;

import com.qiyuan.entity.Result;

public interface SendSMSService {

    Result sendSMSService(String sentToIccId, String messageText, byte tpvp) throws Exception;
}
