package com.qiyuan.factorStorageController;

import com.daoshun.exception.NullParameterException;
import com.qiyuan.Base.BaseController;
import com.qiyuan.entity.Result;
import com.qiyuan.pojo.BikeInfo;
import com.qiyuan.service.IBikeService;
import com.qiyuan.service.ISupplierService;
import com.qiyuan.utils.StringCommonUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omg.CORBA.ARG_IN;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * @author : tonghengzhen
 * Date: 2017/10/20
 * Time: 13:58
 */
@RestController
public class FactorStorageController  extends BaseController {

     private  static Log LOGGER= LogFactory.getLog(FactorStorageController.class);

    @Resource
    private IBikeService bikeService;

    @Resource
    private ISupplierService iSupplierService;

    @PostMapping(value = "/supplier")
    public void supplier(HttpServletRequest request, HttpServletResponse response){
        try {
            callingService(request, response);
        } catch (ServletException e) {
            LOGGER.error("ERROR: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.error("ERROR: " + e.getMessage());
        }
    }
    @PostMapping(value = "/FactoryStoragePro/factory")
    public Result factory(HttpServletRequest request, HttpServletResponse response){
        Result result = null;
        try {
            result = callingService(request, response);
        } catch (ServletException e) {
            LOGGER.error("ERROR: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.error("ERROR: " + e.getMessage());
        }
        return  result;
    }

    @GetMapping(value = "/test1111")
    private List<String> test(String simNo){
        return iSupplierService.getAllsupplieName();
    }

    public static void main(String[] args) {
        String aaaa="500010918";
        boolean b = StringCommonUtil.startsWithStr(aaaa, "5");
        System.out.println(b);
    }
}
