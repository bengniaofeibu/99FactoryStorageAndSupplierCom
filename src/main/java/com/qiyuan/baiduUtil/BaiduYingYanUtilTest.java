package com.qiyuan.baiduUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.qiyuan.common.Constant;

public class BaiduYingYanUtilTest {

	private static String ak = "IK5AlGXoZ23tDAGjldRalicbhdpsrKwE";
	private static String service_id = "137950";
	private static String locating_service_id = "140284";
	private static String bike_Service_id = "135958";
	
	private static BaiduYingYanUtil baiduYingYanUtil = new BaiduYingYanUtil();

	public static String getAk() {
		return ak;
	}

	public static void setAk(String ak) {
		BaiduYingYanUtilTest.ak = ak;
	}

	public static String getService_id() {
		return service_id;
	}

	public static void setService_id(String service_id) {
		BaiduYingYanUtilTest.service_id = service_id;
	}		
	
	public static String getLocating_service_id() {
		return locating_service_id;
	}

	public static void setLocating_service_id(String locating_service_id) {
		BaiduYingYanUtilTest.locating_service_id = locating_service_id;
	}

	public static String getCurrentTimeFormat(String format) {
		return getTimeFormat(new Date(), format);
	}

	/**
	 * @param date
	 * @param string
	 * @return
	 */
	public static String getTimeFormat(Date date, String string) {
		SimpleDateFormat sdFormat;
		if (isEmptyString(string)) {
			sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			sdFormat = new SimpleDateFormat(string);
		}
		try {
			return sdFormat.format(date);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static boolean isEmptyString(String value) {
		if (value == null || value.length() == 0) {
			return true;
		}
		return false;
	}
	
	public static int addEntity(String value){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("service_id", service_id);
		params.put("entity_name", value);
		params.put("entity_desc", "邬文阳");
		params.put("phone", "13750831656");
		params.put("city_no", "0");

		
		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduYingYanUtil.addEntity(params));
			String status = backResult.get("status").toString();
			if(status.equals("0")){
				return Constant.Success;
			}else{
				return Constant.Error_AddEntity;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return Constant.Error_AddEntity;
		}
		
	}
	
	public static int deleteEntity(String value){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("service_id", service_id);
		params.put("entity_name", value);
		
		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduYingYanUtil.deleteEntity(params));
			String status = backResult.get("status").toString();
			if(status.equals("0")){
				return Constant.Success;
			}else{
				return Constant.Error_AddEntity;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return Constant.Error_AddEntity;
		}
	}
	
	public static void updateEntity(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("service_id", service_id);
		params.put("entity_name", "CH000000001");
		System.out.println(baiduYingYanUtil.deleteEntity(params));
	}
	
	public static void createFence(String value){
		
		String wl = "120.074401,30.865213;120.079423,30.865275;120.079432,30.860772;120.074293,30.86078";
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("service_id", service_id);
		params.put("name", "WL_" + value);
		params.put("creator", value);
		params.put("monitored_persons", value);
		params.put("observers", value);
		params.put("valid_times", "0000,1259");
		params.put("valid_cycle", 4);
		params.put("valid_date", 20170315);
		params.put("shape", 2);
		params.put("coord_type", 3);
		params.put("vertexes",  wl);
//		params.put("center", 30.86006);
//		params.put("radius", 1000);
		params.put("alarm_condition", 2);
		System.out.println(baiduYingYanUtil.createFence(params));
	}
	
	public static void listFence(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("service_id", service_id);
		params.put("creator", "CH000000001");
		System.out.println(baiduYingYanUtil.listFence(params));
	}
	
	public static void queryStatus(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("service_id", service_id);
		params.put("fence_id", 6);
		params.put("monitored_persons", "CH000000001");
		System.out.println(baiduYingYanUtil.queryStatus(params));
	}
	

	public static void addpoint(){
		Map<String,Object> params = new HashMap<String,Object>();
//		int time = Integer.parseInt(getCurrentTimeFormat("yyyyMMddHHmmss").substring(0, 10));
//		System.out.println(time);
		String time = Long.toString(new Date().getTime());
		System.out.println(time);
		params.put("ak", ak);
		params.put("service_id", service_id);
		params.put("latitude", 31.86006);
		params.put("longitude", 121.06588);
		params.put("coord_type", 1);
		params.put("loc_time", Long.toString(new Date().getTime()).substring(0, 10));
		params.put("entity_name", "CH000000001");
		System.out.println(baiduYingYanUtil.addpoint(params));
	}
	
	public static int addAdminPoint(String value,String latitude,String longitude){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("service_id", service_id);
		params.put("entity_name", value);
		params.put("latitude", latitude);
		params.put("longitude", longitude);
		params.put("loc_time", Long.toString(new Date().getTime()).substring(0, 10));
		params.put("coord_type", 3);
		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduYingYanUtil.addpoint(params));
			String status = backResult.get("status").toString();
			if(status.equals("0")){
				return Constant.Success;
			}else{
				return Constant.Error_AddPosPoint;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return Constant.Error_AddPosPoint;
		}
		
	}
	
	public static int updateLocatingPointEntity(String uuid,String peakParkingNum){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("service_id", locating_service_id);
		params.put("entity_name", uuid);
		params.put("peakParkingNum", peakParkingNum);
		
		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduYingYanUtil.updateEntity(params));
			String status = backResult.get("status").toString();
			if(status.equals("0")){
				return Constant.Success;
			}else{
				System.out.println("百度鹰眼状态:"+status);
				return Constant.Error_UpdateEntity;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return Constant.Error_UpdateEntity;
		}

	}
	
	public static int addLocatingPointEntity(String uuid,String address,String adminName,String childrenAreaName,String childrenAreaType,String pointName){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("service_id", locating_service_id);
		params.put("entity_name", uuid);
		params.put("entity_desc", address);
		params.put("creator", adminName);	
		params.put("childrenAreaId", childrenAreaName);
		params.put("childrenAreaType", childrenAreaType);
		params.put("pointName", pointName);
		params.put("peakParkingNum", "0|0");
		
		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduYingYanUtil.addEntity(params));
			String status = backResult.get("status").toString();
			String message = backResult.get("message").toString();
			if(status.equals("0")){
				return Constant.Success;
			}else{
				System.out.println("百度鹰眼状态:"+status);
				System.out.println("百度鹰眼回复:"+message);
				return Constant.Error_AddEntity;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return Constant.Error_AddEntity;
		}
		
	}
	
	public static int deleteLocatingPointEntity(String value){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("service_id", locating_service_id);
		params.put("entity_name", value);
		
		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduYingYanUtil.deleteEntity(params));
			String status = backResult.get("status").toString();
			if(status.equals("0")){
				return Constant.Success;
			}else{
				return Constant.Error_AddEntity;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return Constant.Error_AddEntity;
		}
	}
	
	public static int addLocatingPoint(String uuid,String latitude,String longitude,String address,String lineFlag){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("service_id", locating_service_id);
		params.put("entity_name", uuid);
		params.put("latitude", latitude);
		params.put("longitude", longitude);
		params.put("loc_time", Long.toString(new Date().getTime()).substring(0, 10));
		params.put("coord_type", 3);
		params.put("address", address);
		params.put("lineFlag", lineFlag);
		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduYingYanUtil.addpoint(params));
			String status = backResult.get("status").toString();
			if(status.equals("0")){
				return Constant.Success;
			}else{
				return Constant.Error_AddPosPoint;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return Constant.Error_AddPosPoint;
		}
	}
	
	public static int addEntity(String value,String s){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("service_id", bike_Service_id);
		params.put("entity_name", value);
		
	
		params.put("cityNo", "0");
		params.put("status", s);
		
		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduYingYanUtil.addEntity(params));
			String status = backResult.get("status").toString();
			if(status.equals("0")){
				return Constant.Success;
			}else{
				return Constant.Error_AddEntity;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return Constant.Error_AddEntity;
		}
		
	}
	
	public static int updateEntity(String value,String s){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("service_id", bike_Service_id);
		params.put("entity_name", value);
		params.put("status", s);
		
		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduYingYanUtil.updateEntity(params));
			String status = backResult.get("status").toString();
			if(status.equals("0")){
				return Constant.Success;
			}else{
				return Constant.Error_AddPosPoint;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return Constant.Error_AddPosPoint;
		}
	}

	public static int addPosPoint(String value,String latitude,String longitude){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("pos_service_id", bike_Service_id);
		params.put("entity_name", value);
		params.put("loc_time", Long.toString(new Date().getTime()).substring(0, 10));
		params.put("latitude", latitude);
		params.put("longitude", longitude);
		params.put("coord_type_input", "bd09ll");
		params.put("speed", "0");
		params.put("direction", "0");
		params.put("height", "0");
		params.put("chargeVoltage", "0");
		params.put("batteryVoltage", "0");
		params.put("satelliteNum", "8");
		params.put("signalIntensity", "0");
		params.put("lockStatus", "0");
		params.put("batteryLevel", "100");
		
		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduYingYanUtil.addpoint(params));
			String status = backResult.get("status").toString();
			if(status.equals("0")){
				return Constant.Success;
			}else{
				return Constant.Error_AddPosPoint;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return Constant.Error_AddPosPoint;
		}
		
	}
	
	public static int addBikeEntity(String value){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("service_id", bike_Service_id);
		params.put("entity_name", value);
		params.put("entity_desc", "共享单车");
		params.put("status", "12");
		params.put("cityNo", "0");

		
		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduYingYanUtil.addEntity(params));
			String status = backResult.get("status").toString();
			if(status.equals("0")){
				return Constant.Success;
			}else{
				return Constant.Error_AddEntity;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return Constant.Error_AddEntity;
		}
		
	}
	
	public static int addBikeInfo(String bicycleNo,String bluetoothMac,String gprsNo,String newKey,String pass,String simNo){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("bicycleNo", bicycleNo);
		params.put("bluetoothMac", bluetoothMac);
		params.put("gprsNo", gprsNo);
		params.put("newKey", newKey);
		params.put("pass", pass);
		params.put("simNo", simNo);
		
		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduYingYanUtil.addBikeInfo(params));
			String status = backResult.get("code").toString();
			if(status.equals("1")){
				return Constant.Success;
			}else{
				return Constant.Error_AddEntity;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return Constant.Error_AddEntity;
		}
		
	}

	public static int updateEntityCityNo(String entityName, String s,String c) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ak", ak);
		params.put("service_id", bike_Service_id);
		params.put("entity_name", entityName);
		params.put("cityNo", c);
		params.put("status", s);

		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduYingYanUtil.updateEntityCityNo(params));
			String status = backResult.get("status").toString();
			if (status.equals("0")) {
				return Constant.Success;
			} else {
				return Constant.Error_AddEntity;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return Constant.Error_AddEntity;
		}

	}
}
