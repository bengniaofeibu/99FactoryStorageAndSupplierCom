package com.qiyuan.terminalService;

import com.qiyuan.entity.Result;

public interface GetSMSDetailsService {

    Result getSMSDetails(String smsid) throws  Exception;
}
