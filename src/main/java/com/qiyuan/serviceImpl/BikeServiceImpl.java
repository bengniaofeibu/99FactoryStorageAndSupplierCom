package com.qiyuan.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qiyuan.pojo.BikeInfo;
import com.qiyuan.service.IBikeService;

@Service("bikeService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class BikeServiceImpl extends BaseServiceImpl implements IBikeService{

	@Override
	public BikeInfo getBikeInfoByBicycleNum(String bicycleNum) {
		StringBuffer shql = new StringBuffer();
		shql.append("from BikeInfo where bicycleNo =:bicycleNo");
		String[] params = { "bicycleNo" };
		BikeInfo info = (BikeInfo) dataDao.getFirstObjectViaParam(shql.toString(), params, Integer.parseInt(bicycleNum));
		return info;
	}
	
	@Override
	public BikeInfo getBikeInfoByBicycleNumAndBluetoothMac(String bicycleNum,String bluetoothMac) {
		StringBuffer shql = new StringBuffer();
		shql.append("from BikeInfo where bicycleNo =:bicycleNo or bluetoothMac =:bluetoothMac");
		String[] params = { "bicycleNo","bluetoothMac" };
		BikeInfo info = (BikeInfo) dataDao.getFirstObjectViaParam(shql.toString(), params, Integer.parseInt(bicycleNum),bluetoothMac);
		return info;
	}


	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateBikeInfo(BikeInfo bikeInfo) {
		dataDao.updateObject(bikeInfo);
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addBikeInfo(BikeInfo info) {
		dataDao.addObject(info);
		
	}

	@Override
	public BikeInfo getBikeInfoByBluetoothMac(String bluetoothMac) {
		StringBuffer shql = new StringBuffer();
		shql.append("from BikeInfo where bluetoothMac =:bluetoothMac");
		String[] params = { "bluetoothMac" };
		BikeInfo info = (BikeInfo) dataDao.getFirstObjectViaParam(shql.toString(), params, bluetoothMac);
		return info;
	}

	@Override
	public BikeInfo getBikeInfoBySimNo(String SimNo) {
		StringBuffer shql = new StringBuffer();
		shql.append("from BikeInfo where simNo=:simNo");
		String[] params = { "simNo" };
		BikeInfo info = (BikeInfo) dataDao.getFirstObjectViaParam(shql.toString(), params, SimNo);
		return info;
	}

}
