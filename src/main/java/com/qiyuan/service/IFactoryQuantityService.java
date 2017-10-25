package com.qiyuan.service;

import com.qiyuan.pojo.FactoryQuantityInfo;

public interface IFactoryQuantityService {

	public abstract FactoryQuantityInfo getInfoBySupplierId(String supplierId);

	public abstract void updateInfo(FactoryQuantityInfo fqInfo);

	public abstract void addInfo(FactoryQuantityInfo newfqInfo);

}
