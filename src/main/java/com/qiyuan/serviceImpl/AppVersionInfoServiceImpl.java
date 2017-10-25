package com.qiyuan.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qiyuan.pojo.AppVersionInfo;

import com.qiyuan.service.IAppVersionInfoService;
@Service("appVersionInfoService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class AppVersionInfoServiceImpl extends BaseServiceImpl implements IAppVersionInfoService{

	@Override
	public AppVersionInfo getAppInfoById(String appId) {
		StringBuffer shql = new StringBuffer();
		shql.append("from AppVersionInfo where id=:appId order by addtime desc");
		String[] params ={"appId"};
		AppVersionInfo info = (AppVersionInfo) dataDao.getFirstObjectViaParam(shql.toString(),params,appId);
		return info;
	}

	@Override
	public AppVersionInfo getInfoById(String appId){
		StringBuffer shql = new StringBuffer();
		shql.append("select new AppVersionInfo(versionId,addtime,remarks,url) from AppVersionInfo where id=:appId order by addtime desc");
		String[] params ={"appId"};
		AppVersionInfo info = (AppVersionInfo) dataDao.getFirstObjectViaParam(shql.toString(),params,appId);
		return info;
	}

}
