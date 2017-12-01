package com.qiyuan.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qiyuan.pojo.LockTerminalInfo;
import com.qiyuan.service.ILockTerminalService;

@Service("lockTerminalService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class LockTerminalServiceImpl extends BaseServiceImpl implements ILockTerminalService{

	@Override
	public LockTerminalInfo getInfoByMac(String bluetoothMac) {
		StringBuffer shql = new StringBuffer();
		shql.append("from LockTerminalInfo where bluetoothMac =:bluetoothMac");
		String[] params = { "bluetoothMac" };
		LockTerminalInfo info = (LockTerminalInfo) dataDao.getFirstObjectViaParam(shql.toString(), params, bluetoothMac);
		return info;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateLockTerminalInfo(LockTerminalInfo info) {
		dataDao.updateObject(info);
		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addInfo(LockTerminalInfo newLockTerminalInfo) {
		dataDao.addObject(newLockTerminalInfo);
		
	}

	@Override
	public LockTerminalInfo getInfoBySimNo(String simNo) {
		StringBuffer shql = new StringBuffer();
		shql.append("from LockTerminalInfo where simNo =:simNo ");
		String[] params = { "simNo" };
		LockTerminalInfo info = (LockTerminalInfo) dataDao.getFirstObjectViaParam(shql.toString(), params, simNo);
		return info;
	}

}
