package com.qiyuan.servlet;

import java.io.BufferedReader;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daoshun.exception.NullParameterException;
import com.qiyuan.common.*;
import com.qiyuan.pojo.*;
import com.qiyuan.service.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qiyuan.baiduUtil.BaiduYingYanUtilTest;
import org.codehaus.jackson.map.ObjectMapper;



@WebServlet("/factory")
public class FactoryStorageServlet extends BaseServlet{

	/**
	 *
	 */
	private static final long serialVersionUID = -5582507019431931724L;

	private ILockTerminalService lockTerminalService;
	private IBikeService bikeService;
	private ILockGPSRealDataService lockGPRSRealDataService;
	private IAppVersionInfoService appVersionInfoService;
	private ILockFactoryInfoService lockFactoryInfoService;
	private ILockFactoryEmployeeInfoService lockFactoryEmployeeInfoService;
	private ILockTerminalInfoTempService lockTerminalInfoTempService;
	private IAdminPushService adminPushService;
	private IChangeBarcodeRecord changeBarcodeRecordService;

	private  final Log LOGGER = LogFactory.getLog(getClass());

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		lockTerminalService = (ILockTerminalService) applicationContext.getBean("lockTerminalService");
		bikeService = (IBikeService) applicationContext.getBean("bikeService");
		lockGPRSRealDataService = (ILockGPSRealDataService) applicationContext.getBean("lockGPRSRealDataService");
		appVersionInfoService = (IAppVersionInfoService) applicationContext.getBean("appVersionInfoService");
		lockFactoryInfoService = (ILockFactoryInfoService) applicationContext.getBean("lockFactoryInfoService");
		lockFactoryEmployeeInfoService=(ILockFactoryEmployeeInfoService) applicationContext.getBean("lockFactoryEmployeeInfoService");
		lockTerminalInfoTempService= (ILockTerminalInfoTempService) applicationContext.getBean("lockTerminalInfoTempService");
		adminPushService= (IAdminPushService) applicationContext.getBean("adminPushService");
		changeBarcodeRecordService= (IChangeBarcodeRecord) applicationContext.getBean("changeBarcodeRecordService");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String action = getAction(request,response);
			if (Constant.LOGIN.equals(action)) {
				Login(request,response);//登入接口
			}else if (Constant.UPLOADTERMINALINFO.equals(action)) {
				uploadTerminalInfo(request,response);//入库接口
			}else if(Constant.GETBLUETOOTHINFO.equals(action)){
				getBluetoothInfo(request,response);//入库蓝牙开锁接口
			}else if(Constant.GETBLUETOOTHINFOBYID.equals(action)){
				getBluetoothInfoById(request,response);//赳赳开锁蓝牙开锁接口
			}else if(Constant.FACTORYGPRSOPENLOCK.equals(action)){
				factoryGprsOpenLock(request,response);//入库GPRS开锁接口
			}else if(Constant.FACTORYGPRSOPENLOCKBYID.equals(action)){
				factoryGprsOpenLockById(request,response);//赳赳开锁GPRS开锁接口
			}else if(Constant.FACTORYGPRSOPENLOCKBYSIMNO.equals(action)){
				factoryGprsOpenLockBySimNo(request,response);//入库前GPRS开锁接口
			}else if(Constant.ISSTORAGE.equals(action)){
				isStorage(request,response);//车辆是否入库
			}
//			else if(Constant.UPDATELOCK.equals(action)){
//				updateLock(request,response);//升级锁接口
//			}else if(Constant.UPDATELOCKBYSIMNO.equals(action)){
//				updateLockBySimNo(request,response);//入库前升级锁接口
//			}else if(Constant.SENDQUERYLOCKCMD.equals(action)){
//				sendQueryLockCmd(request,response);//下发查询锁版本命令接口
//			}else if(Constant.SENDQUERYLOCKCMDBYSIMNO.equals(action)){
//				sendQueryLockCmdBySimNo(request,response);//入库前下发查询锁版本命令接口
//			}else if(Constant.QUERYLOCK.equals(action)){
//				queryLock(request,response);//查询锁版本接口
//			}else if(Constant.QUERYLOCKBYSIMNO.equals(action)){
//				queryLockBysimNo(request,response);//入库前查询版本接口
//			}
			else if(Constant.QUERYLOCKREALDATA.equals(action)){
				queryLockRealData(request,response);//查询锁详情接口
			}else if(Constant.GETAPPVERSION.equals(action)){
				getAppVersion(request,response);//获取app版本信息接口
			}else if(Constant.GETKEYPSW.equals(action)){
				getKeyPsw(request,response);//获取key，psw接口
			}else if(Constant.CHANGEPSW.equals(action)){
				changePsw(request,response);//更换app密码接口
			}else if(Constant.LOGOUT.equals(action)){
				logOut(request,response);//登出接口
			}else if(Constant.CHANGEBARCODE.equals(action)){
				changeBarcode(request,response);//更换二维码接口
			}else if(Constant.GPRSOPENBATTERYLOCK.equals(action)){
				gprsOpenBatteryLock(request,response);//GPRS开电池锁
			}else if(Constant.GPRSCLOSEMOTORLOCK.equals(action)){
				gprsCloseMotorLock(request,response);//GPRS开电机锁
			}else if(Constant.TEST.equals(action)){
				test(request,response);
			}else if(Constant.GETSIMNOBYBARCODE.equals(action)){
				getSimNoByBarcode(request,response);
			}else if(Constant.GETBARCODEBYSIMNO.equals(action)){
				getBarcodeBySimNo(request,response);
			}else {
				throw new NullParameterException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			setResultWhenException(response, e.getMessage());
		}

	}



	private void test(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		String realName ="";
		String passWord ="";
		String adminCid ="";
		try {
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();
			System.out.println("reportContent:"+reportContent);
//			String req = SecretUtils.Decrypt3DES(reportContent);
			String req=TripleDES.decode(reportContent);
			System.out.println("req:"+req);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
			realName = URLDecoder.decode(requestMap.get("realName"),"UTF-8");
			passWord = requestMap.get("passWord");
			adminCid = requestMap.get("adminCid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(realName);

		CommonUtils.validateEmpty(realName);
		System.out.println(passWord);
		CommonUtils.validateEmpty(passWord);
		System.out.println(adminCid);
		CommonUtils.validateEmpty(adminCid);
	}

	private void getBarcodeBySimNo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String simNo ="";
		try {
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();
			String req = SecretUtils.Decrypt3DES(reportContent);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
			simNo = requestMap.get("simNo");//设备编号
		} catch (Exception e) {
			e.printStackTrace();
		}
//		String simNo=request.getParameter("simNo");
		System.out.println(simNo);
		CommonUtils.validateEmpty(simNo);

		if (simNo.length()==12) {
			BikeInfo bikeInfo = bikeService.getBikeInfoBySimNo(simNo);
			if (bikeInfo != null) {
				String bicycleNo = String.valueOf(bikeInfo.getBicycleNo());
				if (bicycleNo.substring(0, 2).equals("50")) {
					LockTerminalInfo lockTerminalInfo = lockTerminalService.getInfoBySimNo(simNo);
					if (lockTerminalInfo != null) {
						StringBuffer barcode=new StringBuffer();
						barcode.append("http://www.99bicycle.com/download/?b="+bicycleNo);
						resultMap.put("result", "ok");
						resultMap.put("barcode", barcode);
						resultMap.put("message", "获取信息成功");
					} else {
						resultMap.put("result", "fail");
						resultMap.put("message", "无此设备信息");
					}
				} else {
					resultMap.put("result", "fail");
					resultMap.put("message", "该车辆不存在");
				}
			} else {
				resultMap.put("result", "fail");
				resultMap.put("message", "该车辆不存在");
			}
		}else {
			resultMap.put("result", "fail");
			resultMap.put("message", "非法设备ID");
		}
		setResult(response, resultMap);

	}

	private void getSimNoByBarcode(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String barcode ="";
		try {
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();
			String req = SecretUtils.Decrypt3DES(reportContent);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
			barcode = requestMap.get("barcode");//二维码
		} catch (Exception e) {
			e.printStackTrace();
		}
//		String barcode=request.getParameter("barcode");
		System.out.println(barcode);
		CommonUtils.validateEmpty(barcode);

			if(barcode.indexOf("http://www.99bicycle.com") != -1){
				System.out.println("有效二维码");
				int index = barcode.indexOf("b=");
				System.out.println("index:" + index);
				String bicycleNum = barcode.substring(index + 2);
				System.out.println(bicycleNum);
				if (bicycleNum.substring(0,2).equals("50")){
					BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
					if(bikeInfo != null){
						LockTerminalInfo lockTerminalInfo = lockTerminalService.getInfoBySimNo(bikeInfo.getSimNo());
						if(lockTerminalInfo != null){
							resultMap.put("result", "ok");
							resultMap.put("simNo", bikeInfo.getSimNo());
							resultMap.put("message", "获取信息成功");
						}else{
							resultMap.put("result", "fail");
							resultMap.put("message", "无此设备信息");
						}
					}else{
						resultMap.put("result", "fail");
						resultMap.put("message", "该车辆不存在");
					}
				}else {
					resultMap.put("result", "fail");
					resultMap.put("message", "该车辆不存在");
				}

			}else{
				resultMap.put("result", "fail");
				resultMap.put("message", "非法二维码");
			}
		setResult(response, resultMap);
	}

	private void gprsCloseMotorLock(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String barcode ="";
		String cmd ="";
		String id="";
		try {
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();
			String req = TripleDES.decode(reportContent);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
			barcode = requestMap.get("barcode");//二维码
			cmd = requestMap.get("cmd");// 命令
			id = requestMap.get("id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(barcode);
		CommonUtils.validateEmpty(barcode);
		System.out.println(cmd);
		CommonUtils.validateEmpty(cmd);
		System.out.println(id);
		CommonUtils.validateEmpty(id);
		LockFactoryEmployeeInfo lockFactoryEmployeeInfo = lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
		if (lockFactoryEmployeeInfo != null) {
			if (cmd.equals("closeMotorLock")) {
				if (barcode.indexOf("http://www.99bicycle.com") != -1) {
					System.out.println("有效二维码");
					int index = barcode.indexOf("b=");
					System.out.println("index:" + index);
					String bicycleNum = barcode.substring(index + 2);
					System.out.println(bicycleNum);

					BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
					if (bikeInfo != null) {
						int res = CommonUtils.SendCloseMotorLockCmd(bikeInfo.getSimNo());
						if (res == Constant.Success) {
							resultMap.put("result", "ok");
							resultMap.put("message", "指令发送成功");
						} else {
							resultMap.put("result", "fail");
							resultMap.put("message", "指令发送失败");
						}
					} else {
						resultMap.put("result", "fail");
						resultMap.put("message", "此设备没绑定");
					}

				} else {
					resultMap.put("result", "fail");
					resultMap.put("message", "非法二维码");
				}
			} else {
				resultMap.put("result", "fail");
				resultMap.put("message", "非法指令");
			}

		}
		setResult(response, resultMap);
	}

	private void gprsOpenBatteryLock(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String barcode ="";
		String cmd ="";
		String id="";
		try {
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();
			String req = TripleDES.decode(reportContent);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
			barcode = requestMap.get("barcode");//二维码
			cmd = requestMap.get("cmd");// 命令
			id = requestMap.get("id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(barcode);
		CommonUtils.validateEmpty(barcode);
		System.out.println(cmd);
		CommonUtils.validateEmpty(cmd);
		System.out.println(id);
		CommonUtils.validateEmpty(id);
		LockFactoryEmployeeInfo lockFactoryEmployeeInfo = lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
		if (lockFactoryEmployeeInfo != null) {
			if (cmd.equals("openBatteryLock")) {
				if (barcode.indexOf("http://www.99bicycle.com") != -1) {
					System.out.println("有效二维码");
					int index = barcode.indexOf("b=");
					System.out.println("index:" + index);
					String bicycleNum = barcode.substring(index + 2);
					System.out.println(bicycleNum);

					BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
					if (bikeInfo != null) {
						int res = CommonUtils.SendOpenBatteryLockCmd(bikeInfo.getSimNo());
						if (res == Constant.Success) {
							resultMap.put("result", "ok");
							resultMap.put("message", "指令发送成功");
						} else {
							resultMap.put("result", "fail");
							resultMap.put("message", "指令发送失败");
						}
					} else {
						resultMap.put("result", "fail");
						resultMap.put("message", "此设备没绑定");
					}

				} else {
					resultMap.put("result", "fail");
					resultMap.put("message", "非法二维码");
				}
			} else {
				resultMap.put("result", "fail");
				resultMap.put("message", "非法指令");
			}

		}
		setResult(response, resultMap);
	}





	private void changeBarcode(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String newBarcode = "";
		String oldBarcode = "";
		String id = "";
		try {
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();
			String req = TripleDES.decode(reportContent);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
			newBarcode = requestMap.get("newBarcode");//二维码
			oldBarcode = requestMap.get("oldBarcode");// 命令
			id = requestMap.get("id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		CommonUtils.validateEmpty(newBarcode);
		System.out.println(newBarcode);

		CommonUtils.validateEmpty(oldBarcode);
		System.out.println(oldBarcode);

		CommonUtils.validateEmpty(id);
		System.out.println(id);


		LockFactoryEmployeeInfo lockFactoryEmployeeInfo = lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
		if (lockFactoryEmployeeInfo != null) {
			if (!CommonUtils.getBicycleNo(oldBarcode).equals("1") && !CommonUtils.getBicycleNo(newBarcode).equals("1")) {
				String oldBicycleNo = CommonUtils.getBicycleNo(oldBarcode);
				String newBicycleNo = CommonUtils.getBicycleNo(newBarcode);
				BikeInfo oldBikeInfo = bikeService.getBikeInfoByBicycleNum(oldBicycleNo);
				BikeInfo newBikeInfo = bikeService.getBikeInfoByBicycleNum(newBicycleNo);
				if (oldBikeInfo != null) {//旧二维码已绑定
					if (newBikeInfo == null) {//新二维码未绑定
						oldBikeInfo.setBicycleNo(Integer.parseInt(newBicycleNo));
						bikeService.updateBikeInfo(oldBikeInfo);
						ChangeBarcodeRecord changeBarcodeRecord=new ChangeBarcodeRecord();
						changeBarcodeRecord.setAddTime(new Date());
						changeBarcodeRecord.setBicycleNo(newBicycleNo);
						changeBarcodeRecord.setChangedBy(id);
						changeBarcodeRecord.setPreBicycleNo(oldBicycleNo);
						changeBarcodeRecordService.addInfo(changeBarcodeRecord);
						resultMap.put("code", Constant.Success);
						resultMap.put("message", "二维码更换成功");

					} else {//旧二维码已绑定
						resultMap.put("code", Constant.Error_FaultBarcode);
						resultMap.put("message", "二维码更换失败,新旧二维码都已经绑定！");
					}

				} else {//旧二维码未绑定
					if (newBikeInfo != null) {//新二维码已绑定
						newBikeInfo.setBicycleNo(Integer.parseInt(oldBicycleNo));
						bikeService.updateBikeInfo(newBikeInfo);
						ChangeBarcodeRecord changeBarcodeRecord=new ChangeBarcodeRecord();
						changeBarcodeRecord.setAddTime(new Date());
						changeBarcodeRecord.setBicycleNo(oldBicycleNo);
						changeBarcodeRecord.setChangedBy(id);
						changeBarcodeRecord.setPreBicycleNo(newBicycleNo);
						changeBarcodeRecordService.addInfo(changeBarcodeRecord);
						resultMap.put("code", Constant.Success);
						resultMap.put("code", Constant.Success);
						resultMap.put("message", "二维码更换成功");
					} else {//新二维码未绑定
						resultMap.put("code", Constant.Error_FaultBarcode);
						resultMap.put("message", "二维码更换失败新旧二维码都未绑定！");
					}
				}
			} else {
				resultMap.put("code", Constant.Error_FaultBarcode);
				resultMap.put("message", "非法二维码");
			}



//			if (newBarcode.indexOf("http://www.99bicycle.com") != -1 && oldBarcode.indexOf("http://www.99bicycle.com") != -1) {
//				System.out.println("有效二维码");
//				int newindex = newBarcode.indexOf("b=");
//				System.out.println("index:" + newindex);
//				String newBicycleNo = newBarcode.substring(newindex + 2);
//				System.out.println(newBicycleNo);
//
//				int oldindex = oldBarcode.indexOf("b=");
//				System.out.println("index:" + oldindex);
//				String oldBicycleNo = oldBarcode.substring(oldindex + 2);
//				System.out.println(oldBicycleNo);
//
//
//			}else {
//				resultMap.put("code", Constant.Error_FaultBarcode);
//				resultMap.put("message", "非法二维码");
//			}
		}else {
			resultMap.put("code", Constant.Error_AccountNoExist);
			resultMap.put("message", "用户不存在");
		}

		setResult(response, resultMap);
	}


	private void factoryGprsOpenLockById(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap = new HashMap<String, Object>();


		String barcode ="";
		String cmd ="";
		String id="";
		try {
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();
			String req = TripleDES.decode(reportContent);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
			barcode = requestMap.get("barcode");//二维码
			cmd = requestMap.get("cmd");// 命令
			id = requestMap.get("id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(barcode);
		CommonUtils.validateEmpty(barcode);
		System.out.println(cmd);
		CommonUtils.validateEmpty(cmd);
		System.out.println(id);
		CommonUtils.validateEmpty(id);
		LockFactoryEmployeeInfo lockFactoryEmployeeInfo = lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
		if (lockFactoryEmployeeInfo != null) {
			if (cmd.equals("gprsOpenLock")) {
				if (barcode.indexOf("http://www.99bicycle.com") != -1) {
					System.out.println("有效二维码");
					int index = barcode.indexOf("b=");
					System.out.println("index:" + index);
					String bicycleNum = barcode.substring(index + 2);
					System.out.println(bicycleNum);

					BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
					if (bikeInfo != null) {
						int res = CommonUtils.SendOpenLockCmd(bikeInfo.getSimNo());
						if (res == Constant.Success) {
							resultMap.put("result", "ok");
							resultMap.put("message", "指令发送成功");
						} else {
							resultMap.put("result", "fail");
							resultMap.put("message", "指令发送失败");
						}
					} else {
						resultMap.put("result", "fail");
						resultMap.put("message", "此设备没绑定");
					}

				} else {
					resultMap.put("result", "fail");
					resultMap.put("message", "非法二维码");
				}
			} else {
				resultMap.put("result", "fail");
				resultMap.put("message", "非法指令");
			}

		}
		setResult(response, resultMap);
	}

	private void getBluetoothInfoById(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap =new HashMap<String, Object>();
		String barcode ="";
		String cmd ="";
		String id="";
		try {
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();
			System.out.println(reportContent);
			String req = TripleDES.decode(reportContent);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
			barcode = requestMap.get("barcode");//二维码
			cmd = requestMap.get("cmd");// 命令
			id = requestMap.get("id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(barcode);
		CommonUtils.validateEmpty(barcode);
		System.out.println(cmd);
		CommonUtils.validateEmpty(cmd);
		System.out.println(id);
		CommonUtils.validateEmpty(id);
		LockFactoryEmployeeInfo lockFactoryEmployeeInfo=lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
		if (lockFactoryEmployeeInfo!=null){
		if(cmd.equals("getBleMessage")){
			if(barcode.indexOf("http://www.99bicycle.com") != -1){
				System.out.println("有效二维码");
				int index = barcode.indexOf("b=");
				System.out.println("index:" + index);
				String bicycleNum = barcode.substring(index + 2);
				System.out.println(bicycleNum);

				BikeInfo info = bikeService.getBikeInfoByBicycleNum(bicycleNum);
				if(info != null){
					resultMap.put("mac", CommonUtils.PackingFactoryInfo(info.getBluetoothMac(), "mac"));
					resultMap.put("key", CommonUtils.PackingFactoryInfo(info.getNewKey(), "key"));
					resultMap.put("pass",CommonUtils.PackingFactoryInfo(info.getNewPassword(), "pass"));
				}else{
					resultMap.put("mac", "");
					resultMap.put("key", "");
					resultMap.put("pass","");
				}
			}else{
				if(barcode.substring(0, 1).equals("5") && barcode.length() == 9){
					BikeInfo info = bikeService.getBikeInfoByBicycleNum(barcode);
					if(info != null){
						resultMap.put("mac", CommonUtils.PackingFactoryInfo(info.getBluetoothMac(), "mac"));
						resultMap.put("key", CommonUtils.PackingFactoryInfo(info.getNewKey(), "key"));
						resultMap.put("pass",CommonUtils.PackingFactoryInfo(info.getNewPassword(), "pass"));
					}else{
						resultMap.put("mac", "");
						resultMap.put("key", "");
						resultMap.put("pass","");
					}
				}else{
					resultMap.put("mac", "");
					resultMap.put("key", "");
					resultMap.put("pass","");
				}
			}
		}else{
			resultMap.put("mac", "");
			resultMap.put("key", "");
			resultMap.put("pass","");
		}
		}else{
			resultMap.put("result","fail");
			resultMap.put("message", "用户不存在");
		}
		setResult(response, resultMap);
	}

//	private void queryLockBysimNo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//
//
//		String simNo ="";
//
//		try {
//			BufferedReader reader = request.getReader();
//			StringBuilder reportBuilder = new StringBuilder();
//			String tempStr = "";
//			while ((tempStr = reader.readLine()) != null) {
//				reportBuilder.append(tempStr);
//			}
//			String reportContent = reportBuilder.toString();
//			String req = SecretUtils.Decrypt3DES(reportContent);
//
//			ObjectMapper json = new ObjectMapper();
//			Map<String, String> requestMap = json.readValue(req, Map.class);
//			simNo = requestMap.get("simNo");//二维码
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(simNo);
//		CommonUtils.validateEmpty(simNo);
//
//
//			if (simNo.length()==12){
//				BikeInfo bikeInfo = bikeService.getBikeInfoBySimNo(simNo);
//				if(bikeInfo == null){
//					LockTerminalInfo lockTerminalInfo = lockTerminalService.getInfoBySimNo(simNo);
//					if(lockTerminalInfo != null){
//						resultMap.put("result", "ok");
//						resultMap.put("verSoftware", lockTerminalInfo.getVerSoftware());
//						resultMap.put("message", "获取设备信息成功");
//					}else{
//						resultMap.put("result", "fail");
//						resultMap.put("message", "无此设备信息");
//					}
//				}else{
//					resultMap.put("result", "fail");
//					resultMap.put("message", "此设备已绑定");
//				}
//			}else {
//				resultMap.put("result","fail");
//				resultMap.put("message","非法设备ID");
//			}
//
//		setResult(response, resultMap);
//
//	}

//	private void sendQueryLockCmdBySimNo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//
//		String simNo ="";
//		String cmd ="";
//		try {
//			BufferedReader reader = request.getReader();
//			StringBuilder reportBuilder = new StringBuilder();
//			String tempStr = "";
//			while ((tempStr = reader.readLine()) != null) {
//				reportBuilder.append(tempStr);
//			}
//			String reportContent = reportBuilder.toString();
//			String req = SecretUtils.Decrypt3DES(reportContent);
//
//			ObjectMapper json = new ObjectMapper();
//			Map<String, String> requestMap = json.readValue(req, Map.class);
//			simNo = requestMap.get("simNo");//二维码
//			cmd = requestMap.get("cmd");// 命令
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		CommonUtils.validateEmpty(simNo);
//		CommonUtils.validateEmpty(cmd);
//
//
//		if (simNo.length() == 12) {
//			if (cmd.equals("queryLock")) {
//				BikeInfo bikeInfo = bikeService.getBikeInfoBySimNo(simNo);
//				if (bikeInfo == null) {
//					int res = CommonUtils.SendQueryLockCmd(simNo);
//					if (res == Constant.Success) {
//						resultMap.put("result", "ok");
//						resultMap.put("message", "指令发送成功");
//					} else {
//						resultMap.put("result", "fail");
//						resultMap.put("message", "指令发送失败");
//					}
//				} else {
//					resultMap.put("result", "fail");
//					resultMap.put("message", "此设备已绑定");
//				}
//			} else {
//				resultMap.put("result", "fail");
//				resultMap.put("message", "非法指令");
//			}
//		} else {
//			resultMap.put("result", "fail");
//			resultMap.put("message", "非法设备ID");
//		}
//
//		setResult(response, resultMap);
//
//	}

//	private void updateLockBySimNo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//
//		String simNo ="";
//		String updateVersion ="";
//		String cmd ="";
//		try {
//			BufferedReader reader = request.getReader();
//			StringBuilder reportBuilder = new StringBuilder();
//			String tempStr = "";
//			while ((tempStr = reader.readLine()) != null) {
//				reportBuilder.append(tempStr);
//			}
//			String reportContent = reportBuilder.toString();
//			String req = SecretUtils.Decrypt3DES(reportContent);
//
//			ObjectMapper json = new ObjectMapper();
//			Map<String, String> requestMap = json.readValue(req, Map.class);
//			simNo = requestMap.get("simNo");//二维码
//			updateVersion = requestMap.get("updateVersion");//二维码
//			cmd = requestMap.get("cmd");// 命令
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(simNo);
//		CommonUtils.validateEmpty(simNo);
//		System.out.println(updateVersion);
//		CommonUtils.validateEmpty(updateVersion);
//		System.out.println(cmd);
//		CommonUtils.validateEmpty(cmd);
//
//
//			if (simNo.length()==12){
//				if (cmd.equals("updateLock")) {
//					BikeInfo bikeInfo = bikeService.getBikeInfoBySimNo(simNo);
//					if (bikeInfo==null) {
//						int res = CommonUtils.SendUpdateLockCmd(simNo,updateVersion);
//						if (res == Constant.Success) {
//							resultMap.put("result", "ok");
//							resultMap.put("message", "指令发送成功");
//						} else {
//							resultMap.put("result", "fail");
//							resultMap.put("message", "指令发送失败");
//						}
//					} else {
//						resultMap.put("result", "fail");
//						resultMap.put("message", "此设备已绑定");
//					}
//				} else {
//					resultMap.put("result", "fail");
//					resultMap.put("message", "非法指令");
//				}
//
//			}else {
//				resultMap.put("result","fail");
//				resultMap.put("message","非法设备ID");
//			}
//		setResult(response, resultMap);
//	}

	private void factoryGprsOpenLockBySimNo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap = new HashMap<String, Object>();

//		String simNo = request.getParameter("simNo");
//		CommonUtils.validateEmpty(simNo);
//
//		String cmd = request.getParameter("cmd");// 命令
//		CommonUtils.validateEmpty(cmd);
//
//		String id = request.getParameter("id");// 用户ID
//		CommonUtils.validateEmpty(id);
		String simNo ="";
		String cmd ="";
		try {
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();
			String req = SecretUtils.Decrypt3DES(reportContent);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
			simNo = requestMap.get("simNo");//二维码
			cmd = requestMap.get("cmd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(simNo);
		CommonUtils.validateEmpty(simNo);
		System.out.println(cmd);
		CommonUtils.validateEmpty(cmd);

			if (simNo.length() == 12) {
				if (cmd.equals("gprsOpenLock")) {

					BikeInfo bikeInfo = bikeService.getBikeInfoBySimNo(simNo);
					if (bikeInfo == null) {
						int res = CommonUtils.SendOpenLockCmd(simNo);
						if (res == Constant.Success) {
							resultMap.put("result", "ok");
							resultMap.put("message", "指令发送成功");
						} else {
							resultMap.put("result", "fail");
							resultMap.put("message", "指令发送失败");
						}
					} else {
						resultMap.put("result", "fail");
						resultMap.put("message", "此设备已绑定");
					}

				} else {
					resultMap.put("result", "fail");
					resultMap.put("message", "非法指令");
				}
			} else {
				resultMap.put("result", "fail");
				resultMap.put("message", "非法设备ID");
			}


		setResult(response, resultMap);

	}

	private void logOut(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap = new HashMap<String, Object>();

//		String id = request.getParameter("id");// 用户ID
//		CommonUtils.validateEmpty(id);
//
//		String type = request.getParameter("type");// 退出类型 1、正常退出 2、被迫退出
//		CommonUtils.validateEmpty(type);

		String id ="";
		String type ="";
		try {
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();
			String req = TripleDES.decode(reportContent);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
			id = requestMap.get("id");
			type = requestMap.get("type");
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(id);
		CommonUtils.validateEmpty(id);

		System.out.println(type);
		CommonUtils.validateEmpty(type);

		LockFactoryEmployeeInfo lockFactoryEmployeeInfo = lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
		if(lockFactoryEmployeeInfo != null){
			if(Integer.parseInt(type) == 1){
				lockFactoryEmployeeInfo.setLoginState(1);
				lockFactoryEmployeeInfoService.updateInfo(lockFactoryEmployeeInfo);
				resultMap.put("code", Constant.Success);
				resultMap.put("message", "退出成功");
			}else{
				resultMap.put("code", Constant.Error_AnotherLogOut);
				resultMap.put("message", "该账号在其他地方登陆，退出成功");
			}
		}else{
			resultMap.put("code", Constant.Error_LogOut);
			resultMap.put("message", "登出失败");
		}
		setResult(response, resultMap);

	}


	private void Login(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap = new HashMap<String,Object>();

		String realName ="";
		String passWord ="";
		String adminCid ="";
		try {
			request.getParameterMap();
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();

			System.out.println("reportContent:"+reportContent);

			String req = TripleDES.decode(reportContent);
			System.out.println("req:"+req);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
//			Map<String, String> requestMap = json.readValue(reportContent, Map.class);
			realName = URLDecoder.decode(requestMap.get("realName"),"UTF-8");
			passWord = requestMap.get("passWord");
			adminCid = requestMap.get("adminCid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(realName);

		CommonUtils.validateEmpty(realName);
		System.out.println(passWord);
		CommonUtils.validateEmpty(passWord);
		System.out.println(adminCid);
		CommonUtils.validateEmpty(adminCid);


//		String realName = request.getParameter("realName");
//		CommonUtils.validateEmpty(realName);
//
//		String passWord = request.getParameter("passWord");
//		CommonUtils.validateEmpty(passWord);
//
//		String adminCid = request.getParameter("adminCid");// 用户Cid
//		CommonUtils.validateEmpty(adminCid);
//
		LockFactoryEmployeeInfo lockFactoryEmployeeInfo = lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoByRealName(realName);
		LockFactoryEmployeeInfo info = lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfo(realName);
		if (lockFactoryEmployeeInfo!=null) {
			if (lockFactoryEmployeeInfo.getPassword().equalsIgnoreCase(passWord)) {
				System.out.println(lockFactoryEmployeeInfo.getLoginState());
				if(lockFactoryEmployeeInfo.getLoginState()==2){
					AdminPushInfo adminPushInfo = adminPushService.getPushInfoByAdminId(lockFactoryEmployeeInfo.getId());
					if(adminPushInfo != null){
						if (!adminPushInfo.getDeviceToken().equals(adminCid)) {
							PushtoSingle push = new PushtoSingle();
//测试						push.pushsingle(adminCid, "1:您的账户在其他地方登录，请注意账号安全，请重新登录");
							push.pushsingle(adminPushInfo.getDeviceToken(), "1:您的账户在其他地方登录，请注意账号安全，请重新登录");
							adminPushInfo.setDeviceToken(adminCid);
							adminPushService.updatePushInfo(adminPushInfo);
						}
					}
				}else{
					AdminPushInfo adminPushInfo = adminPushService.getPushInfoByAdminId(lockFactoryEmployeeInfo.getId());
					if(adminPushInfo != null){
						adminPushInfo.setDeviceToken(adminCid);
						adminPushInfo.setAddtime(new Date());
						adminPushService.updatePushInfo(adminPushInfo);
					}else{
						AdminPushInfo newAdminPushInfo = new AdminPushInfo();
						newAdminPushInfo.setAdminId(lockFactoryEmployeeInfo.getId());
						newAdminPushInfo.setDeviceToken(adminCid);
						newAdminPushInfo.setAddtime(new Date());
						adminPushService.addPushInfo(newAdminPushInfo);
					}
				}
				lockFactoryEmployeeInfo.setLoginState(2);
				lockFactoryEmployeeInfo.setLoginTime(new Date());
				lockFactoryEmployeeInfoService.updateInfo(lockFactoryEmployeeInfo);
				resultMap.put("code", Constant.Success);
				resultMap.put("info",info);
				resultMap.put("message", "登录成功");
			} else {
				resultMap.put("code", Constant.Error_Psw);
				resultMap.put("message", "登录失败:密码错误");
			}
		}else {
			resultMap.put("code", Constant.Error_AccountNoExist);
			resultMap.put("message", "用户不存在");
		}

		setResult(response, resultMap);
	}

	private void changePsw(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap = new HashMap<String, Object>();

//		String realName = request.getParameter("realName");// 用户姓名
//		CommonUtils.validateEmpty(realName);
//
//		String oldPassword = request.getParameter("oldPassword");// 用户原密码
//		CommonUtils.validateEmpty(oldPassword);
//
//		String newPassword = request.getParameter("newPassword");// 用户新密码
//		CommonUtils.validateEmpty(newPassword);
//
//		String id=request.getParameter("id");//用户id
//		CommonUtils.validateEmpty(id);


		String oldPassword ="";
		String realName ="";
		String id="";
		String newPassword ="";
		try {
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();
			System.out.println(reportContent);
			String req = TripleDES.decode(reportContent);
			System.out.println(req);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
			oldPassword = requestMap.get("oldPassword");
			realName = URLDecoder.decode(requestMap.get("realName"),"UTF-8");
			id = requestMap.get("id");
			newPassword = requestMap.get("newPassword");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(oldPassword);
		CommonUtils.validateEmpty(oldPassword);

		System.out.println(realName);
		CommonUtils.validateEmpty(realName);

		System.out.println(id);
		CommonUtils.validateEmpty(id);

		System.out.println(newPassword);
		CommonUtils.validateEmpty(newPassword);


		LockFactoryEmployeeInfo resInfo=lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
		if (resInfo!=null){
			LockFactoryEmployeeInfo lockFactoryEmployeeInfo = lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoByRealName(realName);
			if(lockFactoryEmployeeInfo != null){
				if(lockFactoryEmployeeInfo.getPassword().equalsIgnoreCase(oldPassword)){
					lockFactoryEmployeeInfo.setPassword(newPassword);
					lockFactoryEmployeeInfoService.updateInfo(lockFactoryEmployeeInfo);
					resultMap.put("code", Constant.Success);
					resultMap.put("message", "修改密码成功");
				}else{
					resultMap.put("code", Constant.Error_Psw);
					resultMap.put("message", "密码错误");
				}
			}else{
				resultMap.put("code", Constant.Error_ChangePsw);
				resultMap.put("message", "修改密码失败");
			}
		}else{
			resultMap.put("code", Constant.Error_AccountNoExist);
			resultMap.put("message", "用户不存在");
		}


		setResult(response, resultMap);


	}

	private void getKeyPsw(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
//		String mac = request.getParameter("mac");// mac地址
//		CommonUtils.validateEmpty(mac);
//		System.out.println(mac);
//
//		String id=request.getParameter("id");//用户id
//		CommonUtils.validateEmpty(id);
		String mac ="";
		String id ="";
		try {
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();

			System.out.println("reportContent:"+reportContent);

			String req = TripleDES.decode(reportContent);
			System.out.println("req:"+req);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
//			Map<String, String> requestMap = json.readValue(reportContent, Map.class);
			id = requestMap.get("id");
			mac = requestMap.get("mac");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(mac);
		CommonUtils.validateEmpty(mac);

		System.out.println(id);
		CommonUtils.validateEmpty(id);

		LockFactoryEmployeeInfo lockFactoryEmployeeInfo=lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
		if (lockFactoryEmployeeInfo!=null){
			String[] bm = mac.split(":");
			String bluetoothMac ="";
			for(int i=0;i < bm.length;i++){
				bluetoothMac = bluetoothMac + bm[i];
			}
			System.out.println(bluetoothMac);
//			LockTerminalInfo lockTerminalInfo=lockTerminalService.getInfoByMac(bluetoothMac);
//			BikeInfo bikeInfo = bikeService.getBikeInfoByBluetoothMac(mac);
			LockTerminalInfoTemp lockTerminalInfoTemp =lockTerminalInfoTempService.getInfoByMac(bluetoothMac);
			String newKey = "";
			String newPassword = "";
			StringBuilder key = new StringBuilder();
			StringBuilder psw = new StringBuilder();
			if (lockTerminalInfoTemp==null) {
				Random random1 = new Random();
				Random random2 = new Random();
				for (int i = 0; i < 32; i++) {
					key.append(random1.nextInt(10));
				}
				for(int i = 0; i< 12; i++) {
					psw.append(random2.nextInt(10));
				}
				newKey = key.toString();
				newPassword = psw.toString();
				System.out.println(newKey+"--------"+newPassword);
				LockTerminalInfoTemp newLockTerminalInfo =new LockTerminalInfoTemp();
//				LockTerminalInfo newLockTerminalInfo = new LockTerminalInfo();
				newLockTerminalInfo.setNewKey(newKey);
				newLockTerminalInfo.setNewPassword(newPassword);
				newLockTerminalInfo.setBluetoothMac(bluetoothMac);
				newLockTerminalInfo.setAddTime(new Date());
//				lockTerminalService.addInfo(newLockTerminalInfo);
				lockTerminalInfoTempService.addInfo(newLockTerminalInfo);
				resultMap.put("code", "0001");
				resultMap.put("newKey", CommonUtils.PackingFactoryInfo(newKey, "key"));
				resultMap.put("newPass", CommonUtils.PackingFactoryInfo(newPassword, "pass"));
				resultMap.put("mac", bluetoothMac);
				resultMap.put("SheBeiID", "");
				resultMap.put("BarCode", "http://www.99bicycle.com?=");

			}else {
				String newPassword1=lockTerminalInfoTemp.getNewPassword();
				String newKey1=lockTerminalInfoTemp.getNewKey();
//				System.out.println(newKey1+"----"+newPassword1);
				lockTerminalInfoTemp.setUpdateTime(new Date());
				resultMap.put("code", "0002");
//				System.out.println("秘钥"+CommonUtils.PackingFactoryInfo(newKey1,"key"));
				resultMap.put("newKey", CommonUtils.PackingFactoryInfo(newKey1,"key"));
//				System.out.println("密码"+CommonUtils.PackingFactoryInfo(newPassword1,"pass"));
				resultMap.put("newPass", CommonUtils.PackingFactoryInfo(newPassword1,"pass"));
				resultMap.put("mac", bluetoothMac);
				resultMap.put("SheBeiID", "");
				resultMap.put("BarCode", "http://www.99bicycle.com?=");
//				lockTerminalService.updateLockTerminalInfo(lockTerminalInfo);
				lockTerminalInfoTempService.updateLockTerminalInfo(lockTerminalInfoTemp);
			}
		}else{
			resultMap.put("result","fail");
			resultMap.put("message", "用户不存在");
		}
			setResult(response, resultMap);
	}


	private void getAppVersion(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
//		String id = request.getParameter("id");//用户id
//		CommonUtils.validateEmpty(id);

		String id ="";
		String appId ="";
		try {
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();
			System.out.println("reportContent:"+reportContent);
//			String req = SecretUtils.Decrypt3DES(reportContent);
			String req=TripleDES.decode(reportContent);
			System.out.println("req:"+req);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
			id = requestMap.get("id");
			appId = requestMap.get("appId");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(id);
		CommonUtils.validateEmpty(id);
		System.out.println(appId);
		CommonUtils.validateEmpty(appId);


		LockFactoryEmployeeInfo lockFactoryEmployeeInfo=lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
		if (lockFactoryEmployeeInfo!=null){
			AppVersionInfo appVersionInfo=appVersionInfoService.getAppInfoById(appId);
			AppVersionInfo info=appVersionInfoService.getInfoById(appId);
			if (appVersionInfo!=null) {
				resultMap.put("code", Constant.Success);
				resultMap.put("message", "版本获取成功");
				resultMap.put("info",info);
			}else {
				resultMap.put("code", Constant.Error_GetAppVersion);
				resultMap.put("message", "版本获取失败");
			}
		}else{
			resultMap.put("result","fail");
			resultMap.put("message", "用户不存在");
		}
		setResult(response, resultMap);
	}

	private void queryLockRealData(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
		Map<String, Object> resultMap =new HashMap<String, Object>();
		String simNo = request.getParameter("simNo");
		CommonUtils.validateEmpty(simNo);

		String id=request.getParameter("id");//用户id
		CommonUtils.validateEmpty(id);

		LockFactoryEmployeeInfo lockFactoryEmployeeInfo=lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
		if (lockFactoryEmployeeInfo!=null){
			LockGPSRealData lockGPSRealData = lockGPRSRealDataService.getLockGpsRealDataInfo(simNo);
			if(lockGPSRealData != null){
				resultMap.put("lockGPSRealData", lockGPSRealData);
				resultMap.put("code", 1);
				resultMap.put("message", "获取实时数据成功");
			}else{
				resultMap.put("code", 0);
				resultMap.put("message", "获取实时数据失败");
			}
		}else{
			resultMap.put("result","fail");
			resultMap.put("message", "用户不存在");
		}


		setResult(response, resultMap);
	}

//	private void queryLock(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
//		Map<String, Object> resultMap =new HashMap<String, Object>();
//
//		String barcode ="";
//		try {
//			BufferedReader reader = request.getReader();
//			StringBuilder reportBuilder = new StringBuilder();
//			String tempStr = "";
//			while ((tempStr = reader.readLine()) != null) {
//				reportBuilder.append(tempStr);
//			}
//			String reportContent = reportBuilder.toString();
//			String req = SecretUtils.Decrypt3DES(reportContent);
//
//			ObjectMapper json = new ObjectMapper();
//			Map<String, String> requestMap = json.readValue(req, Map.class);
//			barcode = requestMap.get("barcode");//二维码
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(barcode);
//		CommonUtils.validateEmpty(barcode);
//
//			if(barcode.indexOf("http://www.99bicycle.com") != -1){
//				System.out.println("有效二维码");
//				int index = barcode.indexOf("b=");
//				System.out.println("index:" + index);
//				String bicycleNum = barcode.substring(index + 2);
//				System.out.println(bicycleNum);
//
//				BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
//				if(bikeInfo != null){
//					LockTerminalInfo lockTerminalInfo = lockTerminalService.getInfoBySimNo(bikeInfo.getSimNo());
//					if(lockTerminalInfo != null){
//						resultMap.put("result", "ok");
//						resultMap.put("verSoftware", bikeInfo.getVerSoftware());
//						resultMap.put("message", "获取设备信息成功");
//					}else{
//						resultMap.put("result", "fail");
//						resultMap.put("message", "无此设备信息");
//					}
//				}else{
//					resultMap.put("result", "fail");
//					resultMap.put("message", "此设备没绑定");
//				}
//
//			}else{
//				resultMap.put("result", "fail");
//				resultMap.put("message", "非法二维码");
//			}
//
//		setResult(response, resultMap);
//	}

//	private void sendQueryLockCmd(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
//		Map<String, Object> resultMap =new HashMap<String, Object>();
//		String barcode ="";
//		String cmd ="";
//		try {
//			BufferedReader reader = request.getReader();
//			StringBuilder reportBuilder = new StringBuilder();
//			String tempStr = "";
//			while ((tempStr = reader.readLine()) != null) {
//				reportBuilder.append(tempStr);
//			}
//			String reportContent = reportBuilder.toString();
//			String req = SecretUtils.Decrypt3DES(reportContent);
//
//			ObjectMapper json = new ObjectMapper();
//			Map<String, String> requestMap = json.readValue(req, Map.class);
//			barcode = requestMap.get("barcode");//二维码
//			cmd = requestMap.get("cmd");// 命令
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(barcode);
//		CommonUtils.validateEmpty(barcode);
//		System.out.println(cmd);
//		CommonUtils.validateEmpty(cmd);
//
//			if(cmd.equals("queryLock")){
//				if(barcode.indexOf("http://www.99bicycle.com") != -1){
//					System.out.println("有效二维码");
//					int index = barcode.indexOf("b=");
//					System.out.println("index:" + index);
//					String bicycleNum = barcode.substring(index + 2);
//					System.out.println(bicycleNum);
//
//					BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
//					if(bikeInfo != null){
//						int res = CommonUtils.SendQueryLockCmd(bikeInfo.getSimNo());
//						if(res == Constant.Success){
//							resultMap.put("result", "ok");
//							resultMap.put("message", "指令发送成功");
//						}else{
//							resultMap.put("result", "fail");
//							resultMap.put("message", "指令发送失败");
//						}
//					}else{
//						resultMap.put("result", "fail");
//						resultMap.put("message", "此设备没绑定");
//					}
//
//				}else{
//					resultMap.put("result", "fail");
//					resultMap.put("message", "非法二维码");
//				}
//			}else{
//				resultMap.put("result", "fail");
//				resultMap.put("message", "非法指令");
//			}
//
//
//
//		setResult(response, resultMap);
//	}

//	private void updateLock(HttpServletRequest request,HttpServletResponse response) throws NullParameterException {
//		Map<String, Object> resultMap =new HashMap<String, Object>();
//
//		String barcode ="";
//		String updateVersion ="";
//		String cmd ="";
//		try {
//			BufferedReader reader = request.getReader();
//			StringBuilder reportBuilder = new StringBuilder();
//			String tempStr = "";
//			while ((tempStr = reader.readLine()) != null) {
//				reportBuilder.append(tempStr);
//			}
//			String reportContent = reportBuilder.toString();
//			String req = SecretUtils.Decrypt3DES(reportContent);
//
//			ObjectMapper json = new ObjectMapper();
//			Map<String, String> requestMap = json.readValue(req, Map.class);
//			barcode = requestMap.get("barcode");//二维码
//			updateVersion = requestMap.get("updateVersion");//二维码
//			cmd = requestMap.get("cmd");// 命令
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(barcode);
//		CommonUtils.validateEmpty(barcode);
//		System.out.println(updateVersion);
//		CommonUtils.validateEmpty(updateVersion);
//		System.out.println(cmd);
//		CommonUtils.validateEmpty(cmd);
//
//		if(cmd.equals("updateLock")){
//				if(barcode.indexOf("http://www.99bicycle.com") != -1){
//					System.out.println("有效二维码");
//					int index = barcode.indexOf("b=");
//					System.out.println("index:" + index);
//					String bicycleNum = barcode.substring(index + 2);
//					System.out.println(bicycleNum);
//
//					BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
//					if(bikeInfo != null){
//						int res = CommonUtils.SendUpdateLockCmd(bikeInfo.getSimNo(),updateVersion);
//						if(res == Constant.Success){
//							resultMap.put("result", "ok");
//							resultMap.put("message", "指令发送成功");
//						}else{
//							resultMap.put("result", "fail");
//							resultMap.put("message", "指令发送失败");
//						}
//					}else{
//						resultMap.put("result", "fail");
//						resultMap.put("message", "此设备没绑定");
//					}
//
//				}else{
//					resultMap.put("result", "fail");
//					resultMap.put("message", "非法二维码");
//				}
//			}else{
//				resultMap.put("result", "fail");
//				resultMap.put("message", "非法指令");
//			}
//
//
//		setResult(response, resultMap);
//
//	}

	private void isStorage(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
		Map<String, Object> resultMap =new HashMap<String, Object>();
		String mac = request.getParameter("mac");// mac地址
		CommonUtils.validateEmpty(mac);

		String id=request.getParameter("id");//用户id
		CommonUtils.validateEmpty(id);

		LockFactoryEmployeeInfo lockFactoryEmployeeInfo=lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
		if (lockFactoryEmployeeInfo!=null){
			String[] bm = mac.split(":");
			String bluetoothMac ="";
			for(int i=0;i < bm.length;i++){
				bluetoothMac = bluetoothMac + bm[i];
			}
			LockTerminalInfo info = lockTerminalService.getInfoByMac(bluetoothMac);
			if(info != null){
				LockGPSRealData lockGPSRealData = lockGPRSRealDataService.getLockGpsRealDataInfo(info.getSimNo());
				if(lockGPSRealData != null){
					if(lockGPSRealData.getOnline()){
						resultMap.put("result", "ok");
						resultMap.put("message", "设备已连接");
					}else{
						resultMap.put("result", "fail");
						resultMap.put("message", "设备无连接");
					}
				}else{
					resultMap.put("result", "fail");
					resultMap.put("message", "设备无连接");
					LOGGER.error("设备无连接:" + info.getSimNo() + "设备不存在");
				}
			}else{
				resultMap.put("result", "fail");
				resultMap.put("message", "设备无连接");
			}
		}else{
			resultMap.put("result","fail");
			resultMap.put("message", "用户不存在");
		}
		setResult(response, resultMap);
	}

	private void factoryGprsOpenLock(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
		Map<String, Object> resultMap =new HashMap<String, Object>();
//		String barcode = request.getParameter("barcode");//二维码
//		CommonUtils.validateEmpty(barcode);
//
//		String cmd = request.getParameter("cmd");// 命令
//		CommonUtils.validateEmpty(cmd);
//
//		String id=request.getParameter("id");//用户id
//		CommonUtils.validateEmpty(id);
		String barcode ="";
		String cmd ="";
		try {
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();
			String req = SecretUtils.Decrypt3DES(reportContent);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
			barcode = requestMap.get("barcode");//二维码
			cmd = requestMap.get("cmd");// 命令
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(barcode);
		CommonUtils.validateEmpty(barcode);
		System.out.println(cmd);
		CommonUtils.validateEmpty(cmd);

			if(cmd.equals("gprsOpenLock")){
				if(barcode.indexOf("http://www.99bicycle.com") != -1){
					System.out.println("有效二维码");
					int index = barcode.indexOf("b=");
					System.out.println("index:" + index);
					String bicycleNum = barcode.substring(index + 2);
					System.out.println(bicycleNum);

					BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
					if(bikeInfo != null){
						int res = CommonUtils.SendOpenLockCmd(bikeInfo.getSimNo());
						if(res == Constant.Success){
							resultMap.put("result", "ok");
							resultMap.put("message", "指令发送成功");
						}else{
							resultMap.put("result", "fail");
							resultMap.put("message", "指令发送失败");
						}
					}else{
						resultMap.put("result", "fail");
						resultMap.put("message", "此设备没绑定");
					}

				}else{
					resultMap.put("result", "fail");
					resultMap.put("message", "非法二维码");
				}
			}else{
				resultMap.put("result", "fail");
				resultMap.put("message", "非法指令");
			}


		setResult(response, resultMap);
	}

	private void getBluetoothInfo(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
		Map<String, Object> resultMap =new HashMap<String, Object>();
//		String barcode = request.getParameter("barcode");//二维码
//		CommonUtils.validateEmpty(barcode);
//
//		String cmd = request.getParameter("cmd");// 命令
//		CommonUtils.validateEmpty(cmd);
//
//		String id=request.getParameter("id");//用户id
//		CommonUtils.validateEmpty(id);
		String barcode ="";
		String cmd ="";
		try {
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();
			String req = SecretUtils.Decrypt3DES(reportContent);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
			barcode = requestMap.get("barcode");//二维码
			cmd = requestMap.get("cmd");// 命令
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(barcode);
		CommonUtils.validateEmpty(barcode);
		System.out.println(cmd);
		CommonUtils.validateEmpty(cmd);
//		LockFactoryEmployeeInfo lockFactoryEmployeeInfo=lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
//		if (lockFactoryEmployeeInfo!=null){
			if(cmd.equals("getBleMessage")){
				if(barcode.indexOf("http://www.99bicycle.com") != -1){
					System.out.println("有效二维码");
					int index = barcode.indexOf("b=");
					System.out.println("index:" + index);
					String bicycleNum = barcode.substring(index + 2);
					System.out.println(bicycleNum);

					BikeInfo info = bikeService.getBikeInfoByBicycleNum(bicycleNum);
					if(info != null){
						resultMap.put("mac", CommonUtils.PackingFactoryInfo(info.getBluetoothMac(), "mac"));
						resultMap.put("key", CommonUtils.PackingFactoryInfo(info.getNewKey(), "key"));
						resultMap.put("pass",CommonUtils.PackingFactoryInfo(info.getNewPassword(), "pass"));
					}else{
						resultMap.put("mac", "");
						resultMap.put("key", "");
						resultMap.put("pass","");
					}
				}else{
					if(barcode.substring(0, 1).equals("5") && barcode.length() == 9){
						BikeInfo info = bikeService.getBikeInfoByBicycleNum(barcode);
						if(info != null){
							resultMap.put("mac", CommonUtils.PackingFactoryInfo(info.getBluetoothMac(), "mac"));
							resultMap.put("key", CommonUtils.PackingFactoryInfo(info.getNewKey(), "key"));
							resultMap.put("pass",CommonUtils.PackingFactoryInfo(info.getNewPassword(), "pass"));
						}else{
							resultMap.put("mac", "");
							resultMap.put("key", "");
							resultMap.put("pass","");
						}
					}else{
						resultMap.put("mac", "");
						resultMap.put("key", "");
						resultMap.put("pass","");
					}
				}
			}else{
				resultMap.put("mac", "");
				resultMap.put("key", "");
				resultMap.put("pass","");
			}
//		}else{
//			resultMap.put("result","fail");
//			resultMap.put("message", "用户不存在");
//
//		}
		setResult(response, resultMap);

	}

	private void uploadTerminalInfo(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
		Map<String, Object> resultMap =new HashMap<String, Object>();
//		String barcode = request.getParameter("barcode");//二维码
//		CommonUtils.validateEmpty(barcode);
//
//		String mac = request.getParameter("mac");// mac地址
//		CommonUtils.validateEmpty(mac);
//
//		String factoryNo = request.getParameter("factoryNo");// 工厂代号
//		CommonUtils.validateEmpty(factoryNo);
//
//		String key = request.getParameter("key");// 新秘钥
//		CommonUtils.validateEmpty(key);
//
//		String psw = request.getParameter("pass");// 新密码
//		CommonUtils.validateEmpty(psw);
//
//		String simNo = request.getParameter("simNo");//设备编号
//		CommonUtils.validateEmpty(simNo);
//
//		String md5reply = request.getParameter("md5reply");//MD5加密信息
//		CommonUtils.validateEmpty(md5reply);
//
//		String id =request.getParameter("id");//用户id
//		CommonUtils.validateEmpty(id);

		String barcode ="";
		String mac ="";
		String id="";
		String factoryNo="";
		String key="";
		String psw="";
		String simNo="";
		String md5reply="";


		try {
			BufferedReader reader = request.getReader();
			StringBuilder reportBuilder = new StringBuilder();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
			String reportContent = reportBuilder.toString();
			System.out.println(reportContent);
			String req = TripleDES.decode(reportContent);

			ObjectMapper json = new ObjectMapper();
			Map<String, String> requestMap = json.readValue(req, Map.class);
			barcode = requestMap.get("barcode");//二维码
			mac = requestMap.get("mac");// 命令
			id = requestMap.get("id");
			factoryNo=requestMap.get("factoryNo");
			key= requestMap.get("key");
			psw= requestMap.get("pass");
			simNo=requestMap.get("simNo");
			md5reply=requestMap.get("md5reply");

		} catch (Exception e) {
			e.printStackTrace();
		}

		CommonUtils.validateEmpty(barcode);
		System.out.println(barcode);

		CommonUtils.validateEmpty(mac);
		System.out.println(mac);

		CommonUtils.validateEmpty(id);
		System.out.println(id);

		CommonUtils.validateEmpty(factoryNo);
		System.out.println(factoryNo);

		CommonUtils.validateEmpty(key);
		System.out.println(key);

		CommonUtils.validateEmpty(psw);
		System.out.println(psw);

		CommonUtils.validateEmpty(simNo);
		System.out.println(simNo);

		CommonUtils.validateEmpty(md5reply);
		System.out.println(md5reply);




		LOGGER.info("发来的信息:" + "蓝牙:" + mac);
		System.out.println("发来的信息:" + "蓝牙:" + mac);

		LockFactoryEmployeeInfo info =lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
		if (info!=null){
			LockFactoryInfo lockFactoryInfo = lockFactoryInfoService.getLockFactoryInfoByLockFactoryNo(factoryNo);
			if (lockFactoryInfo!=null) {
				String res = CommonUtils.MD5(simNo+mac+lockFactoryInfo.getLockFactoryNo());
				if (res.equalsIgnoreCase(md5reply)) {
					if (barcode.indexOf("http://www.99bicycle.com")!=-1) {
						System.out.println("有效二维码");
						int index = barcode.indexOf("b=");
						System.out.println("index:"+index);
						String bicycleNum = barcode.substring(index + 2);
						System.out.println(bicycleNum);

						String[] bm = mac.split(":");
						String bluetoothMac="";
						for (int i = 0; i < bm.length; i++) {
							bluetoothMac = bluetoothMac + bm[i];
						}
						String[] my = key.split(",");
						String newKey = "";
						for(int j=0;j < my.length;j++){
							newKey = newKey + my[j];
						}
						String[] np = psw.split(",");
						String newPass = "";
						for(int k=0;k < np.length;k++){
							newPass = newPass + np[k];
						}
						LOGGER.info("解析信息:" + "蓝牙:" + bluetoothMac);
						System.out.println("解析信息:" + "蓝牙:" + bluetoothMac);
						BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNumAndBluetoothMac(bicycleNum, bluetoothMac);
						if (bikeInfo!=null) {
							resultMap.put("result", "fail");
							resultMap.put("message", "绑定失败:此二维码已绑定");
						}else {
							int resAddBikeEntity =BaiduYingYanUtilTest.addBikeEntity(bicycleNum);
							if (resAddBikeEntity==Constant.Success) {
								LOGGER.error("插入鹰眼地图成功" + bicycleNum);
							}else {
								LOGGER.error("插入鹰眼地图失败" + bicycleNum);
							}
//							int resAddBikeInfo = BaiduYingYanUtilTest.addBikeInfo(bicycleNum, bluetoothMac, "0", newKey, newPass, simNo);
// 							测试
//							int resAddBikeInfo = 1;
//							if (resAddBikeInfo==Constant.Success) {
								System.out.println("----开始写入锁数据---");
								BikeInfo newBikeInfo = new BikeInfo();
								newBikeInfo.setAddTime(new Date());
								newBikeInfo.setBicycleLockStatus(0);
								newBikeInfo.setBicycleLockVoltage("0");
								newBikeInfo.setBicycleNo(Integer.parseInt(bicycleNum));
								newBikeInfo.setBicycleStatus(0);
								newBikeInfo.setBluetoothMac(bluetoothMac);
								newBikeInfo.setBluetoothName("0");
								newBikeInfo.setCityNo(0);
								newBikeInfo.setCurrentLatitude("0");
								newBikeInfo.setCurrentLongitude("0");
								newBikeInfo.setHandleFlag(0);
								newBikeInfo.setLockHardVersion("0");
								newBikeInfo.setLockSoftVersion("0");
								newBikeInfo.setSimNo(simNo);
								newBikeInfo.setUpdateFlag(0);
								newBikeInfo.setNewKey(newKey);
								newBikeInfo.setNewPassword(newPass);
								bikeService.addBikeInfo(newBikeInfo);

								LockTerminalInfo lockTerminalInfo = lockTerminalService.getInfoByMac(bluetoothMac);
								if(lockTerminalInfo == null){
									LockTerminalInfo newLockTerminalInfo = new LockTerminalInfo();
									newLockTerminalInfo.setAddTime(new Date());
									newLockTerminalInfo.setSimNo(simNo);
									newLockTerminalInfo.setBluetoothMac(bluetoothMac);
									newLockTerminalInfo.setNewKey(newKey);
									newLockTerminalInfo.setNewPassword(newPass);
									newLockTerminalInfo.setTerminalType("0");
									lockTerminalService.addInfo(newLockTerminalInfo);
								}else{
									lockTerminalInfo.setNewKey(newKey);
									lockTerminalInfo.setNewPassword(newPass);
									lockTerminalInfo.setUpdateTime(new Date());
//									lockTerminalInfo.setSimNo(simNo);
									lockTerminalService.updateLockTerminalInfo(lockTerminalInfo);
								}
								System.out.println("----结束写入锁数据---");
								resultMap.put("result", "ok");
								resultMap.put("message", "绑定成功");
//							}else {
//								resultMap.put("result", "fail");
//								resultMap.put("message", "入库失败");
//								LOGGER.error("入库失败:插入app数据库失败" + bicycleNum);
//							}
						}
					}else{
						resultMap.put("result", "fail");
						resultMap.put("message", "绑定失败:非法二维码");
						LOGGER.error("非法二维码:" + barcode);
					}
				}else {
					resultMap.put("result", "fail");
					resultMap.put("message", "绑定失败:不匹配");
				}
			}else {
			resultMap.put("result", "fail");
				resultMap.put("message", "绑定失败:锁厂不存在");
			}


		}else{
			resultMap.put("result","fail");
			resultMap.put("message", "用户不存在");
		}

//		LOGGER.info("发来的信息:" + "蓝牙:" + mac);
//		System.out.println("发来的信息:" + "蓝牙:" + mac);
//		if(barcode.indexOf("http://www.99bicycle.com") != -1){
//			System.out.println("有效二维码");
//			int index = barcode.indexOf("b=");
//			System.out.println("index:" + index);
//			String bicycleNum = barcode.substring(index + 2);
//			System.out.println(bicycleNum);
//
//			String[] bm = mac.split(":");
//			String bluetoothMac ="";
//			for(int i=0;i < bm.length;i++){
//				bluetoothMac = bluetoothMac + bm[i];
//			}
//			String[] my = key.split(",");
//			String newKey = "";
//			for(int j=0;j < my.length;j++){
//				newKey = newKey + my[j];
//			}
//			String[] np = pass.split(",");
//			String newPass = "";
//			for(int k=0;k < np.length;k++){
//				newPass = newPass + np[k];
//			}
//			LOGGER.info("解析信息:" + "蓝牙:" + bluetoothMac);
//			System.out.println("解析信息:" + "蓝牙:" + bluetoothMac);
//
//			BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNumAndBluetoothMac(bicycleNum, bluetoothMac);
//			if(bikeInfo != null){
//				resultMap.put("result", "fail");
//				resultMap.put("message", "此二维码已绑定");
//			}else{
//				int resAddBikeEntity = BaiduYingYanUtilTest.addBikeEntity(bicycleNum);
//				if(resAddBikeEntity == Constant.Success){
//					LOGGER.error("插入鹰眼地图成功" + bicycleNum);
//				}else{
//					LOGGER.error("插入鹰眼地图失败" + bicycleNum);
//				}
//					int resAddBikeInfo = BaiduYingYanUtilTest.addBikeInfo(bicycleNum, bluetoothMac, "0", newKey, newPass,simNo);
//					if(resAddBikeInfo == Constant.Success){
//						System.out.println("----开始写入锁数据---");
//						BikeInfo newBikeInfo = new BikeInfo();
//						newBikeInfo.setAddTime(new Date());
//						newBikeInfo.setBicycleLockStatus(0);
//						newBikeInfo.setBicycleLockVoltage("0");
//						newBikeInfo.setBicycleNo(Integer.parseInt(bicycleNum));
//						newBikeInfo.setBicycleStatus(0);
//						newBikeInfo.setBluetoothMac(bluetoothMac);
//						newBikeInfo.setBluetoothName("0");
//						newBikeInfo.setCityNo(0);
//						newBikeInfo.setCurrentLatitude("0");
//						newBikeInfo.setCurrentLongitude("0");
//						newBikeInfo.setHandleFlag(0);
//						newBikeInfo.setLockHardVersion("0");
//						newBikeInfo.setLockSoftVersion("0");
//						newBikeInfo.setSimNo(simNo);
//						newBikeInfo.setUpdateFlag(0);
//						newBikeInfo.setNewKey(newKey);
//						newBikeInfo.setNewPassword(newPass);
//						bikeService.addBikeInfo(newBikeInfo);
//
//						LockTerminalInfo lockTerminalInfo = lockTerminalService.getInfoByMac(bluetoothMac);
//						if(lockTerminalInfo == null){
//							LockTerminalInfo newLockTerminalInfo = new LockTerminalInfo();
//							newLockTerminalInfo.setAddTime(new Date());
//							newLockTerminalInfo.setSimNo(simNo);
//							newLockTerminalInfo.setBluetoothMac(bluetoothMac);
//							newLockTerminalInfo.setNewKey(newKey);
//							newLockTerminalInfo.setNewPassword(newPass);
//							newLockTerminalInfo.setTerminalType("0");
//							lockTerminalService.addInfo(newLockTerminalInfo);
//						}else{
//							lockTerminalInfo.setNewKey(newKey);
//							lockTerminalInfo.setNewPassword(newPass);
//							lockTerminalInfo.setUpdateTime(new Date());
//							lockTerminalService.updateLockTerminalInfo(lockTerminalInfo);
//						}
//						System.out.println("----结束写入锁数据---");
//						resultMap.put("result", "ok");
//						resultMap.put("message", "绑定成功");
//					}else{
//						resultMap.put("result", "fail");
//						resultMap.put("message", "入库失败");
//						LOGGER.error("入库失败:插入app数据库失败" + bicycleNum);
//					}
//
//			}
//		}else{
//			resultMap.put("result", "fail");
//			resultMap.put("message", "非法二维码");
//			LOGGER.error("非法二维码:" + barcode);
//		}
		setResult(response, resultMap);
	}
}
