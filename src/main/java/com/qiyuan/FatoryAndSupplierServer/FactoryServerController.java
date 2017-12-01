package com.qiyuan.FatoryAndSupplierServer;

import com.daoshun.exception.NullParameterException;
import com.qiyuan.Base.BaseController;
import com.qiyuan.annotation.SystemServerLog;
import com.qiyuan.baiduUtil.BaiduYingYanUtilTest;
import com.qiyuan.common.CommonUtils;
import com.qiyuan.common.Constant;
import com.qiyuan.common.PushtoSingle;
import com.qiyuan.enums.FactoryEnum;
import com.qiyuan.pojo.*;
import com.qiyuan.service.*;
import com.qiyuan.utils.JSON;
import com.qiyuan.utils.StringCommonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FactoryServerController extends BaseController{


    private  final Log LOGGER = LogFactory.getLog(FactoryServerController.class);

    private static final int OPEN_MOPED_LOCK_FLAG=0; //开助力车电机锁

    private static final int CLOSE_MOPED_LOCK_FLAG=1;//关助力车电机锁

    private static final int FLAG_0=0;//已经注销

    private static final int FLAG_1=1;//没有注销

    private static final String BICYCLE_SIGN="5"; //单车开头标识

    private static final String MOPED_SIGN="6";//助力车开头标识

    private static  final  String WL_START_NO="50";

    private static final  String BAR_CODE="http://www.99bicycle.com";

    private static final  String CMD_GPRS_OPEN_LOCK="gprsOpenLock";

    private static  final  String CMD_CLOSE_MOTOR_LOCK="closeMotorLock";


    @Resource
    private ILockTerminalService lockTerminalService;
    @Resource
    private IBikeService bikeService;
    @Resource(name = "lockGPRSRealDataService")
    private ILockGPSRealDataService lockGPRSRealDataService;
    @Resource
    private IAppVersionInfoService appVersionInfoService;
    @Resource
    private ILockFactoryInfoService lockFactoryInfoService;
    @Resource
    private ILockFactoryEmployeeInfoService lockFactoryEmployeeInfoService;
    @Resource
    private ILockTerminalInfoTempService lockTerminalInfoTempService;
    @Resource
    private IAdminPushService adminPushService;
    @Resource
    private IChangeBarcodeRecord changeBarcodeRecordService;

    @Autowired
    private RedisTemplate redisTemplate;

    //接口入口
    protected Map<String, Object> factorCallingService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> reponseMap=null;
        try {
            String action = getAction(request, response);
            //锁的接口
            switch (action) {
                case Constant.LOGIN:
                    reponseMap=Login(request);//登入接口
                    break;
                case Constant.UPLOADTERMINALINFO:
                    reponseMap=uploadTerminalInfo(request, response);//入库接口
                    break;
                case Constant.GETBLUETOOTHINFO:
                    reponseMap=getBluetoothInfo(request);//入库蓝牙开锁接口
                    break;
                case Constant.GETBLUETOOTHINFOBYID:
                    reponseMap= getBluetoothInfoById(request, response);//赳赳开锁蓝牙开锁接口
                    break;
                 case Constant.FACTORYGPRSOPENLOCK:
                    reponseMap=factoryGprsOpenLock(request, response);//入库GPRS开锁接口
                    break;
                case Constant.FACTORYGPRSOPENLOCKBYID:
                    reponseMap=factoryGprsOpenLockById(request, response);//开单车电机锁或助力车电机锁
                    break;
                case Constant.FACTORYGPRSOPENLOCKBYSIMNO:
                    reponseMap=factoryGprsOpenLockBySimNo(request);
                    break;
                case Constant.UPDATELOCK:
                    reponseMap=updateLock(request,response);//升级锁接口
                    break;
                case Constant.UPDATELOCKBYSIMNO:
                    reponseMap=updateLockBySimNo(request,response);//入库前升级锁接口
                    break;
                case Constant.ISSTORAGE:
                    reponseMap=isStorage(request);//车辆是否入库
                    break;
                case Constant.QUERYLOCKREALDATA:
                    reponseMap=queryLockRealData(request);//查询锁详情接口
                    break;
                case Constant.GETAPPVERSION:
                    reponseMap=getAppVersion(request);//获取app版本信息接口
                    break;
                case Constant.GETKEYPSW:
                    reponseMap=getKeyPsw(request);//获取key，psw接口
                    break;
                case Constant.CHANGEPSW:
                    reponseMap=changePsw(request);//更换app密码接口
                    break;
                case Constant.LOGOUT:
                    reponseMap=logOut(request);//登出接口
                    break;
                case Constant.CHANGEBARCODE:
                    reponseMap=changeBarcode(request);//更换二维码接口
                    break;
                case Constant.GPRSOPENBATTERYLOCK:
                    reponseMap=gprsOpenBatteryLock(request, response);//GPRS开电池锁
                    break;
                case Constant.GPRSCLOSEMOTORLOCK:
                    reponseMap=gprsCloseMotorLock(request, response);//GPRS开电机锁
                    break;
                case Constant.GETSIMNOBYBARCODE:
                    reponseMap=getSimNoByBarcode(request, response); //获取simNo
                    break;
                case Constant.QUERYLOCKBYSIMNO:
                    reponseMap=queryLockBysimNo(request);//入库前查询版本接口
                    break;
                case Constant.QUERYLOCK:
                    reponseMap=queryLock(request,response);//查询锁版本接口
                    break;
                case Constant.SENDQUERYLOCKCMDBYSIMNO:
                    reponseMap=sendQueryLockCmdBySimNo(request,response);//入库前下发查询锁版本命令接口
                    break;
                case Constant.SENDQUERYLOCKCMD:
                    reponseMap=sendQueryLockCmd(request,response);//下发查询锁版本命令接口
                    break;
                case Constant.GETBARCODEBYSIMNO:
                    reponseMap=getBarcodeBySimNo(request, response);
                    break;
                case Constant.GETBIKEINFO:
                    reponseMap=getBikeInfo(request);
                    break;
                case Constant.OPEN_BATTERY_LOCK:
                    reponseMap=openBatteryLock(request,response);//开助力车电池锁接口
                    break;
                case Constant.CONTRO_LELECTRIC_LOCK:
                    reponseMap=closeMopedLectricLock(request,response);//关助力车电机锁接口
                    break;
                case Constant.GET_CANCELLATION_LOCK_INFO:
                    reponseMap= getCancellationLockInfo(request,response); //获取注销锁的信息
                    break;
                case Constant.SEND_SMS_OPBAT_LOCK://短信开电瓶锁
                case Constant.SEND_SMS_UNLOCK: //短信解锁
                case Constant.SEND_SMS_FCUN_LOCK://短信强制开锁
                    //短信开电瓶锁
                    reponseMap=sendSMSLock(request,response,action);
                    break;
                default:
                    throw new NullParameterException();
            }

        } catch (Exception e) {
           LOGGER.error(e.getMessage());
        }
        return  reponseMap;
    }


    //入库前升级锁接口
    private Map<String, Object> updateLockBySimNo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {

		String simNo ="";
		String updateVersion ="";
		String cmd ="";
		try {
            Map<String, String> requestMap = getRequestParam3DES(request);
            simNo = requestMap.get("simNo");//二维码
			updateVersion = requestMap.get("updateVersion");//二维码
			cmd = requestMap.get("cmd");// 命令
		} catch (Exception e) {
			e.printStackTrace();
		}


			if (simNo.length()==12){
				if (cmd.equals("updateLock")) {
					BikeInfo bikeInfo = bikeService.getBikeInfoBySimNo(simNo);
					if (bikeInfo==null) {
						int res = CommonUtils.SendUpdateLockCmd(simNo,updateVersion);
						if (res == Constant.Success) {
						    return reponseResult(FactoryEnum.OPEN_LOCK_OK);
						} else {
						    return reponseResult(FactoryEnum.OPEN_LOCK_FAIL);
						}
					} else {
						return reponseResult(FactoryEnum.SIM_NO_ALREADY_BINDING);
					}
				 } else {
				       return reponseResult(FactoryEnum.CMD_ERROR);
				}

			}else {
				return reponseResult(FactoryEnum.SIM_NO_ERROR);
			}
	}

	//升级指令
    private Map<String,Object> updateLock(HttpServletRequest request,HttpServletResponse response) throws NullParameterException {
		String barcode ="";
		String updateVersion ="";
		String cmd ="";
		try {
            Map<String, String> requestMap = getRequestParam3DES(request);
			barcode = requestMap.get("barcode");//二维码
			updateVersion = requestMap.get("updateVersion");//二维码
			cmd = requestMap.get("cmd");// 命令
		} catch (Exception e) {
			e.printStackTrace();
		}
		     if(cmd.equals("updateLock")){
                    String bicycleNum = getBicycleNum(response, barcode, BAR_CODE, FactoryEnum.BARCODE_URL_ERROR);
					System.out.println(bicycleNum);
					BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
					if(bikeInfo != null){
						int res = CommonUtils.SendUpdateLockCmd(bikeInfo.getSimNo(),updateVersion);
						if(res == Constant.Success){
						    return reponseResult(FactoryEnum.OPEN_LOCK_OK);
						}else{
							return reponseResult(FactoryEnum.OPEN_LOCK_FAIL);
						}
					}else{
						return reponseResult(FactoryEnum.BICYCLE_NUM_NOT_BINDING);
					}
			}else{
				return reponseResult(FactoryEnum.CMD_ERROR);
			}
	}


    /**
     * 短信操作锁
     * @param request
     * @return
     */
    private Map<String,Object> sendSMSLock(HttpServletRequest request, HttpServletResponse response, String action){
        String barcode="";
        String id = "";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            barcode=requestParam.get("barcode");
            id = requestParam.get("id");
            LockFactoryEmployeeInfo employeeInfo = lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
                if (employeeInfo!=null){
                   String bicycleNum;
                        if (judgeBarcodeURL(barcode,BAR_CODE)){
                            bicycleNum=getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.BARCODE_URL_ERROR);
                        }else {
                            bicycleNum=barcode;
                        }
                     BikeInfo  bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
                    if (bikeInfo!=null){
                        if (bikeInfo.getLockSeries()==2){
                            return CommonUtils.sendSMSLock(bikeInfo.getGprsNo(),action);
                        }else {
                            return reponseResult(FactoryEnum.DEVICE_CANNOT_SEND_SMS);
                        }
                    }else {
                        return reponseResult(FactoryEnum.GET_BICK_SUPPLIER_NAME_UNFOUND);
                    }
            }else {
                return reponseResult(FactoryEnum.USER_NOT_FOUND_FAIL);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }

        return null;
    }


    private Map<String, Object> changeBarcode(HttpServletRequest request) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<>();
        String newBarcode = "";
        String oldBarcode = "";
        String id = "";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            newBarcode = requestParam.get("newBarcode");//二维码
            oldBarcode = requestParam.get("oldBarcode");// 命令
            id = requestParam.get("id");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

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

        }else {
            resultMap.put("code", Constant.Error_AccountNoExist);
            resultMap.put("message", "用户不存在");
        }
       return resultMap;
    }

    protected Map<String, Object> getAppVersion(HttpServletRequest request) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<>();

        String id ="";
        String appId ="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            id = requestParam.get("id");
            appId = requestParam.get("appId");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

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
     return resultMap;
    }


    protected Map<String, Object> queryLockRealData(HttpServletRequest request) throws NullParameterException{
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
        return resultMap;
    }


    protected Map<String, Object> isStorage(HttpServletRequest request) throws NullParameterException{
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
       return resultMap;
    }



    //开单车电机锁或助力车电机锁 （6开头的是助力车 ，5开头是单车）
    protected Map<String, Object> factoryGprsOpenLockById(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        String barcode ="";
        String cmd ="";
        String id="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            if (null!=requestParam){
                barcode = requestParam.get("barcode");//二维码
                cmd = requestParam.get("cmd");// 命令
                id = requestParam.get("id");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        LockFactoryEmployeeInfo lockFactoryEmployeeInfo = lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
        if (lockFactoryEmployeeInfo != null) {
            if (cmd.equals(CMD_GPRS_OPEN_LOCK)) {
                String bicycleNum=getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.BARCODE_URL_ERROR);
                System.out.println("有效二维码");
                System.out.println(bicycleNum);

                BikeInfo bikeInfo;//单车实体
                ElectricBikeInfo mopedInfo;//助力车实体
                    int res=0;
                    if (StringCommonUtil.startsWithStr(bicycleNum,MOPED_SIGN)){
                        //助力车
                         mopedInfo = bikeService.getMopedInfo(bicycleNum);
                        if (mopedInfo==null){
                            return reponseResult(FactoryEnum.BICYCLE_NUM_NOT_BINDING);
                        }
                        res = CommonUtils.SendControlMopedElectriclock(mopedInfo.getSimNo(),OPEN_MOPED_LOCK_FLAG);
                    }else if (StringCommonUtil.startsWithStr(bicycleNum,BICYCLE_SIGN)){
                        //单车
                         bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
                        if (bikeInfo==null){
                           return reponseResult(FactoryEnum.BICYCLE_NUM_NOT_BINDING);
                        }
                        res = CommonUtils.SendOpenLockCmd(bikeInfo.getSimNo());
                    }else {
                        return reponseResult(FactoryEnum.BICYCLE_NUM_ERROR);
                    }

                    if (res == Constant.Success) {
                       return reponseResult(FactoryEnum.OPEN_LOCK_OK);
                    } else {
                      return reponseResult(FactoryEnum.OPEN_LOCK_FAIL);
                    }

            } else {
                return reponseResult( FactoryEnum.CMD_ERROR);
            }
        }
        return null;
    }

    //入库GPRS开锁接口
    private Map<String, Object> factoryGprsOpenLock(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
        String barcode ="";
        String cmd ="";
        try {
            Map<String, String> requestMap = getRequestParam3DES(request);
            barcode = requestMap.get("barcode");//二维码
            cmd = requestMap.get("cmd");// 命令
        } catch (Exception e) {
            e.printStackTrace();
        }

         if(cmd.equals("gprsOpenLock")){
                String bicycleNum = getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.BARCODE_URL_ERROR);
                System.out.println(bicycleNum);

                BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
                if(bikeInfo != null){
                    int res = CommonUtils.SendOpenLockCmd(bikeInfo.getSimNo());
                    if(res == Constant.Success){
                        return reponseResult(FactoryEnum.OPEN_LOCK_OK);
                    }else{
                        return reponseResult(FactoryEnum.OPEN_LOCK_FAIL);
                    }
                }else{
                     return reponseResult(FactoryEnum.BICYCLE_NUM_NOT_BINDING);
                }
        }else{
             return reponseResult(FactoryEnum.CMD_ERROR);
        }
    }


    protected Map<String, Object> factoryGprsOpenLockBySimNo(HttpServletRequest request) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<>();

        String simNo ="";
        String cmd ="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            simNo = requestParam.get("simNo");//二维码
            cmd = requestParam.get("cmd");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        if (simNo.length() == 12) {
            if (cmd.equals(CMD_GPRS_OPEN_LOCK)) {
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
        return resultMap;
    }

    protected Map<String, Object> logOut(HttpServletRequest request) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<>();
        String id ="";
        String type ="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            id = requestParam.get("id");
            type = requestParam.get("type");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }


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
        return resultMap;
    }

    protected Map<String, Object> getBluetoothInfo(HttpServletRequest request) throws NullParameterException{
        Map<String, Object> resultMap =new HashMap<String, Object>();
        String barcode ="";
        String cmd ="";
        try {
            Map<String, String> requestParam = getRequestParam3DES(request);
            barcode = requestParam.get("barcode");//二维码
            cmd = requestParam.get("cmd");// 命令
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
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
        return resultMap;
    }

    protected Map<String, Object> getBluetoothInfoById(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        String barcode ="";
        String cmd ="";
        String id="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            barcode = requestParam.get("barcode");//二维码
            cmd = requestParam.get("cmd");// 命令
            id = requestParam.get("id");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        LockFactoryEmployeeInfo lockFactoryEmployeeInfo=lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
        if (lockFactoryEmployeeInfo!=null){
            if(cmd.equals("getBleMessage")){
                if(barcode.contains(BAR_CODE)){
                    System.out.println("有效二维码");
                    String bicycleNum=getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.BARCODE_URL_ERROR);
                    System.out.println(bicycleNum);

                    ElectricBikeInfo mopedInfo; //助力车实体
                    BikeInfo info; //单车实体
                    if (StringCommonUtil.startsWithStr(bicycleNum,MOPED_SIGN)){
                        //助力车
                         mopedInfo = bikeService.getMopedInfo(bicycleNum);
                        if(mopedInfo != null){
                           return reponseBluetoothParam(mopedInfo,FLAG_1);
                        }else{
                            //已经注销
                            CancellationBikeInfo cancellationBikeInfo = bikeService.getCancellationBikeInfo(bicycleNum);
                            if (cancellationBikeInfo!=null){
                                return reponseBluetoothParam(cancellationBikeInfo,FLAG_0);
                            } else {
                                return reponseBluetoothParam(null,null);
                            }
                        }
                    }else if (StringCommonUtil.startsWithStr(bicycleNum,BICYCLE_SIGN)){
                        //单车
                        info = bikeService.getBikeInfoByBicycleNum(bicycleNum);
                        if(info != null){
                           return reponseBluetoothParam(info,FLAG_1);
                        }else{
                            CancellationBikeInfo cancellationBikeInfo = bikeService.getCancellationBikeInfo(bicycleNum);
                            if (cancellationBikeInfo!=null){
                                return reponseBluetoothParam(cancellationBikeInfo,FLAG_0);
                            } else {
                               return reponseBluetoothParam(null,null);
                            }
                        }
                    }else {
                        return reponseResult(FactoryEnum.BICYCLE_NUM_ERROR);
                    }
                }else{
                    if(StringCommonUtil.startsWithStr(barcode,MOPED_SIGN) && barcode.length() == 9 ){
                        //助力车
                        ElectricBikeInfo mopedInfo = bikeService.getMopedInfo(barcode);
                        if(mopedInfo != null){
                           return reponseBluetoothParam(mopedInfo,FLAG_1);
                        }else{
                            CancellationBikeInfo cancellationBikeInfo = bikeService.getCancellationBikeInfo(barcode);
                            if (cancellationBikeInfo!=null){
                                return reponseBluetoothParam(cancellationBikeInfo,FLAG_0);
                            } else {
                               return reponseBluetoothParam(null,null);
                            }
                        }
                    }else if (StringCommonUtil.startsWithStr(barcode,BICYCLE_SIGN) && barcode.length() == 9){
                        //单车
                        BikeInfo info = bikeService.getBikeInfoByBicycleNum(barcode);
                        if(info != null){
                            return reponseBluetoothParam(info,FLAG_1);
                        }else{
                            CancellationBikeInfo cancellationBikeInfo = bikeService.getCancellationBikeInfo(barcode);
                            if (cancellationBikeInfo!=null){
                               return reponseBluetoothParam(cancellationBikeInfo,FLAG_0);
                            } else {
                               return reponseBluetoothParam(null,null);
                            }
                        }
                    } else{
                        return reponseBluetoothParam(null,null);
                    }

                }
            }else{
              return reponseBluetoothParam(null,null);
            }
        }else{
          return reponseResult(FactoryEnum.USER_NOT_FOUND_FAIL);
        }
    }


    protected Map<String, Object>  Login(HttpServletRequest request) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<String,Object>();

        String realName ="";
        String passWord ="";
        String adminCid ="";
        try {

            Map<String, String> requestParam = getRequestParam(request);
            realName = URLDecoder.decode(requestParam.get("realName"),"UTF-8");
            passWord = requestParam.get("passWord");
            adminCid = requestParam.get("adminCid");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
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
        return  resultMap;
    }


    public Map<String, Object>  getCancellationLockInfo(HttpServletRequest request,HttpServletResponse response){
        try {
            Map<String, String> requestParam = getRequestParam(request);
            String barcode = requestParam.get("barcode");//二维码
            String bicycleNo = getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.BARCODE_URL_ERROR);
            JSONObject bikeInfo=CommonUtils.getBikeInfo(bicycleNo);
            int bikeCode = Integer.valueOf(bikeInfo.get("code").toString());
            if (bikeCode==1){
                JSONObject lockInfo = CommonUtils.getCancellationLockInfo(bicycleNo);
                int lockCode = Integer.valueOf(lockInfo.get("code").toString());
                if (lockCode==0){
                    CancellationBikeInfo cancelBikeInfo= JSON.parseObject(lockInfo.get("data").toString(), CancellationBikeInfo.class);
                    return reponseResult(FactoryEnum.GET_CANCELLATION_LOCK_INFO_OK,cancelBikeInfo,"flag",FLAG_0);
                }else {
                 return reponseResult(FactoryEnum.GET_CANCELLATION_LOCK_INFO_FAIL,"");
                }
            }else {
               return reponseResult(FactoryEnum.GET_BIKE_INFO_FAIL,"","flag",FLAG_1);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    //开助力车电池锁接口
    private  Map<String, Object> openBatteryLock(HttpServletRequest request,HttpServletResponse response){

        try {
            Map<String, String> requestParam = getRequestParam(request);
            String barcode = requestParam.get("barcode");//二维码
            String bicycleNum = getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.BARCODE_URL_ERROR);
            ElectricBikeInfo mopedInfo = bikeService.getMopedInfo(bicycleNum);
            if (mopedInfo==null){
                return reponseResult(FactoryEnum.BICYCLE_NUM_NOT_BINDING);

            }
//            String openBatteryLock=getReqParam("openBatteryLock");
            int repInfo = CommonUtils.SendOpenMopedBatteryLock(mopedInfo.getSimNo());
            if (repInfo==Constant.Success) {
                return reponseResult(FactoryEnum.OPEN_LOCK_OK);
            }else{
                return reponseResult(FactoryEnum.OPEN_LOCK_FAIL);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    //操作助力车电机锁接口
    private  Map<String, Object>  closeMopedLectricLock(HttpServletRequest request,HttpServletResponse response){
        try {
            Map<String, String> requestParam = getRequestParam(request);
            String barcode = requestParam.get("barcode");//二维码
            String bicycleNum = getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.BARCODE_URL_ERROR);
            ElectricBikeInfo mopedInfo = bikeService.getMopedInfo(bicycleNum);
            if (mopedInfo==null){
               return getReponseMap(FactoryEnum.BICYCLE_NUM_NOT_BINDING);
            }
//            String closeMotorLock=getReqParam("closeMotorLock");
//            Integer flag = Integer.valueOf(getReqParam("flag"));
            int repInfo=CommonUtils.SendControlMopedElectriclock(mopedInfo.getSimNo(),CLOSE_MOPED_LOCK_FLAG);
            if (repInfo==Constant.Success) {
                return reponseResult(FactoryEnum.OPEN_LOCK_OK);
            }else{
                return reponseResult(FactoryEnum.OPEN_LOCK_FAIL);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    private Map<String, Object> getBikeInfo(HttpServletRequest request) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<>();
        String unknownNo =request.getParameter("unknownNo");
        CommonUtils.validateEmpty(unknownNo);

        if (12==unknownNo.length()){
            BikeInfo bikeInfo=bikeService.getBikeInfo(unknownNo);
            if (null!=bikeInfo){
                resultMap.put("result", "ok");
                resultMap.put("bikeInfo",bikeInfo);
                resultMap.put("message", "获取信息成功！");
            }else {
                resultMap.put("result", "fail");
                resultMap.put("message", "该设备id未注册！");
            }
        }else if (9==unknownNo.length()){
            BikeInfo bikeInfo=bikeService.getBikeInfoByUnknowNo(Integer.parseInt(unknownNo));
            if (null!=bikeInfo){
                resultMap.put("result", "ok");
                resultMap.put("bikeInfo",bikeInfo);
                resultMap.put("message", "获取信息成功！");
            }else {
                resultMap.put("result", "fail");
                resultMap.put("message", "该车辆id未注册！");
            }
        }else {
            resultMap.put("result", "fail");
            resultMap.put("message", "非法id！");
        }
        return resultMap;
    }


    protected Map<String, Object> getBarcodeBySimNo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        String simNo;
        try {
            Map<String, String> requestParam3DES = getRequestParam3DES(request);
            simNo = requestParam3DES.get("simNo");//设备编号

        if (simNo.length()==12) {
            BikeInfo bikeInfo = bikeService.getBikeInfoBySimNo(simNo);
            if (bikeInfo != null) {
                String bicycleNo = String.valueOf(bikeInfo.getBicycleNo());
                if (bicycleNo.substring(0, 2).equals("50")) {
                    LockTerminalInfo lockTerminalInfo = lockTerminalService.getInfoBySimNo(simNo);
                    if (lockTerminalInfo != null) {
                        StringBuffer barcode=new StringBuffer();
                        barcode.append("http://www.99bicycle.com/download/?b=").append(bicycleNo);
                        return reponseResult(FactoryEnum.GET_SIM_NO_INFO_OK,barcode.toString());
                    } else {
                        return reponseResult(FactoryEnum.NOT_FOUND_SIM_NO_OK);
                    }
                } else {
                    return reponseResult(FactoryEnum.GET_BICK_SUPPLIER_NAME_UNFOUND);
                }
            } else {
                return reponseResult(FactoryEnum.GET_BICK_SUPPLIER_NAME_UNFOUND);
            }
        }else {
            return reponseResult(FactoryEnum.SIM_NO_ERROR);
        }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    private Map<String, Object> sendQueryLockCmd(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
		String barcode ="";
		String cmd ="";
		try {
            Map<String, String> requestParam3DES = getRequestParam3DES(request);
            barcode = requestParam3DES.get("barcode");//二维码
			cmd = requestParam3DES.get("cmd");// 命令
		} catch (Exception e) {
            LOGGER.error(e.getMessage());
		}
			if(cmd.equals("queryLock")){
					System.out.println("有效二维码");
                    String bicycleNum =getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.CMD_ERROR);
					System.out.println(bicycleNum);
					BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
					if(bikeInfo != null){
						int res = CommonUtils.SendQueryLockCmd(bikeInfo.getSimNo());
						if(res == Constant.Success){
						    return reponseResult(FactoryEnum.OPEN_LOCK_OK);
						}else{
						    return reponseResult(FactoryEnum.OPEN_LOCK_FAIL);
						}
					}else{
					    return reponseResult(FactoryEnum.BICYCLE_NUM_NOT_BINDING);
					}
				}else{
				    return reponseResult(FactoryEnum.BARCODE_URL_ERROR);
				}
	   }



    private Map<String, Object> sendQueryLockCmdBySimNo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		String simNo ="";
		String cmd ="";
		try {
            Map<String, String> requestParam3DES = getRequestParam3DES(request);
            simNo = requestParam3DES.get("simNo");//二维码
			cmd = requestParam3DES.get("cmd");// 命令
		} catch (Exception e) {
            LOGGER.error(e.getMessage());
		}

		if (simNo.length() == 12) {
			if (cmd.equals("queryLock")) {
				BikeInfo bikeInfo = bikeService.getBikeInfoBySimNo(simNo);
				if (bikeInfo == null) {
					int res = CommonUtils.SendQueryLockCmd(simNo);
					if (res == Constant.Success) {
					   return reponseResult(FactoryEnum.OPEN_LOCK_FAIL);
					} else {
					   return reponseResult(FactoryEnum.OPEN_LOCK_FAIL);
					}
				} else {
				    return reponseResult(FactoryEnum.SIM_NO_ALREADY_BINDING);
				}
			} else {
			   return reponseResult(FactoryEnum.CMD_ERROR);
			}
		} else {
		    return reponseResult(FactoryEnum.SIM_NO_ERROR);
		}
	}

    //获取锁的版本好
    private Map<String, Object> queryLock(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
		String barcode ="";
		try {
            Map<String, String> requestParam3DES = getRequestParam3DES(request);
            barcode = requestParam3DES.get("barcode");//二维码
		} catch (Exception e) {
            LOGGER.error(e.getMessage());
		}
				System.out.println("有效二维码");
                String bicycleNum =getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.BARCODE_URL_ERROR);
				System.out.println(bicycleNum);

				BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
				if(bikeInfo != null){
					LockTerminalInfo lockTerminalInfo = lockTerminalService.getInfoBySimNo(bikeInfo.getSimNo());
					if(lockTerminalInfo != null){
					    return reponseResult(FactoryEnum.GET_SIM_NO_INFO_OK,bikeInfo.getVerSoftware(),"onlineStatus",bikeInfo.getOnlineStatus());
					}else{
					    return reponseResult(FactoryEnum.NOT_FOUND_SIM_NO_OK);
					}
				}else{
				    return reponseResult(FactoryEnum.BICYCLE_NUM_NOT_BINDING);
				}
	}

    private Map<String, Object> queryLockBysimNo(HttpServletRequest request) throws NullParameterException {
		String simNo ="";
		try {
            Map<String,String> requestParam3DES=getRequestParam3DES(request);
			simNo = requestParam3DES.get("simNo");//二维码
		} catch (Exception e) {
            LOGGER.error(e.getMessage());
		}
			if (simNo.length()==12){
				BikeInfo bikeInfo = bikeService.getBikeInfoBySimNo(simNo);
				if(bikeInfo == null){
					LockTerminalInfo lockTerminalInfo = lockTerminalService.getInfoBySimNo(simNo);
					if(lockTerminalInfo != null){
					    return reponseResult(FactoryEnum.GET_SIM_NO_INFO_OK,lockTerminalInfo.getVerSoftware());
					}else{
					    return reponseResult(FactoryEnum.NOT_FOUND_SIM_NO_OK);
					}
				}else{
				    return reponseResult(FactoryEnum.SIM_NO_ALREADY_BINDING);
				}
			}else {
		           return reponseResult(FactoryEnum.SIM_NO_ERROR);
			}
	}


    protected Map<String, Object> getSimNoByBarcode(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        String barcode ="";
        try {
            Map<String, String> requestParam3DES = getRequestParam3DES(request);
            barcode = requestParam3DES.get("barcode");//二维码
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
            System.out.println("有效二维码");
            String bicycleNum =getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.BARCODE_URL_ERROR);
            System.out.println(bicycleNum);
            if (bicycleNum.substring(0,2).equals("50")){
                BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
                if(bikeInfo != null){
                    LockTerminalInfo lockTerminalInfo = lockTerminalService.getInfoBySimNo(bikeInfo.getSimNo());
                    if(lockTerminalInfo != null){
                       return reponseResult(FactoryEnum.GET_SIM_NO_INFO_OK,bikeInfo.getSimNo());
                    }else{
                       return reponseResult(FactoryEnum.NOT_FOUND_SIM_NO_OK);
                    }
                }else{
                    return reponseResult(FactoryEnum.GET_BICK_SUPPLIER_NAME_UNFOUND);
                }
            }else {
                return reponseResult(FactoryEnum.GET_BICK_SUPPLIER_NAME_UNFOUND);
            }
    }

    protected Map<String, Object> gprsCloseMotorLock(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        String barcode ="";
        String cmd ="";
        String id="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            barcode = requestParam.get("barcode");//二维码
            cmd = requestParam.get("cmd");// 命令
            id = requestParam.get("id");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        LockFactoryEmployeeInfo lockFactoryEmployeeInfo = lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
        if (lockFactoryEmployeeInfo != null) {
            if (cmd.equals(CMD_CLOSE_MOTOR_LOCK)) {
                    String bicycleNum =getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.BARCODE_URL_ERROR);
                    System.out.println("有效二维码");
                    System.out.println(bicycleNum);

                    BikeInfo bikeInfo; //单车实体
                    ElectricBikeInfo mopedInfo; //助力车实体
                    int res=0;
                    if (StringCommonUtil.startsWithStr(bicycleNum,MOPED_SIGN)){
                        mopedInfo = bikeService.getMopedInfo(bicycleNum);
                        if (mopedInfo==null){
                            return reponseResult(FactoryEnum.BICYCLE_NUM_NOT_BINDING);
                        }
                        res = CommonUtils.SendControlMopedElectriclock(mopedInfo.getSimNo(),OPEN_MOPED_LOCK_FLAG);
                    }else if (StringCommonUtil.startsWithStr(bicycleNum,BICYCLE_SIGN)){
                        bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
                        if (bikeInfo==null){
                            return reponseResult(FactoryEnum.BICYCLE_NUM_NOT_BINDING);
                        }
                        res = CommonUtils.SendOpenLockCmd(bikeInfo.getSimNo());
                    }else {
                        return reponseResult(FactoryEnum.BICYCLE_NUM_ERROR);
                    }
                    if (res == Constant.Success) {
                       return reponseResult(FactoryEnum.OPEN_LOCK_OK);
                    } else {
                        return reponseResult(FactoryEnum.OPEN_LOCK_FAIL);
                    }
            } else {
               return reponseResult(FactoryEnum.CMD_ERROR);
            }

        }
        return null;
    }

    protected Map<String, Object> gprsOpenBatteryLock(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        String barcode ="";
        String cmd ="";
        String id="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            barcode = requestParam.get("barcode");//二维码
            cmd = requestParam.get("cmd");// 命令
            id = requestParam.get("id");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        LockFactoryEmployeeInfo lockFactoryEmployeeInfo = lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
        if (lockFactoryEmployeeInfo != null) {
            if (cmd.equals("openBatteryLock")) {
                    System.out.println("有效二维码");
                    String bicycleNum=getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.BARCODE_URL_ERROR);
                    System.out.println(bicycleNum);

                    BikeInfo bikeInfo; //单车实体
                    ElectricBikeInfo mopedInfo; //助力车实体
                    int res=0;
                    if (StringCommonUtil.startsWithStr(bicycleNum,MOPED_SIGN)){
                        mopedInfo = bikeService.getMopedInfo(bicycleNum);
                        if (mopedInfo==null){
                             return reponseResult(FactoryEnum.BICYCLE_NUM_NOT_BINDING);
                        }
                        res = CommonUtils.SendControlMopedElectriclock(mopedInfo.getSimNo(),OPEN_MOPED_LOCK_FLAG);
                    }else if (StringCommonUtil.startsWithStr(bicycleNum,BICYCLE_SIGN)){
                        bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
                        if (bikeInfo==null){
                            return reponseResult(FactoryEnum.BICYCLE_NUM_NOT_BINDING);
                        }
                        res = CommonUtils.SendOpenLockCmd(bikeInfo.getSimNo());
                    }else {
                        return reponseResult(FactoryEnum.BICYCLE_NUM_ERROR);
                    }
                    if (res == Constant.Success) {
                        return reponseResult(FactoryEnum.OPEN_LOCK_OK);
                    } else {
                        return reponseResult(FactoryEnum.OPEN_LOCK_FAIL);
                    }
            } else {
                return reponseResult(FactoryEnum.CMD_ERROR);
            }
        }
        return null;
    }

    private Map<String, Object> changePsw(HttpServletRequest request) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String oldPassword ="";
        String realName ="";
        String id="";
        String newPassword ="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            oldPassword = requestParam.get("oldPassword");
            realName = URLDecoder.decode(requestParam.get("realName"),"UTF-8");
            id = requestParam.get("id");
            newPassword = requestParam.get("newPassword");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

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
       return resultMap;
    }

    protected Map<String, Object> getKeyPsw(HttpServletRequest request) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String mac ="";
        String id ="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            id = requestParam.get("id");
            mac = requestParam.get("mac");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        LockFactoryEmployeeInfo lockFactoryEmployeeInfo=lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
        if (lockFactoryEmployeeInfo!=null){
            String[] bm = mac.split(":");
            String bluetoothMac ="";
            for(int i=0;i < bm.length;i++){
                bluetoothMac = bluetoothMac + bm[i];
            }
            System.out.println(bluetoothMac);
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
                newLockTerminalInfo.setNewKey(newKey);
                newLockTerminalInfo.setNewPassword(newPassword);
                newLockTerminalInfo.setBluetoothMac(bluetoothMac);
                newLockTerminalInfo.setAddTime(new Date());
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
                lockTerminalInfoTemp.setUpdateTime(new Date());
                resultMap.put("code", "0002");
                resultMap.put("newKey", CommonUtils.PackingFactoryInfo(newKey1,"key"));
                resultMap.put("newPass", CommonUtils.PackingFactoryInfo(newPassword1,"pass"));
                resultMap.put("mac", bluetoothMac);
                resultMap.put("SheBeiID", "");
                resultMap.put("BarCode", "http://www.99bicycle.com?=");
                lockTerminalInfoTempService.updateLockTerminalInfo(lockTerminalInfoTemp);
            }
        }else{
            resultMap.put("result","fail");
            resultMap.put("message", "用户不存在");
        }
        return resultMap;
    }

    protected Map<String, Object> uploadTerminalInfo(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
        Map<String, Object> resultMap =new HashMap<String, Object>();

        String barcode ="";
        String mac ="";
        String id="";
        String factoryNo="";
        String key="";
        String psw="";
        String simNo="";
        String md5reply="";

        try {

            Map<String, String> requestParam = getRequestParam(request);
            barcode = requestParam.get("barcode");//二维码
            mac = requestParam.get("mac");// 命令
            id = requestParam.get("id");
            factoryNo=requestParam.get("factoryNo");
            key= requestParam.get("key");
            psw= requestParam.get("pass");
            simNo=requestParam.get("simNo");
            md5reply=requestParam.get("md5reply");

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.info("发来的信息:" + "蓝牙:" + mac);
        System.out.println("发来的信息:" + "蓝牙:" + mac);

        String bluetoothMac= StringCommonUtil.getRegexStr(mac,":");

        String newKey =StringCommonUtil.getRegexStr(key,",");

        String newPass = StringCommonUtil.getRegexStr(psw,",");

        String bicycleNum = getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.BARCODE_URL_ERROR);

        //判断是simno是否存在，如果存在提示
        BikeInfo bikeInfos = bikeService.getBikeInfoBySimNo(simNo);
        if (null!= bikeInfos){
            final boolean isStartsWithStr = StringCommonUtil.startsWithStr(bicycleNum, WL_START_NO);
            //判断是否是物联的
            if (isStartsWithStr) {
                final int cityNo=bikeInfos.getCityNo();
                final int count = bikeService.getBikecUnbundlingNum(simNo);
                //判断是否已经解绑了 如果CityNo等于0并且count==0
                if (cityNo==0 && count!=0){
                    //更新车辆消息
                    bikeInfos.setBicycleNo(Integer.valueOf(bicycleNum));
                    bikeInfos.setAddTime(new Date());
                    bikeInfos.setNewKey(newKey);
                    bikeInfos.setNewPassword(newPass);
                    bikeInfos.setBluetoothMac(bluetoothMac);
                    bikeInfos.setBicycleLockStatus(0);
                    bikeInfos.setBicycleStatus(0);
                    bikeInfos.setActivityStatus(0);
                    bikeInfos.setBatteryStatus(0);
                    bikeInfos.setBindingStatus(0);
                    bikeInfos.setBreakStatus(0);
                    bikeInfos.setChargeStatus(0);
                    bikeInfos.setCorpse_status(0);
                    bikeInfos.setFence_status(0);
                    bikeInfos.setOnlineStatus(0);
                    bikeInfos.setRecallStatus(0);
                    bikeInfos.setRideStatus(0);
                    bikeInfos.setShutdownStatus(0);
                    bikeInfos.setSuspectBreakStatus(0);
                    bikeService.updateBikeInfo(bikeInfos);

                    //记录以前的消息和更新过的信息
                    BikeUpdateInfo bikeUpdateInfo=new BikeUpdateInfo();
                    bikeUpdateInfo.setAddTimeNew(new Date());
                    bikeUpdateInfo.setAddTimeOld(bikeInfos.getAddTime());
                    bikeUpdateInfo.setBicycleNoNew(bicycleNum);
                    bikeUpdateInfo.setBicycleNoOld(bikeInfos.getBicycleNo());
                    bikeUpdateInfo.setBluetoothMacNew(bluetoothMac);
                    bikeUpdateInfo.setBluetoothMacOld(bikeInfos.getBluetoothMac());
                    bikeUpdateInfo.setNewKeyNew(newKey);
                    bikeUpdateInfo.setNewKeyOld(bikeInfos.getNewKey());
                    bikeUpdateInfo.setNewPasswordNew(newPass);
                    bikeUpdateInfo.setNewPasswordOld(bikeInfos.getNewPassword());
                    bikeUpdateInfo.setSimNo(simNo);
                    bikeUpdateInfo.setCreateTime(new Date());
                    bikeUpdateInfo.setUpdateTime(new Date());
//                    bikeService.recordBikeUpdateInfo(bikeUpdateInfo);

                }else if (cityNo==0 && count==0){
                    //simNo已经绑定过
                    resultMap.put("result", "fail");
                    resultMap.put("message", "绑定失败:此simNo已经存在");
                    return resultMap;
                }else {
                    resultMap.put("result", "fail");
                    resultMap.put("message", "绑定失败:异常情况  cityNo :"+cityNo+" 解绑数量: "+count);
                    System.out.println(JSONObject.valueToString(resultMap));
                    return resultMap;
                }
            }
        }

        LockFactoryEmployeeInfo info =lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
        if (info!=null){
            LockFactoryInfo lockFactoryInfo = lockFactoryInfoService.getLockFactoryInfoByLockFactoryNo(factoryNo);
            if (lockFactoryInfo!=null) {
                String res = CommonUtils.MD5(simNo+mac+lockFactoryInfo.getLockFactoryNo());
                if (res.equalsIgnoreCase(md5reply)) {
                    if (judgeBarcodeURL(BAR_CODE,BAR_CODE)) {
                        System.out.println("有效二维码");

                        System.out.println(bicycleNum);


                        LOGGER.info("解析信息:" + "蓝牙:" + bluetoothMac);
                        System.out.println("解析信息:" + "蓝牙:" + bluetoothMac);
                        BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNumAndBluetoothMac(bicycleNum, bluetoothMac);
                        if (bikeInfo!=null) {
                            resultMap.put("result", "fail");
                            resultMap.put("message", "绑定失败:此二维码已绑定");
                        }else {
                            int resAddBikeEntity = BaiduYingYanUtilTest.addBikeEntity(bicycleNum);
                            if (resAddBikeEntity==Constant.Success) {
                                LOGGER.error("插入鹰眼地图成功" + bicycleNum);
                            }else {
                                LOGGER.error("插入鹰眼地图失败" + bicycleNum);
                            }
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
                            newBikeInfo.setBicycleLockStatus(0);
                            newBikeInfo.setBicycleStatus(0);
                            newBikeInfo.setActivityStatus(0);
                            newBikeInfo.setBatteryStatus(0);
                            newBikeInfo.setBindingStatus(0);
                            newBikeInfo.setBreakStatus(0);
                            newBikeInfo.setChargeStatus(0);
                            newBikeInfo.setCorpse_status(0);
                            newBikeInfo.setFence_status(0);
                            newBikeInfo.setOnlineStatus(0);
                            newBikeInfo.setRecallStatus(0);
                            newBikeInfo.setRideStatus(0);
                            newBikeInfo.setShutdownStatus(0);
                            newBikeInfo.setSuspectBreakStatus(0);
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
                                lockTerminalService.updateLockTerminalInfo(lockTerminalInfo);
                            }
                            System.out.println("----结束写入锁数据---");
                            resultMap.put("result", "ok");
                            resultMap.put("message", "绑定成功");
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
       return resultMap;
    }

    //返回蓝牙开锁所需要参数
    private Map<String, Object> reponseBluetoothParam(Object object,Integer flag){
        Map<String,Object> resultMap=new HashMap<>();

          if(object==null || flag==null){
              resultMap.put("mac", "");
              resultMap.put("key", "");
              resultMap.put("pass","");
              System.out.println(JSONObject.valueToString(resultMap));
              return resultMap;
          }
        Class aClass=object.getClass();
        Method method;
        try {
            method = aClass.getMethod("getNewKey");
            String newKey =(String) method.invoke(object);

            method = aClass.getMethod("getBluetoothMac");
            String bluetoothMac =(String) method.invoke(object);

            method = aClass.getMethod("getNewPassword");
            String newPassword =(String) method.invoke(object);

            resultMap.put("mac", CommonUtils.PackingFactoryInfo(bluetoothMac, "mac"));
            resultMap.put("key", CommonUtils.PackingFactoryInfo(newKey, "key"));
            resultMap.put("pass",CommonUtils.PackingFactoryInfo(newPassword, "pass"));
            resultMap.put("flag",flag);
            System.out.println(JSONObject.valueToString(resultMap));
            return resultMap;
        } catch (NoSuchMethodException e) {
            LOGGER.error(e.getMessage());
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        } catch (InvocationTargetException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }
}
