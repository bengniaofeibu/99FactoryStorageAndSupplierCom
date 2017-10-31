package com.qiyuan.FatoryAndSupplierServer;

import com.daoshun.exception.NullParameterException;
import com.qiyuan.Base.BaseController;
import com.qiyuan.baiduUtil.BaiduYingYanUtilTest;
import com.qiyuan.common.CommonUtils;
import com.qiyuan.common.Constant;
import com.qiyuan.entity.Result;
import com.qiyuan.enums.FactoryEnum;
import com.qiyuan.pojo.*;
import com.qiyuan.service.*;
import com.qiyuan.utils.StringCommonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierServerController extends BaseController{

    private   final Log LOGGER = LogFactory.getLog(SupplierServerController.class);

    private static  final String  CHANGE_LOCK_SUPPLIER_NAME="锁厂换锁绑定";

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

    protected Result supplierCallingService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Result result=null;
        try {
            String action = getAction(request, response);

            //车厂的接口
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
        } catch (Exception e) {
            e.printStackTrace();
            setResultWhenException(response, e.getMessage());
        }
        return  null;
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
            Map<String, Object> reponseMap = getReponseMap(FactoryEnum.CHANGE_LOCK_OK);
            setResult(response, reponseMap);
        } catch (NullParameterException e) {
            LOGGER.error(e);
            setResultWhenException(response,e.getMessage());
        }

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
                Map<String,Object> resultMap=getReponseMap(FactoryEnum.GET_BICK_SUPPLIER_NAME_OK,storgeInfo.getSupplierName());
                setResult(response,resultMap);
            } else {
                Map<String,Object> resultMap=getReponseMap(FactoryEnum.GET_BICK_SUPPLIER_NAME_UNFOUND,storgeInfo.getSupplierName());
                setResult(response,resultMap);
            }
        }catch (Exception e){
            LOGGER.error(e);
            setResultWhenException(response,e.getMessage());
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

}
