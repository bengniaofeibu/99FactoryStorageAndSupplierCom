package com.qiyuan.common;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostUtil {
	public static void post() throws IOException{

//		  URL url = new URL("http://101.64.232.240:8089/alipayFuwu/example.do");
		  URL url = new URL("http://60.12.107.149:80/alipayFuwu/example.do");
		  HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

		  httpURLConnection.setDoInput(true);
		  httpURLConnection.setDoOutput(true);    // 设置该连接是可以输出的
		  httpURLConnection.setRequestMethod("POST"); // 设置请求方式
		  httpURLConnection.setRequestProperty("contentType", "GBK");

		  PrintWriter pw = new PrintWriter(new BufferedOutputStream(httpURLConnection.getOutputStream()));
		  pw.write("charset=GBK");          // 向连接中输出数据（相当于发送数据给服务器）
		  pw.write("&biz_content=<XML><Text><Content><![CDATA[push]]></Content></Text><FromAlipayUserId><![CDATA[2088402125991030]]></FromAlipayUserId><AppId><![CDATA[2016022201155812]]></AppId><MsgType><![CDATA[text]]></MsgType><CreateTime><![CDATA[1457426646396]]></CreateTime><FromUserId><![CDATA[20880037081313300931049260316403]]></FromUserId><MsgId><![CDATA[160308464406000001]]></MsgId><UserInfo><![CDATA[{\"logon_id\":\"516***@qq.com\",\"user_name\":\"*克楠\"}]]></UserInfo><hahatype><![CDATA[push]]></hahatype></XML>");
		  pw.write("&service=alipay.mobile.public.message.notify");
		  
		  pw.flush();
		  pw.close();

		  BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"GBK"));
		  String line = null;
		  StringBuilder sb = new StringBuilder();
		  while ((line = br.readLine()) != null) {  // 读取数据
		    sb.append(line + "\n");
		  }

		  System.out.println(sb.toString());
		}
}
