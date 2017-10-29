package com.qiyuan.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qiyuan.pojo.*;
import com.qiyuan.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.daoshun.exception.NullParameterException;
import com.qiyuan.baiduUtil.BaiduLBSUtilTest;
import com.qiyuan.baiduUtil.BaiduYingYanUtilTest;
import com.qiyuan.common.CommonUtils;
import com.qiyuan.common.Constant;

@WebServlet("")
public class SupplierServlet extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8440392428270702290L;
	private ISupplierService supplierService;
	private IBikeOutStorgeService bikeOutStorgeService;
	private IFactoryQuantityService factoryQuantityService;
	private IBikeService iBikeService;
	private ILockGPSRealDataService lockGPSRealDataService;
	
	private  final Log LOGGER = LogFactory.getLog(getClass());
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		supplierService = (ISupplierService) applicationContext.getBean("supplierService");
		bikeOutStorgeService = (IBikeOutStorgeService) applicationContext.getBean("bikeOutStorgeService");
		factoryQuantityService = (IFactoryQuantityService) applicationContext.getBean("factoryQuantityService");
		iBikeService = (IBikeService) applicationContext.getBean("bikeService");
		lockGPSRealDataService= (ILockGPSRealDataService) applicationContext.getBean("lockGPSRealDataService");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String action = getAction(request,response);
			if (Constant.SUPPLIERUPLOADBICYCLE.equals(action)) {	
				uploadBicycleNum(request,response);
			}else if(Constant.SUPPLIERLOGIN.equals(action)){
				uploadSupplierInfo(request,response);
			}else if(Constant.GETFACTORYQUANTITY.equals(action)){
				getFactoryQuantity(request,response);
			}else if(Constant.OUTLOGIN.equals(action)){
				outLogin(request,response);
			}else if(Constant.DAILYOUTPUT.equals(action)){
				dailyOutput(request,response);
			}else if(Constant.DEREGISTRATION.equals(action)){
				deregistration(request,response);
			}else {
				throw new NullParameterException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			setResultWhenException(response, e.getMessage());
		}
	}

	private void deregistration(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap = new HashMap<>();
		String supplierId = request.getParameter("supplierId");
		CommonUtils.validateEmpty(supplierId);
		String bicycleNum = request.getParameter("bicycleNum");
		CommonUtils.validateEmpty(bicycleNum);
		try {
			SupplierInfo supplierInfo=supplierService.getSupplierBySupplierId(supplierId);
			if (supplierInfo!=null){
				BikeOutStorgeInfo bikeOutStorgeInfo=bikeOutStorgeService.getBikeOutInfo(bicycleNum);
				if (bikeOutStorgeInfo!=null){
					if (0==bikeOutStorgeInfo.getDelFlag()){
						bikeOutStorgeInfo.setDelFlag(1);
						bikeOutStorgeService.updateInfo(bikeOutStorgeInfo);
						resultMap.put("code", Constant.Success);
						resultMap.put("message", "车辆取消登记成功！");
					}else {
						resultMap.put("code", Constant.Fail);
						resultMap.put("message", "操作失败：车辆已经取消登记！");
					}

				}else {
					resultMap.put("code", Constant.Error_Deregistration);
					resultMap.put("message", "操作失败：该车未登记！");
				}
			}else {
				resultMap.put("code", Constant.Error_Deregistration);
				resultMap.put("message", "操作失败：获取供应商信息失败");
			}
		}catch (Exception e){
			LOGGER.info(e);
			resultMap.put("code", 101);
			resultMap.put("message", "出错啦！");
		}
		setResult(response,resultMap);
	}

	private void dailyOutput(HttpServletRequest request,HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap = new HashMap<>();
		String supplierId=request.getParameter("supplierId");
		CommonUtils.validateEmpty(supplierId);
		try{
			SupplierInfo supplierInfo =supplierService.getSupplierBySupplierId(supplierId);
			if (supplierInfo!=null){
				//1代自行车
				int FGDailyCount=bikeOutStorgeService.getFGQuantityToday(supplierId);
				//1代电动车
				int FGEDailyCount=bikeOutStorgeService.getFGEQuantityToday(supplierId);
				//2代自行车
				int SGDailyCount=bikeOutStorgeService.getSGQuantityToday(supplierId);
				//2代电动车
				int SGEDailyCount=bikeOutStorgeService.getSGEQuantityToday(supplierId);
				// 日总产量
				int quantityToday=bikeOutStorgeService.getQuantityTodayTotal(supplierId);
				Map dailyCount=new HashMap();
				dailyCount.put("FGDailyCount",FGDailyCount);
				dailyCount.put("FGEDailyCount",FGEDailyCount);
				dailyCount.put("SGDailyCount",SGDailyCount);
				dailyCount.put("SGEDailyCount",SGEDailyCount);
				dailyCount.put("quantityToday",quantityToday);
//				if (-1!=dailyCount){
					resultMap.put("code", Constant.Success);
					resultMap.put("dailyCount",dailyCount);
					resultMap.put("message", "获取信息成功！");
//				}else {
//					resultMap.put("code", Constant.Fail);
//					resultMap.put("message", "获取信息失败！");
//				}

			}else {
				resultMap.put("code", Constant.Error_DailyOutput);
				resultMap.put("message", "获取供应商信息失败");
			}
		}catch (Exception e){
			LOGGER.info(e);
			resultMap.put("code",101);
			resultMap.put("message","出错啦！");
		}
		setResult(response,resultMap);


	}

	private void outLogin(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
		Map<String, Object> resultMap = new HashMap<>();
		String supplierId = request.getParameter("supplierId");
		CommonUtils.validateEmpty(supplierId);
		try{
			SupplierInfo info = supplierService.getSupplierBySupplierId(supplierId);
			if(info != null){
				info.setLoginState(1);
				supplierService.updateSupplierInfo(info);
				resultMap.put("code", Constant.Success);
				resultMap.put("message", "退出成功");
			}else{
				resultMap.put("code", Constant.Error_SupplierOutLogin);
				resultMap.put("message", "获取供应商信息失败");
			}
		}catch (Exception e){
			LOGGER.info(e);
			resultMap.put("code",101);
			resultMap.put("message","出错啦！");
		}
		setResult(response,resultMap);
		
	}

//	private void inBicycle(HttpServletRequest request) throws NullParameterException{
//		String bicycleNo = request.getParameter("bicycleNo");
//		LOGGER.info("车号:" + bicycleNo);
//		CommonUtils.validateEmpty(bicycleNo);
//
//		String bluetoothMac = request.getParameter("bluetoothMac");
//		LOGGER.info("蓝牙mac:" + bluetoothMac);
//		CommonUtils.validateEmpty(bluetoothMac);
//
//		String gprsNo = request.getParameter("gprsNo");
//		LOGGER.info("gprsNo:" + gprsNo);
//		CommonUtils.validateEmpty(gprsNo);
//
//		String newKey = request.getParameter("newKey");
//		LOGGER.info("newKey:" + newKey);
//		CommonUtils.validateEmpty(newKey);
//
//		String pass = request.getParameter("pass");
//		LOGGER.info("pass:" + pass);
//		CommonUtils.validateEmpty(pass);
//
//		String simNo = request.getParameter("simNo");
//		LOGGER.info("simNo:" + simNo);
//		CommonUtils.validateEmpty(simNo);
//
//		BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNo);
//		if(bikeInfo != null){
//			resultMap.put("code", Constant.Error_InBicycle);
//			resultMap.put("message", "入库失败：车号重复");
//			LOGGER.error("入库失败：车号重复:" + bicycleNo);
//		}else{
//			BikeInfo newBikeInfo = new BikeInfo();
//			newBikeInfo.setAddTime(new Date());
//			newBikeInfo.setBicycleLockStatus(0);
//			newBikeInfo.setBicycleLockVoltage("0");
//			newBikeInfo.setBicycleNo(Integer.parseInt(bicycleNo));
//			newBikeInfo.setBicycleStatus(0);
//			newBikeInfo.setBluetoothMac(bluetoothMac);
//			newBikeInfo.setBluetoothName("0");
//			newBikeInfo.setCityNo(0);
//			newBikeInfo.setCurrentLatitude("0");
//			newBikeInfo.setCurrentLongitude("0");
//			newBikeInfo.setGprsNo(gprsNo);
//			newBikeInfo.setHandleFlag(0);
//			newBikeInfo.setLockHardVersion("0");
//			newBikeInfo.setLockSoftVersion("0");
//			newBikeInfo.setSimNo(simNo);
//			newBikeInfo.setUpdateFlag(0);
//			newBikeInfo.setNewKey(newKey);
//			newBikeInfo.setNewPassword(pass);
//			bikeService.addBikeInfo(newBikeInfo);
//			resultMap.put("code", Constant.Success);
//			resultMap.put("message", "入库成功");
//		}
//
//	}

	private void getFactoryQuantity(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
		Map<String,Object> resultMap=new HashMap<>();
		String supplierId = request.getParameter("supplierId");
		CommonUtils.validateEmpty(supplierId);
		try {
			SupplierInfo supplierInfo=supplierService.getSupplierBySupplierId(supplierId);
			if (supplierInfo!=null){
				FactoryQuantityInfo info = factoryQuantityService.getInfoBySupplierId(supplierId);
				if(info != null){
					//1代自行车
					int FGQuantity=bikeOutStorgeService.getFGQuantity(supplierId);
					//1代电动车
					int FGEQuantity=bikeOutStorgeService.getFGEQuantity(supplierId);
					//2代自行车
					int SGQuantity=bikeOutStorgeService.getSGQuantity(supplierId);
					//2代电动车
					int SGEQuantity=bikeOutStorgeService.getSGEQuantity(supplierId);
					// 日总产量
					int quantity=bikeOutStorgeService.getQuantityTotal(supplierId);
					Map bicycleQuantity=new HashMap();
					bicycleQuantity.put("FGQuantity",FGQuantity);
					bicycleQuantity.put("FGEQuantity",FGEQuantity);
					bicycleQuantity.put("SGQuantity",SGQuantity);
					bicycleQuantity.put("SGEQuantity",SGEQuantity);
					bicycleQuantity.put("quantity",quantity);
					resultMap.put("code", Constant.Success);
					resultMap.put("message", "获取出厂车辆数成功");
					resultMap.put("bicycleQuantity", bicycleQuantity);
				}else{
					Map bicycleQuantity=new HashMap();
					bicycleQuantity.put("FGQuantity",0);
					bicycleQuantity.put("FGEQuantity",0);
					bicycleQuantity.put("SGQuantity",0);
					bicycleQuantity.put("SGEQuantity",0);
					bicycleQuantity.put("quantity",0);
					resultMap.put("code", Constant.Success);
					resultMap.put("message", "获取出厂车辆数成功");
					resultMap.put("bicycleQuantity", bicycleQuantity);
				}
			}else {
				resultMap.put("code", Constant.Error_GetFactoryQuantity);
				resultMap.put("message", "获取供应商信息失败");
			}

		}catch (Exception e){
			LOGGER.info(e);
			resultMap.put("code", Constant.Fail);
			resultMap.put("message", "出错啦！");
		}
		setResult(response,resultMap);
		
	}

	private void uploadSupplierInfo(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
		Map<String,Object> resultMap=new HashMap<>();
		String supplierName = request.getParameter("supplierName");
		CommonUtils.validateEmpty(supplierName);

		String supplierPassword = request.getParameter("supplierPassword");
		CommonUtils.validateEmpty(supplierPassword);
		try{
			SupplierInfo supplierInfo = supplierService.getSupplierInfoByNameAndPassword(supplierName,supplierPassword);
			if(supplierInfo != null){
				supplierInfo.setLoginState(2);
				supplierInfo.setLoginTime(new Date());
				supplierService.updateSupplierInfo(supplierInfo);
				resultMap.put("code", Constant.Success);
				resultMap.put("message", "获取供应商信息成功");
				resultMap.put("supplierId", supplierInfo.getId());
			}else{
				resultMap.put("code", Constant.Error_SupplierLogin);
				resultMap.put("message", "获取供应商信息失败");
			}
		}catch (Exception e){
			LOGGER.info(e);
			resultMap.put("code", Constant.Fail);
			resultMap.put("message", "出错啦！");
		}
		setResult(response,resultMap);
	}

	private void uploadBicycleNum(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
		Map<String,Object> resultMap=new HashMap<>();
		String supplierId = request.getParameter("supplierId");
		CommonUtils.validateEmpty(supplierId);
		String bicycleNum = request.getParameter("bicycleNum");
		CommonUtils.validateEmpty(bicycleNum);
		String bicycleModel=request.getParameter("bicycleModel");
		CommonUtils.validateEmpty(bicycleModel);
		try {
			BikeOutStorgeInfo bikeOutStorgeInfo = bikeOutStorgeService.getBikeOutInfoByBicycleNum(bicycleNum);
			if(bikeOutStorgeInfo != null){
					resultMap.put("code", Constant.Error_UploadBicycleNum);
					resultMap.put("message", "出厂设置失败:车辆号已存在");
			}else{
				BikeInfo bikeInfo = iBikeService.getBikeInfoByBicycleNum(bicycleNum);
				if(bikeInfo != null){
					LockGPSRealData lockGPSRealData=lockGPSRealDataService.getLockGpsRealDataInfo(bikeInfo.getSimNo());
					if(lockGPSRealData!=null){
						int resAddEntity = BaiduYingYanUtilTest.addBikeEntity(bicycleNum);
						if(resAddEntity == Constant.Success){
							LOGGER.info("增加车辆实体成功");
						}else{
							LOGGER.info("增加车辆实体失败");
						}
						FactoryQuantityInfo fqInfo = factoryQuantityService.getInfoBySupplierId(supplierId);
						if(fqInfo != null){
							if(bicycleNum.substring(0, 1).equals("5")){
								int bicycleQuantity=bikeOutStorgeService.getQuantity(supplierId);
								if (-1!=bicycleQuantity){
									fqInfo.setBicycleQuantity(bicycleQuantity);
								}
							}else{
								int electricBicycleQuantity=bikeOutStorgeService.getEQuantity(supplierId);
								if (-1!=electricBicycleQuantity){
									fqInfo.setElectricBicycleQuantity(electricBicycleQuantity);
								}
							}
							factoryQuantityService.updateInfo(fqInfo);
						}else{
							FactoryQuantityInfo newfqInfo= new FactoryQuantityInfo();
							newfqInfo.setSupplierId(supplierId);
							newfqInfo.setAddTime(new Date());
							if(bicycleNum.substring(0, 1).equals("5")){
								newfqInfo.setBicycleQuantity(1);
							}else{
								newfqInfo.setElectricBicycleQuantity(1);
							}
							factoryQuantityService.addInfo(newfqInfo);
						}
						SupplierInfo supplierInfo = supplierService.getSupplierBySupplierId(supplierId);
						if(supplierInfo != null){
							BikeOutStorgeInfo newInfo = new BikeOutStorgeInfo();
							newInfo.setBicycleNo(Integer.parseInt(bicycleNum));
							newInfo.setBicycleModel(bicycleModel);
							newInfo.setAddTime(new Date());
							newInfo.setDelFlag(0);
							if(bicycleNum.substring(0, 1).equals("5")){
								newInfo.setBicycleType(5);
							}else{
								newInfo.setBicycleType(1);
							}
							newInfo.setSupplierName(supplierInfo.getSupplierName());
							bikeOutStorgeService.addInfo(newInfo);
							resultMap.put("code", Constant.Success);
							resultMap.put("message", "出厂设置成功");
						}else{
							resultMap.put("code", Constant.Error_UploadBicycleNum);
							resultMap.put("message", "出厂设置失败:厂家信息获取失败");
						}
					}else{
						int resAddEntity = BaiduYingYanUtilTest.addBikeEntity(bicycleNum);
						if(resAddEntity == Constant.Success){
							LOGGER.info("增加车辆实体成功");
						}else{
							LOGGER.info("增加车辆实体失败");
						}
						resultMap.put("code", Constant.Error_UploadBicycleNum);
						resultMap.put("message", "出厂设置失败:车辆未联网");
					}
				}else {
					resultMap.put("code", Constant.Error_UploadBicycleNum);
					resultMap.put("message", "出厂设置失败:车辆不存在。");
				}
			}
		}catch (Exception e){
			LOGGER.info(e);
			resultMap.put("code", 101);
			resultMap.put("message", "出错啦！");
		}
		setResult(response,resultMap);
	}

	
}
