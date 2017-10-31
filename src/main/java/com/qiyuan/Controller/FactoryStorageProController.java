package com.qiyuan.Controller;

import com.qiyuan.FatoryAndSupplierServer.FactoryServerController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class FactoryStorageProController extends FactoryServerController {

    private   final Log LOGGER = LogFactory.getLog(FactoryStorageProController.class);

    @PostMapping(value = "/factory")
    public void factory(HttpServletRequest request, HttpServletResponse response){
        try {
            factorCallingService(request, response);
        } catch (ServletException e) {
            LOGGER.error("ERROR: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.error("ERROR: " + e.getMessage());
        }
    }
}
