package com.qiyuan.serviceImpl;

import com.qiyuan.pojo.BikeOutStorgeInfo;
import com.qiyuan.service.IBikeOutStorgeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("bikeOutStorgeService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class BikeOutStorgeServiceImpl extends BaseServiceImpl implements IBikeOutStorgeService{

	@Override
	public BikeOutStorgeInfo getBikeOutInfoByBicycleNum(String bicycleNum) {
		StringBuffer uhql = new StringBuffer();
		uhql.append("from BikeOutStorgeInfo where bicycleNo =:bicycleNo and delFlag = '0' order by addTime desc");
		String[] params = { "bicycleNo" };
		BikeOutStorgeInfo info = (BikeOutStorgeInfo) dataDao.getFirstObjectViaParam(uhql.toString(), params, Integer.parseInt(bicycleNum));
		return info;
	}

	@Override
	public BikeOutStorgeInfo getBikeOutInfo(String bicycleNum) {
		StringBuffer uhql = new StringBuffer();
		uhql.append("from BikeOutStorgeInfo where bicycleNo =:bicycleNo order by addTime desc");
		String[] params = { "bicycleNo" };
		BikeOutStorgeInfo info = (BikeOutStorgeInfo) dataDao.getFirstObjectViaParam(uhql.toString(), params, Integer.parseInt(bicycleNum));
		return info;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addInfo(BikeOutStorgeInfo newInfo) {
		dataDao.addObject(newInfo);
		
	}

	@Override
	public Integer getQuantityToday(String supplierId,String bicycleModel,String bicycleType) {
		String sql="SELECT COUNT(*) FROM t_bike_out_storge_info t WHERE TO_DAYS(t.add_time)=TO_DAYS(NOW()) and t.bicycle_model='"+bicycleModel+"' and t.bicycle_type='"+bicycleType+"' and t.del_flag = '0' and t.supplier_name in (SELECT s.supplier_name FROM t_supplier_info s where s.id='"+supplierId+"')";
		String res = dataDao.executesBySql(sql);
		if (res!=""){
			return Integer.parseInt(res);
		}else {
			return -1;
		}

	}

	@Override
	public Integer getFGQuantityToday(String supplierId) {
		String sql="SELECT COUNT(*) FROM t_bike_out_storge_info t WHERE TO_DAYS(t.add_time)=TO_DAYS(NOW()) and t.bicycle_model='0' and t.bicycle_type='5' and t.del_flag = '0' and t.supplier_name in (SELECT s.supplier_name FROM t_supplier_info s where s.id='"+supplierId+"')";
		String res = dataDao.executesBySql(sql);
		if (res!=""){
			return Integer.parseInt(res);
		}else {
			return -1;
		}
	}

	@Override
	public Integer getFGEQuantityToday(String supplierId) {
		String sql="SELECT COUNT(*) FROM t_bike_out_storge_info t WHERE TO_DAYS(t.add_time)=TO_DAYS(NOW()) and t.bicycle_model='0' and t.bicycle_type='1' and t.del_flag = '0' and t.supplier_name in (SELECT s.supplier_name FROM t_supplier_info s where s.id='"+supplierId+"')";
		String res = dataDao.executesBySql(sql);
		if (res!=""){
			return Integer.parseInt(res);
		}else {
			return -1;
		}
	}

	@Override
	public Integer getSGQuantityToday(String supplierId) {
		String sql="SELECT COUNT(*) FROM t_bike_out_storge_info t WHERE TO_DAYS(t.add_time)=TO_DAYS(NOW()) and t.bicycle_model='1' and t.bicycle_type='5' and t.del_flag = '0' and t.supplier_name in (SELECT s.supplier_name FROM t_supplier_info s where s.id='"+supplierId+"')";
		String res = dataDao.executesBySql(sql);
		if (res!=""){
			return Integer.parseInt(res);
		}else {
			return -1;
		}
	}

	@Override
	public Integer getSGEQuantityToday(String supplierId) {
		String sql="SELECT COUNT(*) FROM t_bike_out_storge_info t WHERE TO_DAYS(t.add_time)=TO_DAYS(NOW()) and t.bicycle_model='1' and t.bicycle_type='1' and t.del_flag = '0' and t.supplier_name in (SELECT s.supplier_name FROM t_supplier_info s where s.id='"+supplierId+"')";
		String res = dataDao.executesBySql(sql);
		if (res!=""){
			return Integer.parseInt(res);
		}else {
			return -1;
		}
	}

	@Override
	public Integer getQuantityTodayTotal(String supplierId) {
		String sql="SELECT COUNT(*) FROM t_bike_out_storge_info t WHERE TO_DAYS(t.add_time)=TO_DAYS(NOW()) and t.del_flag = '0' and t.supplier_name in (SELECT s.supplier_name FROM t_supplier_info s where s.id='"+supplierId+"')";
		String res = dataDao.executesBySql(sql);
		if (res!=""){
			return Integer.parseInt(res);
		}else {
			return -1;
		}
	}

	@Override
	public Integer getFGQuantity(String supplierId) {
		String sql="SELECT COUNT(*) FROM t_bike_out_storge_info t WHERE t.bicycle_model='0' and t.bicycle_type='5' and t.del_flag = '0' and t.supplier_name in (SELECT s.supplier_name FROM t_supplier_info s where s.id='"+supplierId+"')";
		String res = dataDao.executesBySql(sql);
		if (res!=""){
			return Integer.parseInt(res);
		}else {
			return -1;
		}
	}

	@Override
	public Integer getFGEQuantity(String supplierId) {
		String sql="SELECT COUNT(*) FROM t_bike_out_storge_info t WHERE t.bicycle_model='0' and t.bicycle_type='1' and t.del_flag = '0' and t.supplier_name in (SELECT s.supplier_name FROM t_supplier_info s where s.id='"+supplierId+"')";
		String res = dataDao.executesBySql(sql);
		if (res!=""){
			return Integer.parseInt(res);
		}else {
			return -1;
		}
	}

	@Override
	public Integer getSGQuantity(String supplierId) {
		String sql="SELECT COUNT(*) FROM t_bike_out_storge_info t WHERE t.bicycle_model='1' and t.bicycle_type='5' and t.del_flag = '0' and t.supplier_name in (SELECT s.supplier_name FROM t_supplier_info s where s.id='"+supplierId+"')";
		String res = dataDao.executesBySql(sql);
		if (res!=""){
			return Integer.parseInt(res);
		}else {
			return -1;
		}
	}

	@Override
	public Integer getSGEQuantity(String supplierId) {
		String sql="SELECT COUNT(*) FROM t_bike_out_storge_info t WHERE t.bicycle_model='1' and t.bicycle_type='1' and t.del_flag = '0' and t.supplier_name in (SELECT s.supplier_name FROM t_supplier_info s where s.id='"+supplierId+"')";
		String res = dataDao.executesBySql(sql);
		if (res!=""){
			return Integer.parseInt(res);
		}else {
			return -1;
		}
	}

	@Override
	public Integer getQuantityTotal(String supplierId) {
		String sql="SELECT COUNT(*) FROM t_bike_out_storge_info t WHERE t.del_flag = '0' and t.supplier_name in (SELECT s.supplier_name FROM t_supplier_info s where s.id='"+supplierId+"')";
		String res = dataDao.executesBySql(sql);
		if (res!=""){
			return Integer.parseInt(res);
		}else {
			return -1;
		}
	}

	@Override
	public Integer getQuantity(String supplierId) {
		String sql="SELECT COUNT(*) FROM t_bike_out_storge_info t WHERE t.bicycle_type='5' and t.del_flag = '0' and t.supplier_name in (SELECT s.supplier_name FROM t_supplier_info s where s.id='"+supplierId+"')";
		String res = dataDao.executesBySql(sql);
		if (res!=""){
			return Integer.parseInt(res);
		}else {
			return -1;
		}
	}

	@Override
	public Integer getEQuantity(String supplierId) {
		String sql="SELECT COUNT(*) FROM t_bike_out_storge_info t WHERE t.bicycle_type='1' and t.del_flag = '0' and t.supplier_name in (SELECT s.supplier_name FROM t_supplier_info s where s.id='"+supplierId+"')";
		String res = dataDao.executesBySql(sql);
		if (res!=""){
			return Integer.parseInt(res);
		}else {
			return -1;
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateInfo(BikeOutStorgeInfo bikeOutStorgeInfo) {
		dataDao.updateObject(bikeOutStorgeInfo);
	}

}
