package com.qiyuan.service;

import com.qiyuan.pojo.BikeOutStorgeInfo;

public interface IBikeOutStorgeService {

	public abstract BikeOutStorgeInfo getBikeOutInfoByBicycleNum(String bicycleNum);

	public abstract BikeOutStorgeInfo getBikeOutInfo(String bicycleNum);

	public abstract void addInfo(BikeOutStorgeInfo newInfo);

	public abstract Integer getQuantityToday(String supplierId, String bicycleModel, String bicycleType);

	public abstract Integer getFGQuantityToday(String supplierId);

	public abstract Integer getFGEQuantityToday(String supplierId);

	public abstract Integer getSGQuantityToday(String supplierId);

	public abstract Integer getSGEQuantityToday(String supplierId);

	public abstract Integer getQuantityTodayTotal(String supplierId);

	public abstract Integer getFGQuantity(String supplierId);

	public abstract Integer getFGEQuantity(String supplierId);

	public abstract Integer getSGQuantity(String supplierId);

	public abstract Integer getSGEQuantity(String supplierId);

	public abstract Integer getQuantityTotal(String supplierId);

	public abstract Integer getQuantity(String supplierId);

	public abstract Integer getEQuantity(String supplierId);

	public abstract void updateInfo(BikeOutStorgeInfo bikeOutStorgeInfo);

}
