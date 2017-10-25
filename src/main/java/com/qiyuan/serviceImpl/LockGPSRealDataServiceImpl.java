package com.qiyuan.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qiyuan.pojo.LockGPSRealData;
import com.qiyuan.service.ILockGPSRealDataService;

@Service("lockGPRSRealDataService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class LockGPSRealDataServiceImpl extends BaseServiceImpl implements ILockGPSRealDataService{

	@Override
	public LockGPSRealData getLockGpsRealDataInfo(String simNo) {
		StringBuffer shql = new StringBuffer();
		shql.append("from LockGPSRealData where simNo =:simNo");
		String[] params = { "simNo" };
		LockGPSRealData info = (LockGPSRealData) dataDao.getFirstObjectViaParam(shql.toString(), params, simNo);
		return info;
	}

}
