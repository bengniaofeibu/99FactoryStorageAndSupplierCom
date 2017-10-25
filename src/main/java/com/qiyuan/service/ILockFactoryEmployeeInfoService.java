package com.qiyuan.service;

import java.util.List;

import com.qiyuan.pojo.LockFactoryEmployeeInfo;


public interface ILockFactoryEmployeeInfoService {

	public abstract LockFactoryEmployeeInfo getLockFactoryEmployeeInfoByRealName(String realName);
	
	public abstract LockFactoryEmployeeInfo getLockFactoryEmployeeInfo(String realName);

	public abstract LockFactoryEmployeeInfo getLockFactoryEmployeeInfoById(String id);
	
	public abstract void updateInfo(LockFactoryEmployeeInfo lockFactoryEmployeeInfo);
}
