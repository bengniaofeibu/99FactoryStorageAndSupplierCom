package com.qiyuan.service;

import com.qiyuan.pojo.BikeInfo;
import com.qiyuan.pojo.BikeUpdateInfo;

public interface IBikeService {

	public abstract BikeInfo getBikeInfoByBicycleNum(String bicycleNum);
	
	public abstract BikeInfo getBikeInfoByBluetoothMac(String bluetoothMac);

	public abstract void updateBikeInfo(BikeInfo bikeInfo);

	public abstract void addBikeInfo(BikeInfo info);

	public abstract BikeInfo getBikeInfoByBicycleNumAndBluetoothMac(String bicycleNum, String bluetoothMac);

	public abstract BikeInfo getBikeInfoBySimNo(String SimNo);

	Integer getBikecUnbundlingNum(String simNO);

	BikeInfo getBikeInfo(String simNo);

	BikeInfo getBikeInfoByUnknowNo(Integer unknowNo);

	void  recordBikeUpdateInfo(BikeUpdateInfo bikeUpdateInfo);

}
