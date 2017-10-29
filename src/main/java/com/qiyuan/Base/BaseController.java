package com.qiyuan.Base;

import com.daoshun.exception.NullParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.qiyuan.baiduUtil.BaiduYingYanUtilTest;
import com.qiyuan.common.*;
import com.qiyuan.entity.Result;
import com.qiyuan.enums.EnumService;
import com.qiyuan.enums.LockReponseEnum;
import com.qiyuan.enums.ServiceNameEnum;
import com.qiyuan.enums.SupplierEnum;
import com.qiyuan.pojo.*;
import com.qiyuan.service.*;
import com.qiyuan.terminalService.ApiClientConstantService;
import com.qiyuan.utils.StringCommonUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * @author : tonghengzhen
 * Date: 2017/10/20
 * Time: 14:01
 */
public class BaseController extends HttpServlet {

    private   final Log LOGGER = LogFactory.getLog(getClass());

    private  static final  String BAR_CODE="http://www.99bicycle.com";

    private  static  final  String WL_START_NO="50";

    private  static  final String  CHANGE_LOCK_SUPPLIER_NAME="锁厂换锁绑定";

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


    @Resource
    private ISupplierService supplierService;
    @Resource
    private IBikeOutStorgeService bikeOutStorgeService;
    @Resource
    private IFactoryQuantityService factoryQuantityService;
    @Resource
    private IBikeService iBikeService;
    @Resource
    private ILockGPSRealDataService lockGPSRealDataService;

    /**
     * 获取锁的状态
     */
    @Resource(name = "apiClientConstantService")
    private ApiClientConstantService apiClientConstantService;

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Map<String, Object> paramMap = null;



    //接口入口
    protected Result callingService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Result result=null;
        try {
            String action = getAction(request, response);

            //锁的接口
            if (isRequestUrl(ServiceNameEnum.FACTORY)) switch (action) {
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
                case Constant.FACTORYGPRSOPENLOCK:
                    factoryGprsOpenLock(request, response);//入库GPRS开锁接口
                    break;
                case Constant.FACTORYGPRSOPENLOCKBYID:
                    factoryGprsOpenLockById(request, response);
                    break;
                case Constant.FACTORYGPRSOPENLOCKBYSIMNO:
                    factoryGprsOpenLockBySimNo(request, response);
                    break;
                case Constant.ISSTORAGE:
                    isStorage(request, response);//车辆是否入库aaa
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
                    getSimNoByBarcode(request, response);
                    break;
                case Constant.GETBARCODEBYSIMNO:
                    getBarcodeBySimNo(request, response);
                    break;
                case Constant.GET_LIANTONG_LOCK_STATUS:
                    String iccid=getReqParam("iccid");
                    //根据iccid获取车辆锁的状态
                    result=apiClientConstantService.callWebService(iccid);
                    return  result;
                case Constant.GETBIKEINFO:
                    getBikeInfo(request,response);
                 break;
                default:
                    throw new NullParameterException();
            }

            //车厂的接口
            if (isRequestUrl(ServiceNameEnum.SUPPLIER)){
                switch (action){
                    case Constant.SUPPLIERUPLOADBICYCLE:
                        uploadBicycleNum(request,response);
                     break;
                    case Constant.SUPPLIERLOGIN:
                        uploadSupplierInfo(request,response);
                     break;
                    case Constant.GETFACTORYQUANTITY:
                        getFactoryQuantity(request,response);
                     break;
                    case Constant.OUTLOGIN:
                        outLogin(request,response);
                     break;
                    case Constant.DAILYOUTPUT:
                        dailyOutput(request,response);
                     break;
                    case Constant.DEREGISTRATION:
                        deregistration(request,response);
                     break;
                    case Constant.GET_BICK_SUPPLIER_NAME:
                        getBickSupplierName(response);//根据车辆浩获取厂商名称
                     break;
                    case Constant.CHANGE_BICYCLE_LOCK:
                         changeBicycleLock(response); //锁厂换锁绑定
                     break;
                     default:
                         throw new NullParameterException();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            setResultWhenException(response, e.getMessage());
        }
        return  null;
    }


    /**
     * Servlet 返回结果
     * @param resultMap
     * @param response
     */
    private void setResult(HttpServletResponse response, Map<String, Object> resultMap) {
        response.setContentType("text/html;charset=UTF-8");
//		if (resultMap.get("code") == null) {
//			resultMap.put(Constant.CODE, 1);
////			resultMap.put(Constant.MESSAGE, "操作成功");
//		}
        try {
            PrintWriter out = response.getWriter();
            // json格式转换
            Gson gson = new GsonBuilder()/*.setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping()*/.create();
            String json = gson.toJson(resultMap);
            out.print(json);
            System.out.println(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            resultMap.clear();
        }
    }


    private void setResult(HttpServletResponse response, LockReponseEnum lockReponseEnum,Object objectDate) {
        response.setContentType("text/html;charset=UTF-8");
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("errorCode",lockReponseEnum.getCode());
        dataMap.put("msg",lockReponseEnum.getMsg());
        dataMap.put("data",objectDate);
        try {
            PrintWriter out = response.getWriter();
            // json格式转换
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping().create();
            String json = gson.toJson(dataMap);
            out.print(json);
            System.out.println(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            dataMap.clear();
        }
    }



    public void setResultWhenException(HttpServletResponse response, String errmsg) {
        response.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            out.print(errmsg);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private String getAction(HttpServletRequest request, HttpServletResponse response) {
        String action = null;
        // 上传文件的场合
        if (ServletFileUpload.isMultipartContent(request)) {
            // 设定文件上传的路径
            String localPath = CommonUtils.getLocationPath() + CommonUtils.getTimeFormat(new Date(), "yyyyMMdd") + File.separator;
            CommonUtils.checkPath(localPath);
            // 获得磁盘文件工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("utf-8");
            try {
                List<FileItem> items = upload.parseRequest(request);
                Iterator<FileItem> iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = iter.next();
                    // 文件名称取得
                    String fieldname = item.getFieldName();
                    final String uploadfilename = item.getFieldName();
                    if (item.isFormField()) {
                        // 普通文本信息处理
                        String value = item.getString("UTF-8");
                        request.setAttribute(fieldname, value);
                    } else {
                        // 上传文件
                        if (fieldname.indexOf(".") == -1) {
                            fieldname += ".jpg";
                        }
                        String extension = fieldname.substring(fieldname.lastIndexOf(".")).toLowerCase();
                        // 图片存放的名字 时间戳加图片后缀名
                        String filename = CommonUtils.getTimeFormat(new Date(), "hhmmssSSS") + "_" + (int) (Math.random() * 100) + "origin" + extension;
                        item.write(new File(localPath + filename));
                        long fileid = 0;
                        // 压缩图片
                        if (ImageUtil.isImage(filename)) {
                            String filenameEX = filename.replace("origin", "");
                            ImageUtil.scale4(localPath + filename, localPath + filenameEX, 999999999, 300);
//							fileid = baseService.uploadComplete(localPath.replace(CommonUtils.getLocationPath(), ""), filenameEX);
                        } else {
//							fileid = baseService.uploadComplete(localPath.replace(CommonUtils.getLocationPath(), ""), filename);
                        }
//						System.out.println(fieldname.lastIndexOf("."));
                        request.setAttribute(uploadfilename, fileid + "");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // action取得
        action = getRequestParamter(request, "action");

        if (action != null) {
            action = action.toUpperCase();
        } else {
            try {
                paramMap = new Gson().fromJson(new InputStreamReader(request.getInputStream(), "utf-8"), new TypeToken<Map<String, Object>>() {
                }.getType());
            } catch (JsonIOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JsonSyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (paramMap != null) {
                Iterator<Map.Entry<String, Object>> iterator = paramMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    String key = entry.getKey();
                    if (key.equals("action")) {
                        action = String.valueOf(entry.getValue()).toUpperCase();
                    } else {
                        request.setAttribute(key, entry.getValue());
                    }
                }
                paramMap = null;
            }
        }
        return action;
    }

    private String getRequestParamter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value == null) {
            value = (String) request.getAttribute(name);
        }
        return value;
    }

    protected void test(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
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
        System.out.println(realName);

        CommonUtils.validateEmpty(realName);
        System.out.println(passWord);
        CommonUtils.validateEmpty(passWord);
        System.out.println(adminCid);
        CommonUtils.validateEmpty(adminCid);
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
        String simNo ="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            simNo = requestParam.get("simNo");//设备编号
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    protected void getSimNoByBarcode(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String barcode ="";
        try {
            Map<String, String> requestParam = getRequestParam(request);
            barcode = requestParam.get("barcode");//二维码
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    protected void gprsCloseMotorLock(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
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

    protected void gprsOpenBatteryLock(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
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





    protected void changeBarcode(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
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


    protected void factoryGprsOpenLockById(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<String, Object>();


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
            if (cmd.equals("gprsOpenLock")) {
                if (barcode.contains("http://www.99bicycle.com")) {
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

    protected void getBluetoothInfoById(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap =new HashMap<String, Object>();
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
                if(barcode.contains("http://www.99bicycle.com")){
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

//	protected void queryLockBysimNo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
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

//	protected void sendQueryLockCmdBySimNo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
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

//	protected void updateLockBySimNo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
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

    protected void factoryGprsOpenLockBySimNo(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<String, Object>();

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

    protected void logOut(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<String, Object>();

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
//		String mac = request.getParameter("mac");// mac地址
//		CommonUtils.validateEmpty(mac);
//		System.out.println(mac);
//
//		String id=request.getParameter("id");//用户id
//		CommonUtils.validateEmpty(id);
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


    protected void getAppVersion(HttpServletRequest request, HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
//		String id = request.getParameter("id");//用户id
//		CommonUtils.validateEmpty(id);

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



//	protected void queryLock(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
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

//	protected void sendQueryLockCmd(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
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

//	protected void updateLock(HttpServletRequest request,HttpServletResponse response) throws NullParameterException {
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

    protected void factoryGprsOpenLock(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
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

    protected void getBluetoothInfo(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
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



        String bluetoothMac=StringCommonUtil.getRegexStr(mac,":");

        String newKey =StringCommonUtil.getRegexStr(key,",");

        String newPass = StringCommonUtil.getRegexStr(psw,",");

        String bicycleNum = getBicycleNum(barcode);

        //判断是simno是否存在，如果存在提示
        BikeInfo bikeInfoBySimNo = bikeService.getBikeInfoBySimNo(simNo);
        if (null!= bikeInfoBySimNo){
           final boolean isStartsWithStr = StringCommonUtil.startsWithStr(getBicycleNum(barcode), WL_START_NO);
           //判断是否是物联的
            if (isStartsWithStr) {
              final int cityNo=bikeInfoBySimNo.getCityNo();
              final int count = bikeService.getBikecUnbundlingNum(simNo);
                //判断是否已经解绑了 如果CityNo等于0并且count==0
                if (cityNo==0 && count!=0){
                    //更新车辆消息和记录以前的消息和更新过的信息


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
                }
            }
        }

        LockFactoryEmployeeInfo info =lockFactoryEmployeeInfoService.getLockFactoryEmployeeInfoById(id);
        if (info!=null){
            LockFactoryInfo lockFactoryInfo = lockFactoryInfoService.getLockFactoryInfoByLockFactoryNo(factoryNo);
            if (lockFactoryInfo!=null) {
                String res = CommonUtils.MD5(simNo+mac+lockFactoryInfo.getLockFactoryNo());
                if (res.equalsIgnoreCase(md5reply)) {
                    if (judgeBarcodeURL(BAR_CODE)) {
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

    /**
     * 根据bicycleNum获取厂家名称
     * @param response
     */
    private  void  getBickSupplierName(HttpServletResponse response){
        try {
            String bicycleNum = getReqParam("bicycleNum");
            BikeOutStorgeInfo storgeInfo = bikeOutStorgeService.getBikeOutInfoByBicycleNum(bicycleNum);
            if (storgeInfo !=null ){
                Map<String,Object> resultMap=getReponseMap(SupplierEnum.GETBICKSUPPLIERNAME_OK,storgeInfo.getSupplierName());
                setResult(response,resultMap);
            } else {
                Map<String,Object> resultMap=getReponseMap(SupplierEnum.GETBICKSUPPLIERNAME_UNFOUND,storgeInfo.getSupplierName());
                setResult(response,resultMap);
            }
        }catch (Exception e){
              LOGGER.error(e);
             setResultWhenException(response,e.getMessage());
        }

    }


    private void uploadSupplierInfo(HttpServletRequest request,HttpServletResponse response) throws NullParameterException{
        Map<String,Object> resultMap=new HashMap<>();
        String supplierName = request.getParameter("supplierName");
        CommonUtils.validateEmpty(supplierName);

        String supplierPassword = request.getParameter("supplierPassword");
        CommonUtils.validateEmpty(supplierPassword);
        try{
            SupplierInfo supplierInfo = supplierService.getSupplierInfoByNameAndPassword(supplierName,supplierPassword);
            List<String> allsupplieName = supplierService.getAllsupplieName();
            if(supplierInfo != null){
                supplierInfo.setLoginState(2);
                supplierInfo.setLoginTime(new Date());
                supplierService.updateSupplierInfo(supplierInfo);
                resultMap.put("code", Constant.Success);
                resultMap.put("message", "获取供应商信息成功");
                resultMap.put("supplierId", supplierInfo.getId());
                if (CHANGE_LOCK_SUPPLIER_NAME.equals(supplierName)){
                    resultMap.put("allsupplieName",allsupplieName);
                }
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


    /**
     * 车厂换锁绑定
     */
    private void changeBicycleLock(HttpServletResponse response){
        try {
            String bicycleNum = getReqParam("bicycleNum");
            String bicycleModel = getReqParam("bicycleModel");
            String supplierName = getReqParam("supplierName");
            BikeOutStorgeInfo changeInfo = new BikeOutStorgeInfo();
            changeInfo.setBicycleNo(Integer.parseInt(bicycleNum));
            changeInfo.setBicycleModel(bicycleModel);
            changeInfo.setAddTime(new Date());
            changeInfo.setDelFlag(0);
            if (StringCommonUtil.startsWithStr(bicycleNum,"5")) {
                changeInfo.setBicycleType(5);
            } else {
                changeInfo.setBicycleType(1);
            }
            changeInfo.setSupplierName(supplierName);
            changeInfo.setAddType(1);
            bikeOutStorgeService.addChangeLockInfo(changeInfo);
            Map<String, Object> reponseMap = getReponseMap(SupplierEnum.CHANGELOCK_OK);
            setResult(response, reponseMap);
        } catch (NullParameterException e) {
            LOGGER.error(e);
            setResultWhenException(response,e.getMessage());
        }

    }


    private void uploadBicycleNum(HttpServletRequest request,HttpServletResponse response) throws NullParameterException {
        Map<String, Object> resultMap = new HashMap<>();
        String supplierId = request.getParameter("supplierId");
        CommonUtils.validateEmpty(supplierId);
        String bicycleNum = request.getParameter("bicycleNum");
        CommonUtils.validateEmpty(bicycleNum);
        String bicycleModel = request.getParameter("bicycleModel");
        CommonUtils.validateEmpty(bicycleModel);
        try {
            BikeOutStorgeInfo bikeOutStorgeInfo = bikeOutStorgeService.getBikeOutInfoByBicycleNum(bicycleNum);
            if (bikeOutStorgeInfo != null) {
                resultMap.put("code", Constant.Error_UploadBicycleNum);
                resultMap.put("message", "出厂设置失败:车辆号已存在");
            } else {
                BikeInfo bikeInfo = iBikeService.getBikeInfoByBicycleNum(bicycleNum);
                if (bikeInfo != null) {
                    LockGPSRealData lockGPSRealData = lockGPSRealDataService.getLockGpsRealDataInfo(bikeInfo.getSimNo());
                    if (lockGPSRealData != null) {
                        int resAddEntity = BaiduYingYanUtilTest.addBikeEntity(bicycleNum);
                        if (resAddEntity == Constant.Success) {
                            LOGGER.info("增加车辆实体成功");
                        } else {
                            LOGGER.info("增加车辆实体失败");
                        }
                        FactoryQuantityInfo fqInfo = factoryQuantityService.getInfoBySupplierId(supplierId);
                        if (fqInfo != null) {
                            if (bicycleNum.substring(0, 1).equals("5")) {
                                int bicycleQuantity = bikeOutStorgeService.getQuantity(supplierId);
                                if (-1 != bicycleQuantity) {
                                    fqInfo.setBicycleQuantity(bicycleQuantity);
                                }
                            } else {
                                int electricBicycleQuantity = bikeOutStorgeService.getEQuantity(supplierId);
                                if (-1 != electricBicycleQuantity) {
                                    fqInfo.setElectricBicycleQuantity(electricBicycleQuantity);
                                }
                            }
                            factoryQuantityService.updateInfo(fqInfo);
                        } else {
                            FactoryQuantityInfo newfqInfo = new FactoryQuantityInfo();
                            newfqInfo.setSupplierId(supplierId);
                            newfqInfo.setAddTime(new Date());
                            if (bicycleNum.substring(0, 1).equals("5")) {
                                newfqInfo.setBicycleQuantity(1);
                            } else {
                                newfqInfo.setElectricBicycleQuantity(1);
                            }
                            factoryQuantityService.addInfo(newfqInfo);
                        }
                        SupplierInfo supplierInfo = supplierService.getSupplierBySupplierId(supplierId);
                        if (supplierInfo != null) {
                            BikeOutStorgeInfo newInfo = new BikeOutStorgeInfo();
                            newInfo.setBicycleNo(Integer.parseInt(bicycleNum));
                            newInfo.setBicycleModel(bicycleModel);
                            newInfo.setAddTime(new Date());
                            newInfo.setDelFlag(0);
                            if (bicycleNum.substring(0, 1).equals("5")) {
                                newInfo.setBicycleType(5);
                            } else {
                                newInfo.setBicycleType(1);
                            }
                            newInfo.setSupplierName(supplierInfo.getSupplierName());
                            bikeOutStorgeService.addInfo(newInfo);
                            resultMap.put("code", Constant.Success);
                            resultMap.put("message", "出厂设置成功");
                        } else {
                            resultMap.put("code", Constant.Error_UploadBicycleNum);
                            resultMap.put("message", "出厂设置失败:厂家信息获取失败");
                        }
                    } else {
                        int resAddEntity = BaiduYingYanUtilTest.addBikeEntity(bicycleNum);
                        if (resAddEntity == Constant.Success) {
                            LOGGER.info("增加车辆实体成功");
                        } else {
                            LOGGER.info("增加车辆实体失败");
                        }
                        resultMap.put("code", Constant.Error_UploadBicycleNum);
                        resultMap.put("message", "出厂设置失败:车辆未联网");
                    }
                } else {
                    resultMap.put("code", Constant.Error_UploadBicycleNum);
                    resultMap.put("message", "出厂设置失败:车辆不存在。");
                }
            }
        } catch (Exception e) {
            LOGGER.info(e);
            resultMap.put("code", 101);
            resultMap.put("message", "出错啦！");
        }
        setResult(response, resultMap);
    }


        /**
         * 获取请求参数
         * @param name
         * @return
         */
    private String getReqParam(String name) throws NullParameterException {
        String parameter = this.getRequest().getParameter(name);
        CommonUtils.validateEmpty(parameter);
          return parameter ;
    }


    /**
     * 获取HttpServletRequest
     * @return
     */
    private  HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }


    /**
     * 获取请求参数并解密
     * @param request
     * @return
     */
    private  Map<String,String> getRequestParam(HttpServletRequest request) {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder reportBuilder = new StringBuilder();
            String tempStr = "";
            while ((tempStr = reader.readLine()) != null) {
                reportBuilder.append(tempStr);
            }
            ObjectMapper json = new ObjectMapper();
            Map<String, String> requestMap = json.readValue(TripleDES.decode(reportBuilder.toString()), Map.class);
            //验证参数是否为空
            validateEmpty(requestMap);
            return  requestMap;
         } catch (IOException e) {
            LOGGER.error(e.getMessage());
         } catch (Exception e) {
            LOGGER.error(e.getMessage());
          }
        return null;
      }

    /**
     *  判断请求路径，调用对应的接口模块
     * @param serviceNameEnum
     * @return
     */
     private  boolean isRequestUrl(ServiceNameEnum serviceNameEnum){
        String[] split = getRequest().getRequestURI().split("/");
        int len=split.length;
        for (int i = 0; i < len; i++) {
            if (split[i].equals(serviceNameEnum.getName())){
                return  true;
            }
        }
        return  false;
    }

    /**
     * 验证
     * @param map
     */
    private  void validateEmpty(Map<String,String> map) throws NullParameterException {
        for (Map.Entry<String,String> entry:map.entrySet()){
                CommonUtils.validateEmpty(entry.getValue());
        }
    }

    /**
     * 判断BarcodeURL是否合法
     * @param barcodeURL
     */
    private  boolean  judgeBarcodeURL(String barcodeURL){
           return barcodeURL.indexOf(BAR_CODE)!=-1;
    }


    /**
     * 根据barcodeURL截取icycleNum
     * @param barcodeURL
     * @return
     */
    private  String getBicycleNum(String barcodeURL){
          if (judgeBarcodeURL(barcodeURL)){
                int index = barcodeURL.indexOf("b=");
                return  barcodeURL.substring(index + 2);
          }
          return  "";
    }

    /**
     * 返回参数Map
     * @param enumService
     * @param obj
     * @return
     */
    public  Map<String,Object> getReponseMap(EnumService enumService,Object obj){
            Map<String,Object> map=new HashMap<>();
            map.put("code",enumService.getCode());
            map.put("message",enumService.getMessage());
            map.put("data",obj);
         return  map;
    }

    /**
     * 返回参数Map
     * @param enumService
     * @return
     */
    public  Map<String,Object> getReponseMap(EnumService enumService){
        Map<String,Object> map=new HashMap<>();
        map.put("code",enumService.getCode());
        map.put("message",enumService.getMessage());
        return  map;
    }
}