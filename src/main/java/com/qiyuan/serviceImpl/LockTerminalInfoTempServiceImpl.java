package com.qiyuan.serviceImpl;


import com.qiyuan.pojo.LockTerminalInfoTemp;
import com.qiyuan.service.ILockTerminalInfoTempService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("lockTerminalInfoTempService")
public class LockTerminalInfoTempServiceImpl extends BaseServiceImpl implements ILockTerminalInfoTempService{
    @Override
    public LockTerminalInfoTemp getInfoByMac(String mac) {
        StringBuffer shql=new StringBuffer();
        shql.append("from LockTerminalInfoTemp where bluetoothMac =:mac");
        String[] params={"mac"};
        LockTerminalInfoTemp info= (LockTerminalInfoTemp) dataDao.getFirstObjectViaParam(shql.toString(),params,mac);
        return info;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateLockTerminalInfo(LockTerminalInfoTemp info) {
        dataDao.updateObject(info);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void addInfo(LockTerminalInfoTemp newLockTerminalInfo) {
        dataDao.addObject(newLockTerminalInfo);
    }

    @Override
    public LockTerminalInfoTemp getInfoBySimNo(String simNo) {
        StringBuffer shql=new StringBuffer();
        shql.append("from LockTerminalInfoTemp where simNo =:simNo");
        String[] params={"simNo"};
        LockTerminalInfoTemp info= (LockTerminalInfoTemp) dataDao.getFirstObjectViaParam(shql.toString(),params,simNo);
        return info;
    }
}
