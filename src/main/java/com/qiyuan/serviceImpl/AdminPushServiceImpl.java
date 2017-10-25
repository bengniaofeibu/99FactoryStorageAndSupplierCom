package com.qiyuan.serviceImpl;

import com.qiyuan.pojo.AdminPushInfo;
import com.qiyuan.service.IAdminPushService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("adminPushService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class AdminPushServiceImpl extends BaseServiceImpl implements IAdminPushService {

	 private  static Log logger= LogFactory.getLog(AdminPushServiceImpl.class);


	@Override
	public AdminPushInfo getPushInfoByAdminId(String id) {
		StringBuffer ahql = new StringBuffer();
		ahql.append("from AdminPushInfo where adminId =:adminId");
		String[] params = { "adminId" };
		AdminPushInfo adminPushInfo = (AdminPushInfo) dataDao.getFirstObjectViaParam(ahql.toString(), params, id);
		return adminPushInfo;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updatePushInfo(AdminPushInfo adminPushInfo) {
		dataDao.updateObject(adminPushInfo);
		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addPushInfo(AdminPushInfo newAdminPushInfo) {
		dataDao.addObject(newAdminPushInfo);
		
	}
}
