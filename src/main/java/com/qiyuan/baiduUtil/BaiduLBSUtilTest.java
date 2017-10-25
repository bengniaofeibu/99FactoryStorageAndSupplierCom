package com.qiyuan.baiduUtil;

import com.qiyuan.common.Constant;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class BaiduLBSUtilTest {

	private static String ak = "IK5AlGXoZ23tDAGjldRalicbhdpsrKwE";
	
	
	private static BaiduLBSUtil baiduLBSUtil = new BaiduLBSUtil();
	public static String getAk() {
		return ak;
	}
	public static void setAk(String ak) {
		BaiduLBSUtilTest.ak = ak;
	}
	
	public static void createGeotable(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("name", "around");
		params.put("geotype", "1");
		params.put("is_published", "1");
		params.put("ak", ak);
		System.out.println(baiduLBSUtil.createGeotable(params));
	}
	
	public static void listGeotable(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		System.out.println(baiduLBSUtil.listGeotable(params));
	}

	public static void detailGeotable(String geotable_id){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("id", geotable_id);
		System.out.println(baiduLBSUtil.detailGeotable(params));
	}

	public static void updateGeotable(String geotable_id){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("id", geotable_id);
		params.put("is_published", 1);
		System.out.println(baiduLBSUtil.updateGeotable(params));
	}
	
	public static void deleteGeotable(String geotable_id){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("id", geotable_id);
		System.out.println(baiduLBSUtil.deleteGeotable(params));
	}
	
	public static void createColumn1(String geotable_id){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("geotable_id",geotable_id);
		params.put("name", "����");
		params.put("key", "city");
		params.put("type", "3");
		params.put("max_length", "512");
		//�Ƿ������ֶ�
		params.put("is_sortfilter_field", "0");
		//�Ƿ��ѯ�ֶ�
		params.put("is_search_field", "1");
		//�Ƿ������ֶ�
		params.put("is_index_field", "1");
		params.put("is_unique_field ", "0");
		System.out.println(baiduLBSUtil.createColumn(params));
	}
	
	public static void createColumn2(String geotable_id){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("geotable_id",geotable_id);
		params.put("name", "����");
		params.put("key", "district");
		params.put("type", "3");
		params.put("max_length", "512");
		//�Ƿ������ֶ�
		params.put("is_sortfilter_field", "0");
		//�Ƿ��ѯ�ֶ�
		params.put("is_search_field", "1");
		//�Ƿ������ֶ�
		params.put("is_index_field", "1");
		params.put("is_unique_field ", "0");
		System.out.println(baiduLBSUtil.createColumn(params));
	}

	public static void createColumn3(String geotable_id){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("geotable_id",geotable_id);
		params.put("name", "��˾ID");
		params.put("key", "company_id");
		params.put("type", "1");
		params.put("max_length", "512");
		//�Ƿ������ֶ�
		params.put("is_sortfilter_field", "1");
		//�Ƿ��ѯ�ֶ�
		params.put("is_search_field", "0");
		//�Ƿ������ֶ�
		params.put("is_index_field", "1");
		params.put("is_unique_field ", "0");
		System.out.println(baiduLBSUtil.createColumn(params));
	}
	
	public static void listColumn(String geotable_id){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("geotable_id",geotable_id);
		System.out.println(baiduLBSUtil.listColumn(params));
	}
	
	public static void detailColumn(String geotable_id) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("geotable_id",geotable_id);
		params.put("id", geotable_id);
		System.out.println(baiduLBSUtil.detailColumn(params));
	}

	public static String createPOI(String title,String lockHardVersion,String geotable_id){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("geotable_id",geotable_id);
		params.put("title", title);
		params.put("address", "赳赳电单车");		
		params.put("tags", "bicycle");		
		String latitude = "30.86006";
		String longitude = "120.06588";		
		params.put("latitude", latitude);		
		params.put("longitude", longitude);
		params.put("coord_type", "1");
//自己添加字段		
		params.put("bicycleStatus", "0");
		params.put("electricQuantityValue", "0");
		params.put("bicycleNum", title);	
		params.put("currentBatteryNum", "0");
		params.put("lockHardVersion", lockHardVersion);
		params.put("cumulativeKilometers", "0");
		params.put("cityNo", "0");
		params.put("bicycleLockStatus", "0");
		params.put("batteryLockStatus", "0");
		params.put("controllerStatus", "0");
		params.put("bicycleLockVoltage", "0");
		params.put("batteryVoltage", "0");
		params.put("ridingKilometers", "0");
		
		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduLBSUtil.createPOI(params));
			String status = backResult.get("status").toString();
			if(status.equals("0")){
				return backResult.get("id").toString();
			}else{
				return null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public void listPOI(String geotable_id){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("geotable_id",geotable_id);
		System.out.println(baiduLBSUtil.listPOI(params));
	}

	public static int deletePOI(String id,String geotable_id){
		Map<String ,String > params = new HashMap<String, String>();
		params.put("ak", ak);
		params.put("geotable_id", geotable_id);
		params.put("id", id);
		
		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduLBSUtil.deletePOI(params));
			String status = backResult.get("status").toString();
			if(status.equals("0")){
				return Constant.Success;
			}else{
				return Constant.Error_DeletePoi;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return Constant.Error_DeletePoi;
		}
	}
	
	public static String createPosPOI(String title,String geotable_id){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("geotable_id",geotable_id);
		params.put("title", title);
		params.put("address", "赳赳基站");		
		params.put("tags", "bicycle");		
		String latitude = "30.96006";
		String longitude = "120.16588";		
		params.put("latitude", latitude);		
		params.put("longitude", longitude);
		params.put("coord_type", "1");
//自己添加字段		
		params.put("posId", title);
		params.put("posName", title);
		params.put("chargingBinCount", "0");
		params.put("posState", "0");
		params.put("availableBorrowing", "0");
		params.put("availableReturn", "0");
		
		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduLBSUtil.createPOI(params));
			String status = backResult.get("status").toString();
			if(status.equals("0")){
				return backResult.get("id").toString();
			}else{
				return null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int updatePosPOI(String title,String geotable_id,String poiId,String address,String latitude,String longitude){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ak", ak);
		params.put("geotable_id",geotable_id);
		params.put("id",poiId);
		params.put("address",address);
		params.put("latitude",latitude);
		params.put("longitude",longitude);
		params.put("coord_type","3");
		
		JSONObject backResult;
		try {
			backResult = new JSONObject(baiduLBSUtil.updatePOI(params));
			String status = backResult.get("status").toString();
			if(status.equals("0")){
				return Constant.Success;
			}else{
				return Constant.Error_UpdatePosPoi;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return Constant.Error_UpdatePosPoi;
		}
		
	}

	
}
