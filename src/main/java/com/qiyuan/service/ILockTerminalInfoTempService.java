package com.qiyuan.service;


import com.qiyuan.pojo.LockTerminalInfoTemp;

public interface ILockTerminalInfoTempService {
    public abstract LockTerminalInfoTemp getInfoByMac(String mac);

    public abstract void updateLockTerminalInfo(LockTerminalInfoTemp info);

    public abstract void addInfo(LockTerminalInfoTemp newLockTerminalInfo);

    public abstract LockTerminalInfoTemp getInfoBySimNo(String simNo);
}
