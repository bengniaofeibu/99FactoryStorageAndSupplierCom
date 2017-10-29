package com.qiyuan.serviceImpl;

import com.qiyuan.pojo.SupplierInfo;
import com.qiyuan.service.ISupplierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("supplierService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class SupplierServiceImpl extends BaseServiceImpl implements ISupplierService {


	private  static  final  String GET_ALL_SUPPLIER_NAME="select info.supplier_name from t_supplier_info  as info where supplier_name!='锁厂换锁绑定'";

	@Override
	public SupplierInfo getSupplierInfoByNameAndPassword(String supplierName, String supplierPassword) {
		StringBuffer uhql = new StringBuffer();
		uhql.append("from SupplierInfo where supplierName =:supplierName and supplierPassword =:supplierPassword");
		String[] params = { "supplierName","supplierPassword" };
		SupplierInfo info = (SupplierInfo) dataDao.getFirstObjectViaParam(uhql.toString(), params, supplierName,supplierPassword);
		return info;
	}

	@Override
	public SupplierInfo getSupplierBySupplierId(String supplierId) {
		StringBuffer uhql = new StringBuffer();
		uhql.append("from SupplierInfo where id =:id");
		String[] params = { "id" };
		SupplierInfo info = (SupplierInfo) dataDao.getFirstObjectViaParam(uhql.toString(), params, supplierId);
		return info;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateSupplierInfo(SupplierInfo supplierInfo) {
		dataDao.updateObject(supplierInfo);
		
	}

	@Override
	public List<String> getAllsupplieName() {
		return (List<String>) dataDao.getALLData(GET_ALL_SUPPLIER_NAME);
	}

}
