package com.qiyuan.serviceImpl;

import com.qiyuan.pojo.FactoryQuantityInfo;
import com.qiyuan.service.IFactoryQuantityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("factoryQuantityService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class FactoryQuantityServiceImpl extends BaseServiceImpl implements IFactoryQuantityService {

	@Override
	public FactoryQuantityInfo getInfoBySupplierId(String supplierId) {
		StringBuffer uhql = new StringBuffer();
		uhql.append("from FactoryQuantityInfo where supplierId =:supplierId");
		String[] params = { "supplierId" };
		FactoryQuantityInfo info = (FactoryQuantityInfo) dataDao.getFirstObjectViaParam(uhql.toString(), params, supplierId);
		return info;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateInfo(FactoryQuantityInfo fqInfo) {
		dataDao.updateObject(fqInfo);
		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addInfo(FactoryQuantityInfo newfqInfo) {
		dataDao.addObject(newfqInfo);
		
	}

}
