package com.qiyuan.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("httpUtil")
public class HttpUtil {
	private static Log logger = LogFactory.getLog(HttpUtil.class);


	public static void main(String[] args) throws ClientProtocolException,
			IOException {

//		Map<String, String> params = new HashMap<String, String>();
//		params.put("action", "GetLlianTongLockStatus");
//		params.put("iccid", "89860617030074266530");
//		String str=HttpUtil.get("http://localhost:8080/OperateBicyclePro/factory?action=GetLlianTongLockStatus&iccid=89860617030074266530");
//		System.out.println(str);
		deduceMainApplicationClass();

	}

	private static Class<?> deduceMainApplicationClass() {
		try {
			StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();

			for (StackTraceElement stackTraceElement : stackTrace) {
				System.out.println(stackTraceElement.getMethodName());
				if ("main".equals(stackTraceElement.getMethodName())) {
					System.out.println(stackTraceElement.getClassName());
					return Class.forName(stackTraceElement.getClassName());
				}
			}
		}
		catch (ClassNotFoundException ex) {
			// Swallow and continue
		}
		return null;
	}

	public static String post(String url, Map<String, String> params)
			throws ClientProtocolException, IOException {
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type","application/json");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		HttpResponse response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);
	}
	
	public static String get(String url) throws ClientProtocolException, IOException {
		HttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Content-Type ","multipart/form-data");
		HttpResponse response = httpclient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);
	}
}
