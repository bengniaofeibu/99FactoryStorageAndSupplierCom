package com.qiyuan.baiduUtil;

import java.util.Map;

public class BaiduLBSUtil {
	
	/**
	 * �������ݵı����ʽ
	 */
	private static String recEncoding = "UTF-8";
	public static String getRecEncoding() {
		return recEncoding;
	}
	public static void setRecEncoding(String recEncoding) {
		BaiduLBSUtil.recEncoding = recEncoding;
	}
	
	//创建表（create geotable）接口
	@SuppressWarnings("unchecked")
	public String createGeotable(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/geotable/create";
		return HttpRequestProxy.doPost(url, params, getRecEncoding());
	}
	
	//查询表（list geotable）接口
	@SuppressWarnings("unchecked")
	public String listGeotable(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/geotable/list";
		return HttpRequestProxy.doGet(url, params , getRecEncoding());
	}
	
	//查询指定id表（detail geotable）接口
	@SuppressWarnings("unchecked")
	public String detailGeotable(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/geotable/detail";
		return HttpRequestProxy.doGet(url,params,getRecEncoding());
	}
	
	//修改表（update geotable）接口
	@SuppressWarnings("unchecked")
	public String updateGeotable(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/geotable/update";
		return HttpRequestProxy.doPost(url,params,getRecEncoding());
	}
	
	//删除表（geotable）接口
	@SuppressWarnings("unchecked")
	public String deleteGeotable(Map params){
		//ע����geotable����û����Ч����ʱ������ɾ��geotable
		String url = "http://api.map.baidu.com/geodata/v3/geotable/delete";
		return HttpRequestProxy.doPost(url,params,getRecEncoding());
	}
	
	//创建列（create column）接口
	@SuppressWarnings("unchecked")
	public String createColumn(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/column/create";
		return HttpRequestProxy.doPost(url,params,getRecEncoding());
	}
	
	//查询列（list column）接口
	@SuppressWarnings("unchecked")
	public String listColumn(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/column/list";
		return HttpRequestProxy.doGet(url,params,getRecEncoding());
	}
	
	//查询指定id列（detail column）详情接口
	@SuppressWarnings("unchecked")
	public String detailColumn(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/column/detail";
		return HttpRequestProxy.doGet(url,params,getRecEncoding());
	}
	
	//修改指定条件列（column）接口
	@SuppressWarnings("unchecked")
	public String updateColumn(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/column/update";
		return HttpRequestProxy.doPost(url,params,getRecEncoding());
	}
	
	//删除指定条件列（column）接口
	@SuppressWarnings("unchecked")
	public String deleteColumn(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/column/delete";
		return HttpRequestProxy.doPost(url,params,getRecEncoding());
	}
	
	//创建数据（create poi）接口
	@SuppressWarnings("unchecked")
	public String createPOI(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/poi/create";
		return HttpRequestProxy.doPost(url,params,getRecEncoding());
	}
	
	//查询指定条件的数据（poi）列表接口
	@SuppressWarnings("unchecked")
	public String listPOI(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/poi/list";
		return HttpRequestProxy.doGet(url,params,getRecEncoding());
	}
	
	//查询指定id的数据（poi）详情接口
	@SuppressWarnings("unchecked")
	public String detailPOI(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/poi/detail";
		return HttpRequestProxy.doGet(url,params,getRecEncoding());
	}
	
	//修改数据（poi）接口
	@SuppressWarnings("unchecked")
	public String updatePOI(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/poi/update";
		return HttpRequestProxy.doGet(url,params,getRecEncoding());
	}
	
	//删除数据（poi）接口（支持批量）
	@SuppressWarnings("unchecked")
	public String deletePOI(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/poi/delete";
		return HttpRequestProxy.doPost(url,params,getRecEncoding());
	}
	
	//批量上传数据（post pois csv file）接口
	@SuppressWarnings("unchecked")
	public String postPOIsCSVFile(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/poi/upload";
		return HttpRequestProxy.doPost(url,params,getRecEncoding());
	}
	
	//批量上传进度查询接口（支持进度查询和上传失败的poi）
	@SuppressWarnings("unchecked")
	public String listImportStatus(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/job/listimportdata";
		return HttpRequestProxy.doGet(url,params,getRecEncoding());
	}
	
	//批量操作任务查询（list job）接口
	@SuppressWarnings("unchecked")
	public String listJob(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/job/list";
		return HttpRequestProxy.doGet(url,params,getRecEncoding());
	}
	
	//根据id查询批量任务（detail job）接口
	@SuppressWarnings("unchecked")
	public String detailJob(Map params){
		String url = "http://api.map.baidu.com/geodata/v3/job/detail";
		return HttpRequestProxy.doPost(url,params,getRecEncoding());
	}
}
