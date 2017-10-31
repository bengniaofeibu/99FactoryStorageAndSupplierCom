package com.qiyuan.serviceImpl;

import com.qiyuan.pojo.BikeUpdateInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qiyuan.pojo.BikeInfo;
import com.qiyuan.service.IBikeService;

@Service("bikeService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class BikeServiceImpl extends BaseServiceImpl implements IBikeService{


//	private  static  final  String BIKEC_UNBUNDLING_NUM="select count(1) from CancellationCitynoInfo where simNo=?";

	private  static  final  String BIKEC_UNBUNDLING_NUM="select count(1) from CancellationCitynoInfo where preCityNo=?";

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

	@Override
	public BikeInfo getBikeInfo(String simNo) {
		StringBuffer shql=new StringBuffer();
		shql.append("select new BikeInfo(bicycleNo,addTime,simNo,gprsNo)from BikeInfo where simNo=:simNo");
		String[] params = { "simNo" };
		BikeInfo info= (BikeInfo) dataDao.getFirstObjectViaParam(shql.toString(),params,simNo);
		return info;
	}

	@Override
	public BikeInfo getBikeInfoByUnknowNo(Integer unknowNo) {
		StringBuffer shql=new StringBuffer();
		shql.append("select new BikeInfo(bicycleNo,addTime,simNo,gprsNo)from BikeInfo where bicycleNo=:unknowNo");
		String[] params = { "unknowNo" };
		BikeInfo info= (BikeInfo) dataDao.getFirstObjectViaParam(shql.toString(),params,unknowNo);
		return info;
	}

	/**
	 * 根据simno查出车辆是否解绑
	 * @param simNO
 	 * @return  0没有解绑 大于0 已经解绑
	 */
	@Override
	public Integer getBikecUnbundlingNum(String simNO) {
		return dataDao.getInfoCount(BIKEC_UNBUNDLING_NUM,simNO);
	}


	/**
	 * 记录车辆更新信息
	 * @param bikeUpdateInfo
	 */
	@Override
	public void recordBikeUpdateInfo(BikeUpdateInfo bikeUpdateInfo) {
        dataDao.addObject(bikeUpdateInfo);
	}
}
