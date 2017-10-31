package com.qiyuan.Controller;

import com.qiyuan.FatoryAndSupplierServer.SupplierServerController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class SupplierComProController extends SupplierServerController {

    private  static Log LOGGER= LogFactory.getLog(SupplierComProController.class);


    @PostMapping(value = "/supplier")
    public void supplier(HttpServletRequest request, HttpServletResponse response){
        try {
            supplierCallingService(request, response);
        } catch (ServletException e) {
            LOGGER.error("ERROR: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.error("ERROR: " + e.getMessage());
        }
    }

}
