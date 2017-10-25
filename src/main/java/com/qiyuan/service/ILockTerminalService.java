package com.qiyuan.service;

import com.qiyuan.pojo.LockTerminalInfo;

public interface ILockTerminalService {

	public abstract LockTerminalInfo getInfoByMac(String mac);

	public abstract void updateLockTerminalInfo(LockTerminalInfo info);

	public abstract void addInfo(LockTerminalInfo newLockTerminalInfo);

	public abstract LockTerminalInfo getInfoBySimNo(String simNo);

}
