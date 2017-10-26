package com.qiyuan.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class StringCommonUtil {


    /**
     * 检查字符串(val1)中是否存在(val2)字符
     * @param val1
     * @param val2
     * @return
     */
    public static boolean contains(String val1,String val2){
         return StringUtils.contains(val1,val2);
    }

    /**
     * 根据分隔符拼接字符串
     * @param str
     * @param regex
     * @return
     */
    public static String getRegexStr(String str,String regex){
        String[] array = str.split(regex);
        StringBuilder sb=new StringBuilder();
        for(int i=0;i < array.length;i++){
            sb.append(array[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.println(StringCommonUtil.contains("500066541","59"));
    }
}
