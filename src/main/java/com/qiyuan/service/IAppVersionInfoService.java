package com.qiyuan.service;

import com.qiyuan.pojo.AppVersionInfo;

public interface IAppVersionInfoService {
	
	public abstract AppVersionInfo getAppInfoById(String appId);

	public abstract  AppVersionInfo getInfoById(String appId);

}
