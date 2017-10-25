package com.qiyuan.service;

import com.qiyuan.pojo.AdminPushInfo;

public interface IAdminPushService {

	public abstract AdminPushInfo getPushInfoByAdminId(String id);

	public abstract void updatePushInfo(AdminPushInfo adminPushInfo);

	public abstract void addPushInfo(AdminPushInfo newAdminPushInfo);

}
