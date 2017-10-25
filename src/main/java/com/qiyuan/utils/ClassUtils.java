 package com.qiyuan.utils;
 
 import org.apache.log4j.Logger;

 public class ClassUtils
 {
   public static Logger logger = Logger.getLogger(ClassUtils.class);
 
   public static Object getBean(String className)
   {
     Class clazz = null;
     try
     {
       clazz = Class.forName(className);
     }
     catch (Exception ex)
     {
       logger.info("�Ҳ���ָ������");
     }
     if (clazz != null)
     {
       try
       {
         return clazz.newInstance();
       }
       catch (Exception ex) {
         logger.info("�Ҳ���ָ������");
       }
     }
     return null;
   }
 }

