package com.qiyuan.Controller;

import com.qiyuan.FatoryAndSupplierServer.FactoryServerController;
import com.qiyuan.annotation.SystemServerLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class FactoryStorageProController extends FactoryServerController {

    private   final Log LOGGER = LogFactory.getLog(FactoryStorageProController.class);


    @Autowired
    private RedisTemplate redisTemplate;

    @SystemServerLog(funcionExplain = "控制层")
    @RequestMapping(value = "/factory",method = {RequestMethod.POST, RequestMethod.GET})
    public Map<String, Object> factory(HttpServletRequest request, HttpServletResponse response){
        try {
            return factorCallingService(request, response);
        } catch (ServletException e) {
            LOGGER.error("ERROR: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.error("ERROR: " + e.getMessage());
        }
        return  null;
    }


    @RequestMapping(value = "/test/{key}")
    public  Object getMessage(@PathVariable(value = "key") String key){
        return getValue(key);
    }

    public Object getValue(Object value){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(value);
    }

}
