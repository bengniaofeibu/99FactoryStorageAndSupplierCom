package com.qiyuan.serviceImpl;

import com.qiyuan.pojo.ChangeBarcodeRecord;
import com.qiyuan.service.IChangeBarcodeRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("changeBarcodeRecordService")
public class ChangeBarcodeRecordImpl extends BaseServiceImpl implements IChangeBarcodeRecord {


    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    @Override
    public void addInfo(ChangeBarcodeRecord changeBarcodeRecord) {
            dataDao.addObject(changeBarcodeRecord);
    }
}
