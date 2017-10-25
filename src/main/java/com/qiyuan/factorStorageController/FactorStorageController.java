package com.qiyuan.factorStorageController;

import com.daoshun.exception.NullParameterException;
import com.qiyuan.Base.BaseController;
import com.qiyuan.entity.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * @author : tonghengzhen
 * Date: 2017/10/20
 * Time: 13:58
 */
@RestController
@RequestMapping(value = "/FactoryStoragePro")
public class FactorStorageController  extends BaseController {

     private  static Log LOGGER= LogFactory.getLog(FactorStorageController.class);


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
    @PostMapping(value = "/factory")
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
    //aaaa
}
