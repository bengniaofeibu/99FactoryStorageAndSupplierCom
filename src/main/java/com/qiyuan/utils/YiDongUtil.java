package com.qiyuan.utils;

import com.qiyuan.baiduUtil.HttpRequestProxy;
import com.qiyuan.common.TripleDES;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class YiDongUtil {


    private static  final SimpleDateFormat format=new SimpleDateFormat("YYYYMMDDHHMMSS");

    private static  final  String HOST="183.230.96.66";

    private static  final  int PORT=8087;

    private static  final  String VERSION="v2";

    private  static String SERVICE_REQUEST_URL="http://"+HOST+":"+PORT+"/"+VERSION+"/";

    /** 移动提供 appid **/
    private  static  final  String  APP_ID="";

    /** 移动提供 ebid**/
    private  static  final  String EB_ID="";

    /** 移动提供 password**/
    private  static  final  String PASS_WORD="";

    private static  final  int FLAG=8;

    private static String CODING = "UTF-8";


    /**
     * 生成transId
     * @return
     */
    public static String getTransId(){
        StringBuilder builder=new StringBuilder(APP_ID);
      return  builder.append(format.format(new Date())).append(createRandomNum(FLAG)).toString();
    }


    /**
     * 生成getToken
     * @return
     */
    public  static String getToken(){
        StringBuilder builder=new StringBuilder(APP_ID);
        return TripleDES.getSHA256Str(builder.append(PASS_WORD).append(getTransId()).toString());
    }


    /**
     *
     * 生成多位的随机数
     * @return
     */
    private static String createRandomNum(int order){
        Random random=new Random();
        int num;
        StringBuilder builder=new StringBuilder();
        for (int i=0;i<order;i++){
            num = random.nextInt(10);
            builder.append(num);
        }
        return  builder.toString();
    }

    /**
     * 发送接口请求
     * @return
     */
    public static String  sendServiceReq(String serviceName, Map<String,Object> param){
          StringBuilder builder=new StringBuilder(SERVICE_REQUEST_URL);
          String serviceUrl=builder.append(serviceName).toString();
          Map<String, Object> publicParam = createPublicParam();
          for (Map.Entry<String,Object> entry:param.entrySet()){
            publicParam.put(entry.getKey(),entry.getValue());
          }
          return HttpRequestProxy.doPost(serviceUrl,publicParam,CODING);
    }


    /**
     * 生成公共请求参数
     * @return
     */
    private  static  Map<String,Object> createPublicParam(){
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("appid",APP_ID);
        paramMap.put("transid",getTransId());
        paramMap.put("ebid",EB_ID);
        paramMap.put("token",getToken());
        return paramMap;
    }

    public static void main(String[] args) {
        Map<String,Object> map=new HashMap<>();
        System.out.println(sendServiceReq("",map));
    }
}
