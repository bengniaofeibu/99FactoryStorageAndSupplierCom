package com.qiyuan.service;

import com.qiyuan.pojo.SupplierInfo;

import java.util.List;

public interface ISupplierService {

	public abstract SupplierInfo getSupplierInfoByNameAndPassword(String supplierName, String supplierPassword);

	public abstract SupplierInfo getSupplierBySupplierId(String supplierId);

	public abstract void updateSupplierInfo(SupplierInfo supplierInfo);

	List<String> getAllsupplieName();

}
