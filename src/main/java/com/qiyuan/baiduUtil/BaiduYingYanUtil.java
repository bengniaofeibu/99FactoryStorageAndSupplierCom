package com.qiyuan.baiduUtil;

import java.util.Map;

public class BaiduYingYanUtil {

	private static String recEncoding = "UTF-8";


	private  static  final String OPEN_BATTERY_LOCK_URL="http://99company.99bicycle.com:8081/lock?action=OPENBATTERYLOCK";

	private  static  final String CONTROL_LECTRIC_LOCK_URL ="http://99company.99bicycle.com:8081/lock?action=CONTROLELECTRICLOCK";

	private  static  final  String GET_CANCELLATION_LOCK_INFO_URL="http://10.0.180.67/LockApi/lock?action=GETCANCELLATIONBIKEINFO";

	private  static  final  String GET_BIKE_INFO_URL="http://10.0.180.67/LockApi/lock?action=GETBIKEINFO";

	public static String getRecEncoding() {
		return recEncoding;
	}
	public static void setRecEncoding(String recEncoding) {
		BaiduYingYanUtil.recEncoding = recEncoding;
	}
	
	//创建实体接口
	public String addEntity(Map params){
		String url = "http://api.map.baidu.com/trace/v2/entity/add";
		return HttpRequestProxy.doPost(url, params, getRecEncoding());
	}
	
	//删除实体接口
	public String deleteEntity(Map params){
		String url = "http://api.map.baidu.com/trace/v2/entity/delete";
		return HttpRequestProxy.doPost(url, params, getRecEncoding());
	}
	
	//更新实体接口
	public String updateEntity(Map params){
		String url = "http://api.map.baidu.com/trace/v2/entity/update";
		return HttpRequestProxy.doPost(url, params, getRecEncoding());
	}
	
	//list——查询entity
	public String listEntity(Map params){
		String url = "http://api.map.baidu.com/trace/v2/entity/list";
		return HttpRequestProxy.doGet(url, params , getRecEncoding());
	}
	
	//添加entity属性字段
	public String addcolumnEntity(Map params){
		String url = "http://api.map.baidu.com/trace/v2/entity/addcolumn";
		return HttpRequestProxy.doPost(url, params , getRecEncoding());
	}
	
	//删除entity属性字段
	public String deletecolumnEntity(Map params){
		String url = "http://api.map.baidu.com/trace/v2/entity/deletecolumn";
		return HttpRequestProxy.doPost(url, params , getRecEncoding());
	}
	
	//列出entity属性字段
	public String listcolumnEntity(Map params){
		String url = "http://api.map.baidu.com/trace/v2/entity/listcolumn";
		return HttpRequestProxy.doGet(url, params , getRecEncoding());
	}
	
	//创建围栏
	public String createFence(Map params){
		String url = "http://api.map.baidu.com/trace/v2/fence/create";
		return HttpRequestProxy.doPost(url, params , getRecEncoding());
	}
	
	//删除围栏
	public String deleteFence(Map params){
		String url = "http://api.map.baidu.com/trace/v2/fence/delete";
		return HttpRequestProxy.doPost(url, params , getRecEncoding());
	}
	
	//更新围栏
	public String updateFence(Map params){
		String url = "http://api.map.baidu.com/trace/v2/fence/update";
		return HttpRequestProxy.doPost(url, params , getRecEncoding());
	}
	
	//查询围栏
	public String listFence(Map params){
		String url = "http://api.map.baidu.com/trace/v2/fence/list";
		return HttpRequestProxy.doGet(url, params , getRecEncoding());
	}
	
	//查询围栏内监控对象状态
	public String queryStatus(Map params){
		String url = "http://api.map.baidu.com/trace/v2/fence/querystatus";
		return HttpRequestProxy.doGet(url, params , getRecEncoding());
	}
	
	//查询围栏内监控对象历史报警信息
	public String historyalarm(Map params){
		String url = "http://api.map.baidu.com/trace/v2/fence/historyalarm";
		return HttpRequestProxy.doGet(url, params , getRecEncoding());
	}
	
	public String addpoint(Map params){
		String url = "http://api.map.baidu.com/trace/v2/track/addpoint";
		return HttpRequestProxy.doPost(url, params , getRecEncoding());
	}
	
	//添加轨迹点
	public String addpoint2(Map params){
		String url = "http://yingyan.baidu.com/api/v3/track/addpoint";
		return HttpRequestProxy.doPost(url, params , getRecEncoding());
	}

	public static String openLock(Map params){
		String url = "http://10.0.180.37:80/MidComPro/lock?action=OPENLOCK";
//		String url = "http://172.16.20.201:8081/netty?action=SENDMESSAGE";
		return HttpRequestProxy.doPost(url, params , getRecEncoding());
	}

	public static String openBatteryLock(Map params){
		String url = "http://10.0.180.37:80/MidComPro/lock?action=OPENBATTERYLOCK";
//		String url = "http://172.16.20.201:8081/netty?action=OPENBATTERYLOCK";
		return HttpRequestProxy.doPost(url, params , getRecEncoding());
	}

	public static String closeMotorLock(Map params){
		String url = "http://10.0.180.37:80/MidComPro/lock?action=CLOSEMOTORLOCK";
//		String url = "http://172.16.20.201:8081/netty?action=CLOSEMOTORLOCK";
		return HttpRequestProxy.doPost(url, params , getRecEncoding());
	}
	
	public static String updateLock(Map params){
		String url = "http://10.0.180.37:80/MidComPro/lock?action=UPDATELOCK";
		return HttpRequestProxy.doPost(url, params , getRecEncoding());
	}
	
	public static String queryLock(Map params){
		String url = "http://10.0.180.37:80/MidComPro/lock?action=QUERYLOCK";
		return HttpRequestProxy.doPost(url, params , getRecEncoding());
	}
	
	public String addBikeInfo(Map params){
		String url = "http://10.0.180.44:8081/SupplierComPro/supplier?action=INBICYCLE";
		return HttpRequestProxy.doPost(url, params , getRecEncoding());
	}
	//解绑更新鹰眼状态
	public String updateEntityCityNo(Map params){
		String url = "http://yingyan.baidu.com/api/v3/entity/update";
		return HttpRequestProxy.doPost(url, params , getRecEncoding());
	}

	//开助力车电池锁
	public static String openMopedBatteryLock(Map<String,Object> params){
		return HttpRequestProxy.doPost(OPEN_BATTERY_LOCK_URL, params , getRecEncoding());
	}

	//操作助力车电机锁接口
	public static String controlMopedElectriclock(Map<String,Object> params){
		return HttpRequestProxy.doPost(CONTROL_LECTRIC_LOCK_URL, params , getRecEncoding());
	}

	//获取注销锁的信息接口
	public static String getCancellationLockInfo(Map<String,Object> params){
		  return  HttpRequestProxy.doPost(GET_CANCELLATION_LOCK_INFO_URL,params,getRecEncoding());
	}

    //获取车辆是否注销
	public static String getBikeInfo(Map<String,Object> params){
	    return HttpRequestProxy.doPost(GET_BIKE_INFO_URL,params,getRecEncoding());
	}
}
