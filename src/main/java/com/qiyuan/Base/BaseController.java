package com.qiyuan.Base;

import com.daoshun.exception.NullParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.qiyuan.common.*;
import com.qiyuan.enums.EnumService;
import com.qiyuan.enums.FactoryEnum;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * @author : tonghengzhen
 * Date: 2017/10/20
 * Time: 14:01
 */

public class BaseController extends HttpServlet {

    private   final Log LOGGER = LogFactory.getLog(getClass());
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Map<String, Object> paramMap = null;


    private static  final String  CHANGE_LOCK_SUPPLIER_NAME="锁厂换锁绑定";



    /**
     * 判断BarcodeURL是否合法
     * @param barcodeURL
     */
    protected   boolean  judgeBarcodeURL(String barcodeURL,String barCode){
        return barcodeURL.indexOf(barCode)!=-1;
    }


    /**
     * 根据barcodeURL截取icycleNum
     * @param barcodeURL
     * @return
     */
    protected  String getBicycleNum(HttpServletResponse response,String barcodeURL,String barCode,EnumService enumService){
        if (judgeBarcodeURL(barcodeURL,barCode)){
            int index = barcodeURL.indexOf("b=");
            return  barcodeURL.substring(index + 2);
        }else {
            Map<String, Object> reponseMap = getReponseMap(enumService);
            setResult(response,reponseMap);
        }
        return  "";
    }


    /**
     * Servlet 返回结果
     * @param resultMap
     * @param response
     */
    protected void setResult(HttpServletResponse response, Map<String, Object> resultMap) {
        response.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            // json格式转换
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping().create();
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


    protected void setResultWhenException(HttpServletResponse response, String errmsg) {
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
    protected String getAction(HttpServletRequest request, HttpServletResponse response) {
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



        /**
         * 获取请求参数
         * @param name
         * @return
         */
    protected String getReqParam(String name) throws NullParameterException {
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
    protected   Map<String,String> getRequestParam(HttpServletRequest request) {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder reportBuilder = new StringBuilder();
            String tempStr = "";
            while ((tempStr = reader.readLine()) != null) {
                reportBuilder.append(tempStr);
            }
            ObjectMapper json = new ObjectMapper();
            Map<String, String> requestMap = json.readValue(TripleDES.decode(reportBuilder.toString()), Map.class);
//            Map<String, String> requestMap = json.readValue(reportBuilder.toString(), Map.class);
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
     * 获取请求参数并解密(3DES)
     * @param request
     * @return
     */
    protected   Map<String,String> getRequestParam3DES(HttpServletRequest request) {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder reportBuilder = new StringBuilder();
            String tempStr = "";
            while ((tempStr = reader.readLine()) != null) {
                reportBuilder.append(tempStr);
            }
            ObjectMapper json = new ObjectMapper();
            Map<String, String> requestMap = json.readValue(SecretUtils.Decrypt3DES(reportBuilder.toString()), Map.class);
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
     * 验证
     * @param map
     */
    private  void validateEmpty(Map<String,String> map) throws NullParameterException {
        for (Map.Entry<String,String> entry:map.entrySet()){
                CommonUtils.validateEmpty(entry.getValue());
        }
    }



     //返回result结果
     protected void reponseResult(HttpServletResponse response,EnumService enumService){
         Map<String, Object> reponseMap = getReponseMap(enumService);
         setResult(response,reponseMap);
     }

     //返回result结果
     protected void reponseResult(HttpServletResponse response,EnumService enumService,Object obj){
        Map<String, Object> reponseMap = getReponseMap(enumService,obj);
        setResult(response,reponseMap);
    }

    //返回result结果
    protected void reponseResult(HttpServletResponse response,EnumService enumService,Object obj,String key,Object value){
        Map<String, Object> reponseMap = getReponseMap(enumService,obj,key,value);
        setResult(response,reponseMap);
    }


    /**
     * 返回参数Map
     * @param enumService
     * @param obj
     * @return
     */
    protected   Map<String,Object> getReponseMap(EnumService enumService,Object obj){
            Map<String,Object> map=new HashMap<>();
            map.put("result",enumService.getCode());
            map.put("message",enumService.getMessage());
            map.put("data",obj);
         return  map;
    }

    /**
     * 返回参数Map
     * @param enumService
     * @param obj
     * @return
     */
    protected   Map<String,Object> getReponseMap(EnumService enumService,Object obj,String key,Object value){
        Map<String,Object> map=new HashMap<>();
        map.put("result",enumService.getCode());
        map.put("message",enumService.getMessage());
        map.put("data",obj);
        map.put(key,value);
        return  map;
    }

    /**
     * 返回参数Map
     * @param enumService
     * @return
     */
    protected   Map<String,Object> getReponseMap(EnumService enumService){
        Map<String,Object> map=new HashMap<>();
        map.put("result",enumService.getCode());
        map.put("message",enumService.getMessage());
        return  map;
    }
}