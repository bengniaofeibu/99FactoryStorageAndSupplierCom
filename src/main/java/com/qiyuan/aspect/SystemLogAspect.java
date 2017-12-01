package com.qiyuan.aspect;

import com.qiyuan.annotation.SystemServerLog;
import com.qiyuan.utils.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@Component
@Aspect
public class SystemLogAspect {


    private static final SimpleDateFormat DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final Logger LOGGER= LoggerFactory.getLogger(SystemLogAspect.class);

    @Autowired
    private HttpServletRequest request;

    private ThreadLocal<Date> threadLocal=new NamedThreadLocal<>("threadLocal time");


    @Pointcut("@annotation(com.qiyuan.annotation.SystemServerLog)")
    public void serverAspect(){}


    @Before("serverAspect()")
    public void before(JoinPoint joinPoint){
        Date date=new Date();
        threadLocal.set(date);
        if (LOGGER.isDebugEnabled()){
            try {
                LOGGER.debug("开始时间 {}  action {}",DATE_FORMAT.format(date),getRequestParam(request.getParameterMap()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @After("serverAspect()")
    public void doAfter(JoinPoint joinPoint){
        try {
            String methodName = joinPoint.getSignature().getName();
            //开始之前
            long startTime=threadLocal.get().getTime();
            long endTime=System.currentTimeMillis();
            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("{}--{}方法执行时间 {}ms",getServiceMthodDescription(joinPoint),methodName,(endTime-startTime));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterReturning(returning = "object", pointcut = "serverAspect()")
    public void doAfterReturning(Object object) {
        LOGGER.info("response={}", JSON.toJSONString(object));
    }


    @AfterThrowing(value = "serverAspect()",throwing = "exception")
    public  void doAfterThrowing(Throwable exception){
        if (LOGGER.isErrorEnabled()){
           LOGGER.error(" ERROR {}" ,exception.getMessage());
        }
    }


    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public  static String getServiceMthodDescription(JoinPoint joinPoint)
            throws Exception {
       MethodSignature methodSignature= (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SystemServerLog annotation = method.getAnnotation(SystemServerLog.class);
        return annotation.funcionExplain();
    }


    /**
     * 获取请求参数
     * @param params
     */
    public String getRequestParam(Map<String,String[]> params ){
         StringBuilder builder=new StringBuilder();
        for (Map.Entry<String,String[]> entry:params.entrySet()){
              builder.append(entry.getValue()[0]);
        }
        return builder.toString();
    }
}


