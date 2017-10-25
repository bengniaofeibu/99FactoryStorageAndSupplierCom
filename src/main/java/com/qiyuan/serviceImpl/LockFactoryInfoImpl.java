package com.qiyuan.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qiyuan.pojo.LockFactoryInfo;
import com.qiyuan.service.ILockFactoryInfoService;

@Service("lockFactoryInfoService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class LockFactoryInfoImpl extends BaseServiceImpl implements ILockFactoryInfoService{

	@Override
	public LockFactoryInfo getLockFactoryInfoByLockFactoryNo(String lockFactoryNo) {
		StringBuffer shql = new StringBuffer();
		shql.append("from LockFactoryInfo where lockFactoryNo=:lockFactoryNo");
		String[] params = {"lockFactoryNo"};
		LockFactoryInfo lockFactoryInfo=(LockFactoryInfo) dataDao.getFirstObjectViaParam(shql.toString(), params, lockFactoryNo);
		return lockFactoryInfo;
	}
}
