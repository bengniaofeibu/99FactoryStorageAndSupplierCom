package com.qiyuan.serviceImpl;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qiyuan.pojo.LockFactoryEmployeeInfo;
import com.qiyuan.service.ILockFactoryEmployeeInfoService;


@Service("lockFactoryEmployeeInfoService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class LockFactoryEmployeeInfoImpl extends BaseServiceImpl implements ILockFactoryEmployeeInfoService{



	@Override
	public LockFactoryEmployeeInfo getLockFactoryEmployeeInfoByRealName(String realName) {
		StringBuffer shql = new StringBuffer();
		shql.append("from LockFactoryEmployeeInfo where realName=:realName");
		String[] params = { "realName" };
		LockFactoryEmployeeInfo lockFactoryEmployeeInfo=(LockFactoryEmployeeInfo) dataDao.getFirstObjectViaParam(shql.toString(), params, realName);
		return lockFactoryEmployeeInfo;
	}

	@Override
	public LockFactoryEmployeeInfo getLockFactoryEmployeeInfo(String realName) {
		StringBuffer shql = new StringBuffer();
		shql.append("select new LockFactoryEmployeeInfo(id,realName,addTime,lockFactoryNo,factoryName,photoUrl)from LockFactoryEmployeeInfo where realName=:realName");
		String[] params = { "realName" };
		LockFactoryEmployeeInfo lockFactoryEmployeeInfo=(LockFactoryEmployeeInfo) dataDao.getFirstObjectViaParam(shql.toString(), params, realName);
		return lockFactoryEmployeeInfo;
	}

	@Override
	public LockFactoryEmployeeInfo getLockFactoryEmployeeInfoById(String id) {
		StringBuffer shql =new StringBuffer();
		shql.append("from LockFactoryEmployeeInfo where id =:id");
		String[] params ={"id"};
		LockFactoryEmployeeInfo lockFactoryEmployeeInfo= (LockFactoryEmployeeInfo) dataDao.getFirstObjectViaParam(shql.toString(),params,id);
		return  lockFactoryEmployeeInfo;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateInfo(LockFactoryEmployeeInfo lockFactoryEmployeeInfo) {
		dataDao.updateObject(lockFactoryEmployeeInfo);
		
	}


}
