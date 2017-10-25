package com.qiyuan.common;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.qiyuan.baiduUtil.BaiduYingYanUtilTest;

public class Test {

//	public static String creatUUID() {  
//        return UUID.randomUUID().toString().replace("-", "");  
//	}
	
	public static void main(String[] args) throws Exception {

		String str="{\"realName\":\"%E7%8E%8B%E6%B5%B7%E6%9B%99\",\"passWord\":\"4297f44b13955235245b2497399d7a93\",\"adminCid\":\"bcc33d0c8e503e539a9f37a331a80915\"}";
		String res= TripleDES.encode(str);
		System.out.println(res);


//		int res = BaiduYingYanUtilTest.addBikeEntity("500000928");
//		System.out.println(res);
//		String str = "C8FD1902F46D";
//
//		String res = CommonUtils.PackingFactoryInfo(str, "mac");
//		System.out.println(res);



		
//		String str = "http://www.99bicycle.com/download/?b=500000937";
//		if(str.indexOf("http://www.99bicycle.com") != -1){
//			System.out.println("有效二维码");
//			int index = str.indexOf("b=");
//			System.out.println("index:" + index);
//			String res = str.substring(index + 2);
//			System.out.println(res);
//		}
		
		
//		String string = "500000001";
//		String string2 = string.substring(0, 1);
//		System.out.println(string2);
		
//		Point2D.Double point = new Point2D.Double(120.077443,30.863534);
//		List<Point2D.Double> pts = new ArrayList<Point2D.Double>();
//		pts.add(new Point2D.Double(120.075019,30.929749));  
//		pts.add(new Point2D.Double(120.281989,30.929749));  
//		pts.add(new Point2D.Double(120.281989,30.80824)); 
//		pts.add(new Point2D.Double(120.075019,30.80824));
//		
//		if(CommonUtils.IsPtInPoly(point, pts)){  
//		      System.out.println("点在多边形内");
//		
//		 }else{  
//		      System.out.println("点在多边形外");			      
//		 } 
//		StringBuilder sBuilder=new StringBuilder();
//		Random random1 = new Random();
//		for (int i = 0; i < 6; i++) {
//		for (int j = 0; j < 2; j++) {
//			sBuilder.append(random1.nextInt(10));
//		}
//		sBuilder.append(",");
//		}
//		sBuilder.deleteCharAt(sBuilder.length()-1);
//		System.out.println(sBuilder.toString());
//		String sb=CommonUtils.PackingFactoryInfo(sBuilder.toString(),"pass");
//
//		System.out.println(sb);
	}

}
