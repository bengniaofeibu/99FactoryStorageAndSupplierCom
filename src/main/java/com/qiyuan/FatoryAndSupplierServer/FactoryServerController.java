package com.qiyuan.FatoryAndSupplierServer;

import com.daoshun.exception.NullParameterException;
import com.google.gson.Gson;
import com.qiyuan.Base.BaseController;
import com.qiyuan.baiduUtil.BaiduYingYanUtilTest;
import com.qiyuan.common.CommonUtils;
import com.qiyuan.common.Constant;
import com.qiyuan.common.PushtoSingle;
import com.qiyuan.entity.CancelBikeInfo;
import com.qiyuan.entity.Result;
import com.qiyuan.enums.FactoryEnum;
import com.qiyuan.pojo.*;
import com.qiyuan.service.*;
import com.qiyuan.terminalService.ApiClientConstantService;
import com.qiyuan.terminalService.GetSMSDetailsService;
import com.qiyuan.terminalService.SendSMSService;
import com.qiyuan.utils.StringCommonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
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

    /**
     * 获取锁的状态
     */
    @Resource(name = "apiClientConstantService")
    private ApiClientConstantService apiClientConstantService;

    @Resource(name="sendSMSService")
    private SendSMSService sendSMSService;

    @Resource(name="getSMSDetailsService")
    private GetSMSDetailsService getSMSDetailsService;



    //接口入口
    protected void factorCallingService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = getAction(request, response);
            //锁的接口
            switch (action) {
                case Constant.LOGIN:
                    Login(request, response);//登入接口
                    break;
                case Constant.UPLOADTERMINALINFO:
                    uploadTerminalInfo(request, response);//入库接口
                    break;
                case Constant.GETBLUETOOTHINFO:
                    getBluetoothInfo(request, response);//入库蓝牙开锁接口
                    break;
                case Constant.GETBLUETOOTHINFOBYID:
                    getBluetoothInfoById(request, response);//赳赳开锁蓝牙开锁接口
                    break;
               /* case Constant.FACTORYGPRSOPENLOCK:
                    factoryGprsOpenLock(request, response);//入库GPRS开锁接口
                    break;*/
                case Constant.FACTORYGPRSOPENLOCKBYID:
                    factoryGprsOpenLockById(request, response);////开单车电机锁或助力车电机锁
                    break;
                case Constant.FACTORYGPRSOPENLOCKBYSIMNO:
                    factoryGprsOpenLockBySimNo(request, response);
                    break;
                case Constant.ISSTORAGE:
                    isStorage(request, response);//车辆是否入库
                    break;
                case Constant.QUERYLOCKREALDATA:
                    queryLockRealData(request, response);//查询锁详情接口
                    break;
                case Constant.GETAPPVERSION:
                    getAppVersion(request, response);//获取app版本信息接口
                    break;
                case Constant.GETKEYPSW:
                    getKeyPsw(request, response);//获取key，psw接口
                    break;
                case Constant.CHANGEPSW:
                    changePsw(request, response);//更换app密码接口
                    break;
                case Constant.LOGOUT:
                    logOut(request, response);//登出接口
                    break;
                case Constant.CHANGEBARCODE:
                    changeBarcode(request, response);//更换二维码接口
                    break;
                case Constant.GPRSOPENBATTERYLOCK:
                    gprsOpenBatteryLock(request, response);//GPRS开电池锁
                    break;
                case Constant.GPRSCLOSEMOTORLOCK:
                    gprsCloseMotorLock(request, response);//GPRS开电机锁
                    break;
                case Constant.GETSIMNOBYBARCODE:
                    getSimNoByBarcode(request, response); //获取simNo
                    break;
                case Constant.QUERYLOCKBYSIMNO:
                    queryLockBysimNo(request,response);//入库前查询版本接口
                    break;
                case Constant.QUERYLOCK:
                    queryLock(request,response);//查询锁版本接口
                    break;
                case Constant.SENDQUERYLOCKCMDBYSIMNO:
                    sendQueryLockCmdBySimNo(request,response);//入库前下发查询锁版本命令接口
                    break;
                case Constant.SENDQUERYLOCKCMD:
                    sendQueryLockCmd(request,response);//下发查询锁版本命令接口
                    break;
                case Constant.GETBARCODEBYSIMNO:
                    getBarcodeBySimNo(request, response);
                    break;
                case Constant.GET_LIANTONG_LOCK_STATUS:
                    getBicycleLockStatus(response);//获取车辆锁的状态(联通)
                    break;
                case  Constant.SEND_SMS_LOCK:
                    sendSmSLock(response); //发送短信到设备上(联通)
                    break;
                case  Constant.GET_SMS_DETAILS:
                    getSMSDetails(response); //获取短信是否送达到设备上的状态(联通)
                    break;
                case Constant.GETBIKEINFO:
                    getBikeInfo(request,response);
                    break;
                case Constant.OPEN_BATTERY_LOCK:
                    openBatteryLock(request,response);//开助力车电池锁接口
                    break;
                case Constant.CONTRO_LELECTRIC_LOCK:
                    closeMopedLectricLock(request,response);//关助力车电机锁接口
                    break;
                case Constant.GET_CANCELLATION_LOCK_INFO:
                    getCancellationLockInfo(request,response); //获取注销锁的信息
                    break;
                default:
                    throw new NullParameterException();
            }

        } catch (Exception e) {
            e.printStackTrace();
            setResultWhenException(response, e.getMessage());
        }
    }

    //获取短信是否送达到设备上的状态(联通)
    private void getSMSDetails(HttpServletResponse response) {
        try {
            String smsId=getReqParam("smsId");
            Result result = getSMSDetailsService.getSMSDetails(smsId);
            if (result.getCode()== 200){
                reponseResult(response,FactoryEnum.GET_SMS_DETAILS_OK,result.getData());
            }else{
                reponseResult(response,FactoryEnum.GET_SMS_DETAILS_FAIL);
            }
        } catch (Exception e) {
            setResultWhenException(response,e.getMessage());
        }

    }

    //获取车辆锁的状态(联通)
    private  void getBicycleLockStatus(HttpServletResponse response){
        try {
            String iccid=getReqParam("iccid");
            Result result = apiClientConstantService.callWebService(iccid);
            if (result.getCode()== 200){
                reponseResult(response,FactoryEnum.GET_BICYLE_LOCK_OK,result.getData());
            }else{
                reponseResult(response,FactoryEnum.GET_BICYLE_LOCK_FAIL);
            }
        } catch (Exception e) {
            setResultWhenException(response,e.getMessage());
        }

    }

    //发送短信到设备上(联通)
    private  void sendSmSLock(HttpServletResponse response){
        try {
            String iccid=getReqParam("iccid");
            String message=getReqParam("message");
            byte tpvp=Byte.valueOf(getReqParam("tpvp"));
            Result result = sendSMSService.sendSMSService(iccid,message,tpvp);
            if (result.getCode()== 200){
                reponseResult(response,FactoryEnum.SMS_SEND_OK,result.getData());
            }else{
                reponseResult(response,FactoryEnum.SMS_SEND_FAIL);
            }
        } catch (Exception e) {
            setResultWhenException(response,e.getMessage());
        }

    }

    private void changeBarcode(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
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
            e.printStackTrace();
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

        setResult(response, resultMap);
    }

    protected void getAppVersion(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<>();

        String id ="";
        String appId ="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            id = requestParam.get("id");
            appId = requestParam.get("appId");
        } catch (Exception e) {
            e.printStackTrace();
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
        setResult(response, resultMap);
    }


    protected void queryLockRealData(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
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


    protected void isStorage(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
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

    /*protected void factoryGprsOpenLock(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
        Map<String, Object> resultMap =new HashMap<String, Object>();
        String barcode ="";
        String cmd ="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            barcode = requestParam.get("barcode");//二维码
            cmd = requestParam.get("cmd");// 命令
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(CMD_GPRS_OPEN_LOCK.equals(cmd)){
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
    }*/

    //开单车电机锁或助力车电机锁 （6开头的是助力车 ，5开头是单车）
    protected void factoryGprsOpenLockById(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
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
            e.printStackTrace();
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
                            reponseResult(response,FactoryEnum.BICYCLE_NUM_NOT_BINDING);
                        }
                        res = CommonUtils.SendControlMopedElectriclock(mopedInfo.getSimNo(),OPEN_MOPED_LOCK_FLAG);
                    }else if (StringCommonUtil.startsWithStr(bicycleNum,BICYCLE_SIGN)){
                        //单车
                         bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
                        if (bikeInfo==null){
                            reponseResult(response,FactoryEnum.BICYCLE_NUM_NOT_BINDING);
                        }
                        res = CommonUtils.SendOpenLockCmd(bikeInfo.getSimNo());
                    }else {
                        reponseResult(response, FactoryEnum.BICYCLE_NUM_ERROR);
                    }

                    if (res == Constant.Success) {
                        reponseResult(response, FactoryEnum.OPEN_LOCK_OK);
                    } else {
                        reponseResult(response, FactoryEnum.OPEN_LOCK_FAIL);
                    }

            } else {
                reponseResult(response, FactoryEnum.CMD_ERROR);
            }

        }
    }

    protected void factoryGprsOpenLockBySimNo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<>();

        String simNo ="";
        String cmd ="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            simNo = requestParam.get("simNo");//二维码
            cmd = requestParam.get("cmd");
        } catch (Exception e) {
            e.printStackTrace();
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


        setResult(response, resultMap);

    }

    protected void logOut(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<>();

        String id ="";
        String type ="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            id = requestParam.get("id");
            type = requestParam.get("type");
        } catch (Exception e) {
            e.printStackTrace();
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
        setResult(response, resultMap);
    }

    protected void getBluetoothInfo(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
        Map<String, Object> resultMap =new HashMap<String, Object>();
        String barcode ="";
        String cmd ="";
        try {
            Map<String, String> requestParam = getRequestParam3DES(request);
            barcode = requestParam.get("barcode");//二维码
            cmd = requestParam.get("cmd");// 命令
        } catch (Exception e) {
            e.printStackTrace();
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
        setResult(response, resultMap);
    }

    protected void getBluetoothInfoById(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        String barcode ="";
        String cmd ="";
        String id="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            barcode = requestParam.get("barcode");//二维码
            cmd = requestParam.get("cmd");// 命令
            id = requestParam.get("id");
        } catch (Exception e) {
            e.printStackTrace();
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
                            reponseBluetoothParam(response,mopedInfo,FLAG_1);
                        }else{
                            //已经注销
                            CancellationBikeInfo cancellationBikeInfo = bikeService.getCancellationBikeInfo(bicycleNum);
                            if (cancellationBikeInfo!=null){
                                reponseBluetoothParam(response,cancellationBikeInfo,FLAG_0);
                            } else {
                                reponseBluetoothParam(response,null,null);
                            }
                        }
                    }else if (StringCommonUtil.startsWithStr(bicycleNum,BICYCLE_SIGN)){
                        //单车
                        info = bikeService.getBikeInfoByBicycleNum(bicycleNum);
                        if(info != null){
                            reponseBluetoothParam(response,info,FLAG_1);
                        }else{
                            CancellationBikeInfo cancellationBikeInfo = bikeService.getCancellationBikeInfo(bicycleNum);
                            if (cancellationBikeInfo!=null){
                                reponseBluetoothParam(response,cancellationBikeInfo,FLAG_0);
                            } else {
                                reponseBluetoothParam(response,null,null);
                            }
                        }
                    }else {
                        reponseResult(response, FactoryEnum.BICYCLE_NUM_ERROR);
                    }
                }else{
                    if(StringCommonUtil.startsWithStr(barcode,MOPED_SIGN) && barcode.length() == 9 ){
                        //助力车
                        ElectricBikeInfo mopedInfo = bikeService.getMopedInfo(barcode);
                        if(mopedInfo != null){
                            reponseBluetoothParam(response,mopedInfo,FLAG_1);
                        }else{
                            CancellationBikeInfo cancellationBikeInfo = bikeService.getCancellationBikeInfo(barcode);
                            if (cancellationBikeInfo!=null){
                                reponseBluetoothParam(response,cancellationBikeInfo,FLAG_0);
                            } else {
                                reponseBluetoothParam(response,null,null);
                            }
                        }
                    }else if (StringCommonUtil.startsWithStr(barcode,BICYCLE_SIGN) && barcode.length() == 9){
                        //单车
                        BikeInfo info = bikeService.getBikeInfoByBicycleNum(barcode);
                        if(info != null){
                            reponseBluetoothParam(response,info,FLAG_1);
                        }else{
                            CancellationBikeInfo cancellationBikeInfo = bikeService.getCancellationBikeInfo(barcode);
                            if (cancellationBikeInfo!=null){
                                reponseBluetoothParam(response,cancellationBikeInfo,FLAG_0);
                            } else {
                                reponseBluetoothParam(response,null,null);
                            }
                        }
                    } else{
                        reponseBluetoothParam(response,null,null);
                    }

                }
            }else{
                reponseBluetoothParam(response,null,null);
            }
        }else{
           reponseResult(response,FactoryEnum.USER_NOT_FOUND_FAIL);
        }
    }


    protected void  Login(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
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
            e.printStackTrace();
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

        setResult(response, resultMap);
    }


    //获取注销锁的信息
    private void  getCancellationLockInfo(HttpServletRequest request,HttpServletResponse response){
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
                    Gson gson=new Gson();
                    CancelBikeInfo map=gson.fromJson(lockInfo.get("data").toString(), CancelBikeInfo.class);
                    reponseResult(response, FactoryEnum.GET_CANCELLATION_LOCK_INFO_OK,map,"flag",FLAG_0);
                }else {
                    reponseResult(response,FactoryEnum.GET_CANCELLATION_LOCK_INFO_FAIL,"");
                }
            }else {
                reponseResult(response,FactoryEnum.GET_BIKE_INFO_FAIL,"","flag",FLAG_1);
            }
        }catch (Exception e){
           setResultWhenException(response,e.getMessage());
        }
    }

    //开助力车电池锁接口
    private  void   openBatteryLock(HttpServletRequest request,HttpServletResponse response){

        try {
            Map<String, String> requestParam = getRequestParam(request);
            String barcode = requestParam.get("barcode");//二维码
            String bicycleNum = getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.BARCODE_URL_ERROR);
            ElectricBikeInfo mopedInfo = bikeService.getMopedInfo(bicycleNum);
            if (mopedInfo==null){
                reponseResult(response, FactoryEnum.BICYCLE_NUM_NOT_BINDING);
                return;
            }
//            String openBatteryLock=getReqParam("openBatteryLock");
            int repInfo = CommonUtils.SendOpenMopedBatteryLock(mopedInfo.getSimNo());
            if (repInfo==Constant.Success) {
                reponseResult(response, FactoryEnum.OPEN_LOCK_OK);
            }else{
                reponseResult(response, FactoryEnum.OPEN_LOCK_FAIL);
            }
        }catch (Exception e){
            setResultWhenException(response,e.getMessage());
        }
    }

    //操作助力车电机锁接口
    private  void  closeMopedLectricLock(HttpServletRequest request,HttpServletResponse response){
        try {
            Map<String, String> requestParam = getRequestParam(request);
            String barcode = requestParam.get("barcode");//二维码
            String bicycleNum = getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.BARCODE_URL_ERROR);
            ElectricBikeInfo mopedInfo = bikeService.getMopedInfo(bicycleNum);
            Map<String, Object> resultMap = null;
            if (mopedInfo==null){
                resultMap=getReponseMap(FactoryEnum.BICYCLE_NUM_NOT_BINDING);
                setResult(response,resultMap);
                return;
            }
//            String closeMotorLock=getReqParam("closeMotorLock");
//            Integer flag = Integer.valueOf(getReqParam("flag"));
            int repInfo=CommonUtils.SendControlMopedElectriclock(mopedInfo.getSimNo(),CLOSE_MOPED_LOCK_FLAG);
            if (repInfo==Constant.Success) {
                reponseResult(response, FactoryEnum.OPEN_LOCK_OK);
            }else{
                reponseResult(response, FactoryEnum.OPEN_LOCK_FAIL);
            }
        }catch (Exception e){
            setResultWhenException(response,e.getMessage());
        }
    }

    private void getBikeInfo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
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
        setResult(response,resultMap);
    }


    protected void getBarcodeBySimNo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
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
                        reponseResult(response,FactoryEnum.GET_SIM_NO_INFO_OK,barcode.toString());
                    } else {
                        reponseResult(response,FactoryEnum.NOT_FOUND_SIM_NO_OK);
                    }
                } else {
                    reponseResult(response,FactoryEnum.GET_BICK_SUPPLIER_NAME_UNFOUND);
                }
            } else {
                reponseResult(response,FactoryEnum.GET_BICK_SUPPLIER_NAME_UNFOUND);
            }
        }else {
            reponseResult(response,FactoryEnum.SIM_NO_ERROR);
        }
        } catch (Exception e) {
            setResultWhenException(response,e.getMessage());
        }
        setResult(response, resultMap);
    }

    private void sendQueryLockCmd(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
		Map<String, Object> resultMap =new HashMap<>();
		String barcode ="";
		String cmd ="";
		try {
            Map<String, String> requestParam3DES = getRequestParam3DES(request);
            barcode = requestParam3DES.get("barcode");//二维码
			cmd = requestParam3DES.get("cmd");// 命令
		} catch (Exception e) {
			e.printStackTrace();
		}
			if(cmd.equals("queryLock")){
					System.out.println("有效二维码");
                    String bicycleNum =getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.CMD_ERROR);
					System.out.println(bicycleNum);
					BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
					if(bikeInfo != null){
						int res = CommonUtils.SendQueryLockCmd(bikeInfo.getSimNo());
						if(res == Constant.Success){
						    reponseResult(response,FactoryEnum.OPEN_LOCK_OK);
						}else{
						    reponseResult(response,FactoryEnum.OPEN_LOCK_FAIL);
						}
					}else{
					    reponseResult(response,FactoryEnum.BICYCLE_NUM_NOT_BINDING);
					}
				}else{
				    reponseResult(response,FactoryEnum.BARCODE_URL_ERROR);
				}

		setResult(response, resultMap);
	}



    private void sendQueryLockCmdBySimNo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap = new HashMap<>();

		String simNo ="";
		String cmd ="";
		try {
            Map<String, String> requestParam3DES = getRequestParam3DES(request);
            simNo = requestParam3DES.get("simNo");//二维码
			cmd = requestParam3DES.get("cmd");// 命令
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (simNo.length() == 12) {
			if (cmd.equals("queryLock")) {
				BikeInfo bikeInfo = bikeService.getBikeInfoBySimNo(simNo);
				if (bikeInfo == null) {
					int res = CommonUtils.SendQueryLockCmd(simNo);
					if (res == Constant.Success) {
					    reponseResult(response,FactoryEnum.OPEN_LOCK_FAIL);
					} else {
					    reponseResult(response,FactoryEnum.OPEN_LOCK_FAIL);
					}
				} else {
				    reponseResult(response,FactoryEnum.SIM_NO_ALREADY_BINDING);
				}
			} else {
			    reponseResult(response,FactoryEnum.CMD_ERROR);
			}
		} else {
		    reponseResult(response,FactoryEnum.SIM_NO_ERROR);
		}

		setResult(response, resultMap);

	}


    private void queryLock(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
		Map<String, Object> resultMap =new HashMap<>();

		String barcode ="";
		try {
            Map<String, String> requestParam3DES = getRequestParam3DES(request);
            barcode = requestParam3DES.get("barcode");//二维码
		} catch (Exception e) {
			e.printStackTrace();
		}
				System.out.println("有效二维码");
                String bicycleNum =getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.BARCODE_URL_ERROR);
				System.out.println(bicycleNum);

				BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
				if(bikeInfo != null){
					LockTerminalInfo lockTerminalInfo = lockTerminalService.getInfoBySimNo(bikeInfo.getSimNo());
					if(lockTerminalInfo != null){
					    reponseResult(response,FactoryEnum.GET_SIM_NO_INFO_OK,bikeInfo.getVerSoftware());
					}else{
					    reponseResult(response,FactoryEnum.NOT_FOUND_SIM_NO_OK);
					}
				}else{
				    reponseResult(response,FactoryEnum.BICYCLE_NUM_NOT_BINDING);
				}
		setResult(response, resultMap);
	}

    private void queryLockBysimNo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
		Map<String, Object> resultMap = new HashMap<>();
		String simNo ="";
		try {
            Map<String,String> requestParam3DES=getRequestParam3DES(request);
			simNo = requestParam3DES.get("simNo");//二维码
		} catch (Exception e) {
			e.printStackTrace();
		}
			if (simNo.length()==12){
				BikeInfo bikeInfo = bikeService.getBikeInfoBySimNo(simNo);
				if(bikeInfo == null){
					LockTerminalInfo lockTerminalInfo = lockTerminalService.getInfoBySimNo(simNo);
					if(lockTerminalInfo != null){
					    reponseResult(response,FactoryEnum.GET_SIM_NO_INFO_OK,lockTerminalInfo.getVerSoftware());
					}else{
					    reponseResult(response,FactoryEnum.NOT_FOUND_SIM_NO_OK);
					}
				}else{
				    reponseResult(response,FactoryEnum.SIM_NO_ALREADY_BINDING);
				}
			}else {
		           reponseResult(response,FactoryEnum.SIM_NO_ERROR);
			}

		setResult(response, resultMap);

	}


    protected void getSimNoByBarcode(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String barcode ="";
        try {
            Map<String, String> requestParam3DES = getRequestParam3DES(request);
            barcode = requestParam3DES.get("barcode");//二维码
        } catch (Exception e) {
            e.printStackTrace();
        }
            System.out.println("有效二维码");
            String bicycleNum =getBicycleNum(response,barcode,BAR_CODE,FactoryEnum.BARCODE_URL_ERROR);
            System.out.println(bicycleNum);
            if (bicycleNum.substring(0,2).equals("50")){
                BikeInfo bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
                if(bikeInfo != null){
                    LockTerminalInfo lockTerminalInfo = lockTerminalService.getInfoBySimNo(bikeInfo.getSimNo());
                    if(lockTerminalInfo != null){
                        reponseResult(response,FactoryEnum.GET_SIM_NO_INFO_OK,bikeInfo.getSimNo());
                    }else{
                        reponseResult(response,FactoryEnum.NOT_FOUND_SIM_NO_OK);
                    }
                }else{
                    reponseResult(response,FactoryEnum.GET_BICK_SUPPLIER_NAME_UNFOUND);
                }
            }else {
                reponseResult(response,FactoryEnum.GET_BICK_SUPPLIER_NAME_UNFOUND);
            }

        setResult(response, resultMap);
    }

    protected void gprsCloseMotorLock(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        String barcode ="";
        String cmd ="";
        String id="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            barcode = requestParam.get("barcode");//二维码
            cmd = requestParam.get("cmd");// 命令
            id = requestParam.get("id");
        } catch (Exception e) {
            e.printStackTrace();
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
                            reponseResult(response,FactoryEnum.BICYCLE_NUM_NOT_BINDING);
                        }
                        res = CommonUtils.SendControlMopedElectriclock(mopedInfo.getSimNo(),OPEN_MOPED_LOCK_FLAG);
                    }else if (StringCommonUtil.startsWithStr(bicycleNum,BICYCLE_SIGN)){
                        bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
                        if (bikeInfo==null){
                            reponseResult(response,FactoryEnum.BICYCLE_NUM_NOT_BINDING);
                        }
                        res = CommonUtils.SendOpenLockCmd(bikeInfo.getSimNo());
                    }else {
                        reponseResult(response, FactoryEnum.BICYCLE_NUM_ERROR);
                    }
                    if (res == Constant.Success) {
                        reponseResult(response, FactoryEnum.OPEN_LOCK_OK);
                    } else {
                        reponseResult(response, FactoryEnum.OPEN_LOCK_FAIL);
                    }
            } else {
                reponseResult(response,FactoryEnum.CMD_ERROR);
            }

        }
    }

    protected void gprsOpenBatteryLock(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        String barcode ="";
        String cmd ="";
        String id="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            barcode = requestParam.get("barcode");//二维码
            cmd = requestParam.get("cmd");// 命令
            id = requestParam.get("id");
        } catch (Exception e) {
            e.printStackTrace();
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
                             reponseResult(response,FactoryEnum.BICYCLE_NUM_NOT_BINDING);
                        }
                        res = CommonUtils.SendControlMopedElectriclock(mopedInfo.getSimNo(),OPEN_MOPED_LOCK_FLAG);
                    }else if (StringCommonUtil.startsWithStr(bicycleNum,BICYCLE_SIGN)){
                        bikeInfo = bikeService.getBikeInfoByBicycleNum(bicycleNum);
                        if (bikeInfo==null){
                            reponseResult(response,FactoryEnum.BICYCLE_NUM_NOT_BINDING);
                        }
                        res = CommonUtils.SendOpenLockCmd(bikeInfo.getSimNo());
                    }else {
                        reponseResult(response, FactoryEnum.BICYCLE_NUM_ERROR);
                    }
                    if (res == Constant.Success) {
                        reponseResult(response, FactoryEnum.OPEN_LOCK_OK);
                    } else {
                        reponseResult(response, FactoryEnum.OPEN_LOCK_FAIL);
                    }
            } else {
                reponseResult(response,FactoryEnum.CMD_ERROR);
            }
        }
    }

    private void changePsw(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
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
            e.printStackTrace();
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
        setResult(response, resultMap);
    }

    protected void getKeyPsw(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String mac ="";
        String id ="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            id = requestParam.get("id");
            mac = requestParam.get("mac");
        } catch (Exception e) {
            e.printStackTrace();
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
        setResult(response, resultMap);
    }

    protected void uploadTerminalInfo(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
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
            e.printStackTrace();
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
                    setResult(response, resultMap);
                    return;
                }else {
                    resultMap.put("result", "fail");
                    resultMap.put("message", "绑定失败:异常情况  cityNo :"+cityNo+" 解绑数量: "+count);
                    setResult(response, resultMap);
                    return;
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

        setResult(response, resultMap);
    }

    //返回蓝牙开锁所需要参数
    private void reponseBluetoothParam(HttpServletResponse response,Object object,Integer flag){
        Map<String,Object> resultMap=new HashMap<>();

          if(object==null || flag==null){
              resultMap.put("mac", "");
              resultMap.put("key", "");
              resultMap.put("pass","");
              setResult(response,resultMap);
              return;
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
            setResult(response,resultMap);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
