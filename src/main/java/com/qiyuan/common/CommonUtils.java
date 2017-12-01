/**
 * 
 */
package com.qiyuan.common;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.SortedMap;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
//import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import com.daoshun.exception.NullParameterException;
import com.qiyuan.baiduUtil.BaiduYingYanUtil;

@Component
public class CommonUtils {


	/**
	 * 读取配置文件
	 */
	public static Properties properties = new Properties();
	static {
		try {
			String path = "config.properties";
			InputStream inStream = CommonUtils.class.getClassLoader().getResourceAsStream(path);
			if (inStream == null) {
				inStream = CommonUtils.class.getClassLoader().getResourceAsStream("/" + path);
			}
			properties.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 文件访问路径
	public static String getFileRootUrl() {
		return properties.getProperty("fileRootUrl");
	}

	public static String getLocationPath() {
		return properties.getProperty("uploadFilePath");
	}

	public static String getWebRootUrl() {
		return properties.getProperty("webRootUrl");
	}

	/**
	 * 查路径是否存在，不存在则创建路径
	 * 
	 * @param path
	 */
	public static void checkPath(String path) {
		String[] paths = null;
		if (path.contains("/")) {
			paths = path.split(File.separator);
		} else {
			paths = path.split(File.separator + File.separator);
		}
		if (paths == null || paths.length == 0) {
			return;
		}
		String pathdir = "";
		for (String string : paths) {
			pathdir = pathdir + string + File.separator;
			File file = new File(pathdir);
			if (!file.exists()) {
				file.mkdir();
			}
		}
	}

	/**
	 * 判断参数是否为空
	 * 
	 */
	public static void validateEmpty(String value) throws NullParameterException {
			if (value == null || value.length() == 0) {
				throw new NullParameterException();
			}
		
	}

	/**
	 * 判断String是否为空
	 * 
	 */
	public static boolean isEmptyString(String value) {
		if (value == null || value.length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * String -> int
	 * 
	 * @param String
	 * @return int
	 */
	public static int parseInt(String string, int def) {
		if (isEmptyString(string))
			return def;
		int num = def;
		try {
			num = Integer.parseInt(string);
		} catch (Exception e) {
			num = def;
		}
		return num;
	}

	/**
	 * String -> long
	 * 
	 * @param String
	 * @return long
	 */
	public static long parseLong(String string, long def) {
		if (isEmptyString(string))
			return def;
		long num;
		try {
			num = Long.parseLong(string);
		} catch (Exception e) {
			num = def;
		}
		return num;
	}

	/**
	 * String -> double
	 * 
	 * @param String
	 * @return long
	 */
	public static double parseDouble(String string, double def) {
		if (isEmptyString(string))
			return def;
		double num;
		try {
			num = Double.parseDouble(string);
		} catch (Exception e) {
			num = def;
		}
		return num;
	}

	/**
	 * String -> float
	 * 
	 * @param String
	 * @return float
	 */
	public static float parseFloat(String string, float def) {
		if (isEmptyString(string))
			return def;
		float num;
		try {
			num = Float.parseFloat(string);
		} catch (Exception e) {
			num = def;
		}
		return num;
	}

	public static String getCurrentTimeFormat(String format) {
		return getTimeFormat(new Date(), format);
	}

	/**
	 * @param date
	 * @param string
	 * @return
	 */
	public static String getTimeFormat(Date date, String string) {
		SimpleDateFormat sdFormat;
		if (isEmptyString(string)) {
			sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			sdFormat = new SimpleDateFormat(string);
		}
		try {
			return sdFormat.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	public static int hasNext(List<?> a) {
		if (a != null && a.size() > 0) {
			return 1;
		}
		return 0;
	}

	/**
	 * MD5加密
	 * 
	 * @param 要加密的String
	 * @return 加密后String
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str).toUpperCase();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 格式化时间- String转换Date "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param 格式化的时间
	 * @return 格式化后的时间
	 */
	public static Date getDateFormat(String date, String format) {
		if (isEmptyString(date))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param fileName
	 * @param Title
	 * @param listContent
	 * @return
	 */
	public static String exportExcel(String fileName, String[] Title, List<?> listContent) {
		// 以下开始输出到EXCEL
		String newfilename = "";
		try {
			// 定义输出流，以便打开保存对话框______________________begin
			newfilename += CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmmssSSS") + "_" + (int) (Math.random() * 100) + ".xls";
			String filename = CommonUtils.properties.get("uploadFilePath") + newfilename;
			File file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
			}
			/** **********创建工作************ */
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			/** **********创建工作************ */
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			/** **********设置纵横打印（默认为纵打）打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);
			/** ************设置单元格字体************** */
			// WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
			/** ************以下设置三种单元格样式，灵活备用************ */
			// 用于标题居中
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_center.setWrap(false); // 文字是否换行
			// 用于正文居左
			NumberFormat nf = new NumberFormat("0.00");
			WritableCellFormat wcf_left = new WritableCellFormat(nf);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行
			/** ***************以下是EXCEL大标题********************* */
			// sheet.mergeCells(0, 0, colWidth, 0);
			// sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
			/** ***************以下是EXCEL第一行列标题********************* */
			for (int i = 0; i < Title.length; i++) {
				sheet.addCell(new Label(i, 0, Title[i], wcf_center));
			}
			/** ***************以下是EXCEL正文数据********************* */
			Field[] fields = null;
			int i = 1;
			for (Object obj : listContent) {
				fields = obj.getClass().getDeclaredFields();
				int j = 0;
				for (Field v : fields) {
					v.setAccessible(true);
					Object va = v.get(obj);
					if (va == null) {
						va = "";
					}
					// System.out.println(va.getClass());
					if (va.getClass() == Double.class) {
						sheet.addCell(new jxl.write.Number(j, i, (Double) va, wcf_left));
					} else {
						sheet.addCell(new Label(j, i, va.toString(), wcf_left));
					}
					j++;
				}
				i++;
			}
			/** **********将以上缓存中的内容写到EXCEL******** */
			workbook.write();
			/** *********关闭文件************* */
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newfilename;
	}

	public static void downloadExcel(String filepath, String downloadname, HttpServletResponse response) {
		try {
			// String file= filepath;
			File file = new File(filepath);
			@SuppressWarnings("resource")
			InputStream is = new FileInputStream(file);
			response.reset(); // 必要地清除response中的缓存信息
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(downloadname.getBytes(), "ISO_8859_1") + CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmm") + ".xls");
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType("applicationnd.ms-excel");// 根据个人这个是下载文件的类型
			javax.servlet.ServletOutputStream out = response.getOutputStream();
			byte[] content = new byte[1024];
			int length = 0;
			while ((length = is.read(content)) != -1) {
				out.write(content, 0, length);
			}
			out.write(content);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param 文件
	 * @param 服务器路径
	 * @param 文件名
	 * @return boolean
	 */
	public static String uploadApp(File imageFile, String realpath, String fileName) {
		String path = null;
		if (imageFile != null) {

			// d:upload/temp/
			String tempPath = realpath;

			// 取得后缀名
			String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();

			// 时间戳加两位随机数据
			String severPicName = CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmmssSSS")+ (int) (Math.random() * 100) + extension;

			// 上传文件保存路径
			File savefile = new File(new File(tempPath), severPicName);
			if (!savefile.getParentFile().exists())
				savefile.getParentFile().mkdirs();

			// 复制文件到指定
			try {
				FileUtils.copyFile(imageFile, savefile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 保存路径
			path = severPicName;

		}
		return path;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的路径
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static void deleteFile(String sPath) {
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
		}
	}

	/**
	 * 给指定用户发送短信
	 * 
	 * @param phone
	 *            手机号码
	 * @param content
	 *            短信内容
	 * @return
	 */
	public static String sendSms(String phone, String content) {
		// http://202.91.244.252/qd/SMSSendYD?usr=***&pwd=***&mobile=***&sms=***&extdsrcid=***
		StringBuilder url = new StringBuilder();
		url.append(Constant.SMS_ADDRESS);
		url.append(Constant.SMS_USER);
		url.append("&pwd=");
		url.append(Constant.SMS_PWD);
		url.append("&mobile=");
		url.append(phone);
		url.append("&sms=");
		try {
			url.append(java.net.URLEncoder.encode(content, "GBK"));
		} catch (UnsupportedEncodingException e) {
		}
		String result = HttpRequest.sendGet(url.toString(), null);
		return result;
	}
	
	/**
	 * 随机字符串
	 * @return
	 */
	public static String randomString(){
		StringBuffer array = new StringBuffer();
		for (int i = 0; i <= 9; i++) {
			array.append(i);
		}
		//小写字母暂时不要
//		for (int i = (int) 'a'; i <= (int) 'z'; i++) {
//			array.append((char) i);
//		}
		for (int i = (int) 'A'; i <= (int) 'Z'; i++) {
			array.append((char) i);
		}
		int length = array.length();

		// 假设n现在为100
		int n = 30;
		// 存储最后生成的字符串
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < n; i++) {
			// 获得随机位置的字符
			char c = array.charAt((int) (Math.random() * length));
			str.append(c);
		}
		return str.toString();
	}
	
	public static String stringToXml(SortedMap<Object,Object> parameters){
		if(parameters!=null){
			StringBuffer xml = new StringBuffer();
			xml.append("<xml>");  
			  
			xml.append("<appid><![CDATA[");  
            xml.append(parameters.get("appid"));  
            xml.append("]]></appid>");  
            
            xml.append("<body><![CDATA[");  
            xml.append(parameters.get("body"));  
            xml.append("]]></body>");  
            
            xml.append("<fee_type><![CDATA[");  
            xml.append(parameters.get("fee_type"));  
            xml.append("]]></fee_type>");
  
            xml.append("<mch_id><![CDATA[");  
            xml.append(parameters.get("mch_id"));  
            xml.append("]]></mch_id>");  
  
            xml.append("<nonce_str><![CDATA[");  
            xml.append(parameters.get("nonce_str"));  
            xml.append("]]></nonce_str>");  
  
            xml.append("<notify_url><![CDATA[");  
            xml.append(parameters.get("notify_url"));  
            xml.append("]]></notify_url>");
            
            xml.append("<out_trade_no><![CDATA[");  
            xml.append(parameters.get("out_trade_no"));  
            xml.append("]]></out_trade_no>");  
            
            xml.append("<spbill_create_ip><![CDATA[");  
            xml.append(parameters.get("spbill_create_ip"));  
            xml.append("]]></spbill_create_ip>"); 
  
            xml.append("<total_fee><![CDATA[");  
            xml.append(parameters.get("total_fee"));  
            xml.append("]]></total_fee>");  
  
            xml.append("<trade_type><![CDATA[");  
            xml.append(parameters.get("trade_type"));  
            xml.append("]]></trade_type>");  
  
            xml.append("<sign><![CDATA[");  
            xml.append(parameters.get("sign"));  
            xml.append("]]></sign>");  
  
  
            xml.append("</xml>");  
            return xml.toString();  
		}else{
			return "";
		}
		
		
	}
	
	/**
	 * http传输获取数据
	 * @param requestUrl
	 * @param requestMethod
	 * @param output
	 * @return
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String output) {  
        try{  
            URL url = new URL(requestUrl);  
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();  
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setUseCaches(false);  
            connection.setRequestMethod(requestMethod);  
            if (null != output) {  
                OutputStream outputStream = connection.getOutputStream();  
                outputStream.write(output.getBytes("UTF-8"));  
                outputStream.close();  
            }  
            // 从输入流读取返回内容  
            InputStream inputStream = connection.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
            String str = null;  
            StringBuffer buffer = new StringBuffer();  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            inputStream.close();  
            inputStream = null;  
            connection.disconnect();  
            return buffer.toString();  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
  
        return "";  
    }  
	
	
	
	public static  String dateDiff(String startTime, String endTime) {
		//按照传入的格式生成一个simpledateformate对象
		String time = "";
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long nd = 1000*24*60*60;//一天的毫秒数
		long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
//		long ns = 1000;//一秒钟的毫秒数
		//获得两个时间的毫秒时间差异
		long diff;
		try {
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			long day = diff/nd;//计算差多少天
			long hour = diff%nd/nh;//计算差多少小时
			long min = diff%nd%nh/nm;//计算差多少分钟
//			long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果
//			System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟");
			if(day==0){
				if(hour==0){
					time=min+"分钟";
				}else{
					time=hour+"小时"+min+"分钟";
				}
			}else{
				time=day+"天"+hour+"小时"+min+"分钟";
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}
	
	 /**
     * 字符串转换为Ascii
     * @param value
     * @return
     */
    public static String stringToAscii(String value)  
    {  
        StringBuffer sbu = new StringBuffer();  
        char[] chars = value.toCharArray();   
        for (int i = 0; i < chars.length; i++) {  
            if(i != chars.length - 1)  
            {  
                sbu.append((int)chars[i]).append(",");  
            }  
            else {  
                sbu.append((int)chars[i]);  
            }  
        }  
        return sbu.toString();
    }
	/**
	 * @param POST_URL
	 * @param content
	 * @throws IOException
	 */
	public static String readContentFromPost(String POST_URL,String content) throws IOException {
        URL postUrl = new URL(POST_URL);
        // 打开连接
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
//        connection.setRequestProperty("Content-Type",
//                "application/x-www-form-urlencoded");
        connection.setRequestProperty("contentType", "utf-8");
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//        out.writeBytes(content); 
        out.write(content.getBytes("utf-8"));
        out.flush();
        out.close(); // flush and close
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream(),"utf-8"));
        String line="";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            return line;
        }
               
        reader.close();
        connection.disconnect();
        
        return line;
    }	
	
	public static String readContentFromURL(String POST_URL) throws IOException {
		URL url = new URL(POST_URL);//要调用的url
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");//设置get方式获取数据
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
//        conn.setRequestProperty("Host", SERVICE_HOST);       
        String lines = null;
        if (conn.getResponseCode() == 200) {//如果连接成功
            BufferedReader br = new BufferedReader(new InputStreamReader(conn
                    .getInputStream()));//创建流
            
            while ((lines = br.readLine()) != null) {
                System.out.println(lines.replaceAll("&[lg]t;",""));//将读取到的数据打印
                return lines;
            }
            br.close();
            
        }
        conn.disconnect();	
        return lines;
//		URL postUrl = new URL(POST_URL);
//		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
//		connection.setDoOutput(true);
//        connection.setDoInput(true);
//        connection.setRequestMethod("POST");
//        connection.setUseCaches(false);
//        connection.setInstanceFollowRedirects(true);
//        connection.setRequestProperty("Content-Type",
//                "application/x-www-form-urlencoded");
//        connection.connect();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(
//                connection.getInputStream(),"utf-8"));
//        String line="";
//        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
//            return line;
//        }
//               
//        reader.close();
//        connection.disconnect();
//        
//        return line;
//     }
	}	
	
	public static byte[] intToByteArray(int i) {   
		  byte[] result = new byte[4];   
		  //由高位到低位
		  result[0] = (byte)((i >> 24) & 0xFF);
		  result[1] = (byte)((i >> 16) & 0xFF);
		  result[2] = (byte)((i >> 8) & 0xFF); 
		  result[3] = (byte)(i & 0xFF);
		  return result;
	}
	
	public static long dateDifferent(Date startTime,Date endTime) throws ParseException{
		long diffMinutes = 0;
		long diff = endTime.getTime() - startTime.getTime();
		diffMinutes = diff / (60 * 1000) % 60;
		return diffMinutes;
//		long diffSeconds = diff / 1000 % 60;
//      long diffMinutes = diff / (60 * 1000) % 60;
//      long diffHours = diff / (60 * 60 * 1000) % 24;
//      long diffDays = diff / (24 * 60 * 60 * 1000);
		 
	}
	
	//上传图片
	public static boolean generatePic(String picString, String picurl) { // 对字节数组字符串进行Base64解码并生成图片
		if (picurl == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
				// Base64解码
			byte[] b = decoder.decodeBuffer(picString);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(picurl);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
				return false;
		}
	}
	
	public static String sendCode(String phone){
		Random random = new Random();
		StringBuilder vercode = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			vercode.append(random.nextInt(10));
		}
		String content = "";
		content = "尊敬的用户，您的验证码是:" + vercode.toString() + "。验证码有效时间为5分钟";
		CommonUtils.sendSms(phone, content);
		return vercode.toString();
	}
	
	public static String creatUUID() {  
        return UUID.randomUUID().toString().replace("-", "");  
	} 
		
	/** 
	 * 判断点是否在多边形内 
	 * @param point 检测点 
	 * @param pts   多边形的顶点 
	 * @return      点在多边形内返回true,否则返回false 
	 */  
	public static boolean IsPtInPoly(Point2D.Double point, List<Point2D.Double> pts){  
	      
	    int N = pts.size();  
	    boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true  
	    int intersectCount = 0;//cross points count of x   
	    double precision = 2e-10; //浮点类型计算时候与0比较时候的容差  
	    Point2D.Double p1, p2;//neighbour bound vertices  
	    Point2D.Double p = point; //当前点  
	      
	    p1 = pts.get(0);//left vertex          
	    for(int i = 1; i <= N; ++i){//check all rays              
	        if(p.equals(p1)){  
	            return boundOrVertex;//p is an vertex  
	        }  
	          
	        p2 = pts.get(i % N);//right vertex              
	        if(p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)){//ray is outside of our interests                  
	            p1 = p2;   
	            continue;//next ray left point  
	        }  
	          
	        if(p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)){//ray is crossing over by the algorithm (common part of)  
	            if(p.y <= Math.max(p1.y, p2.y)){//x is before of ray                      
	                if(p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)){//overlies on a horizontal ray  
	                    return boundOrVertex;  
	                }  
	                  
	                if(p1.y == p2.y){//ray is vertical                          
	                    if(p1.y == p.y){//overlies on a vertical ray  
	                        return boundOrVertex;  
	                    }else{//before ray  
	                        ++intersectCount;  
	                    }   
	                }else{//cross point on the left side                          
	                    double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;//cross point of y                          
	                    if(Math.abs(p.y - xinters) < precision){//overlies on a ray  
	                        return boundOrVertex;  
	                    }  
	                      
	                    if(p.y < xinters){//before ray  
	                        ++intersectCount;  
	                    }   
	                }  
	            }  
	        }else{//special case when ray is crossing through the vertex                  
	            if(p.x == p2.x && p.y <= p2.y){//p crossing over p2                      
	                Point2D.Double p3 = pts.get((i+1) % N); //next vertex                      
	                if(p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)){//p.x lies between p1.x & p3.x  
	                    ++intersectCount;  
	                }else{  
	                    intersectCount += 2;  
	                }  
	            }  
	        }              
	        p1 = p2;//next ray left point  
	    }  
	      
	    if(intersectCount % 2 == 0){//偶数在多边形外  
	        return false;  
	    } else { //奇数在多边形内  
	        return true;  
	    }  
	      
	}  
	
	public static String PackingFactoryInfo(String param,String type){
		String res = "";
		if(type.equals("mac")){
			String[] m = new String[6];
			m[0] = param.substring(0, 2);
			m[1] = param.substring(2, 4);
			m[2] = param.substring(4, 6);
			m[3] = param.substring(6, 8);
			m[4] = param.substring(8, 10);
			m[5] = param.substring(10, 12);
			for(int i=0;i < 6; i++){
				if(i == 5){
					res = res + m[i];
				}else{
					res = res + m[i] + ":";
				}
			}
		}
		
		//71 99 98 22 29 52 70 34 53 65 75 01 78 88 852
		if(type.equals("key")){
			String[] ky = new String[16];
			for(int i=0;i < 16; i++){
				ky[i] = param.substring(2*i, 2*i + 2);
			}
			for(int j=0;j < 16; j++){
				if(j == 15){
					res = res + ky[j];
				}else{
					res = res + ky[j] + ",";
				}
			}
		}
		if(type.equals("pass")){
			String[] ps = new String[6];
			for(int i=0;i < 6; i++){
				ps[i] = param.substring(2*i, 2*i + 2);
			}
			for(int j=0;j < 6;j++){
				if(j == 5){
					res = res + ps[j];
				}else{
					res = res + ps[j] + ",";
				}
			}
		}
		
		return res;
	}
	
	public static int SendOpenLockCmd(String simNo){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("simNo", simNo);
		
		JSONObject backResult;
		try {
			backResult = new JSONObject(BaiduYingYanUtil.openLock(params));
			String code = backResult.get("code").toString();
			if(code.equals("1")){
				return 1;
			}else{
				return 2;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return 2;
		}
	}

	public static int SendOpenBatteryLockCmd(String simNo){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("simNo", simNo);

		JSONObject backResult;
		try {
			backResult = new JSONObject(BaiduYingYanUtil.openBatteryLock(params));
			String code = backResult.get("code").toString();
			if(code.equals("1")){
				return 1;
			}else{
				return 2;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return 2;
		}
	}

	public static int SendCloseMotorLockCmd(String simNo){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("simNo", simNo);

		JSONObject backResult;
		try {
			backResult = new JSONObject(BaiduYingYanUtil.closeMotorLock(params));
			String code = backResult.get("code").toString();
			if(code.equals("1")){
				return 1;
			}else{
				return 2;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return 2;
		}
	}

	//开助力车电池锁接口
	public static int SendOpenMopedBatteryLock(String simNo){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("simNo", simNo);
		JSONObject backResult;
		try {
			backResult = new JSONObject(BaiduYingYanUtil.openMopedBatteryLock(params));
			String code = backResult.get("code").toString();
			if(code.equals("1")){
				return 1;
			}else{
				return 2;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return 2;
		}
	}
    //操作助力车电机锁接口
    public static int SendControlMopedElectriclock(String simNo,int flag){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("simNo", simNo);
        params.put("flag",flag);
        params.put("actionType",0);
        JSONObject backResult;
        try {
            backResult = new JSONObject(BaiduYingYanUtil.controlMopedElectriclock(params));
            String code = backResult.get("code").toString();
            if(code.equals("1")){
                return 1;
            }else{
                return 2;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 2;
        }
    }
	//短信开电瓶锁
	public static Map<String,Object> sendSMSLock(String iccid,String action){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("iccid", iccid);
		try {
			Map mapTypes = JSON.parseObject(BaiduYingYanUtil.sendSMSLock(params,action));
			return mapTypes;
		} catch (JSONException e) {
		}
		return null;
	}


    //获取注销锁的信息
    public static JSONObject getCancellationLockInfo(String  bicycleNo){
		Map<String,Object> params = new HashMap<>();
		params.put("bicycleNo", bicycleNo);
		JSONObject backResult;
		try {
		backResult = new JSONObject(BaiduYingYanUtil.getCancellationLockInfo(params));
		  return  backResult;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	//获取车辆是否注销
	public  static  JSONObject getBikeInfo(String bicycleNo){
		Map<String,Object> params = new HashMap<>();
		params.put("bicycleNo", bicycleNo);
		JSONObject backResult;
		try {
			backResult = new JSONObject(BaiduYingYanUtil.getBikeInfo(params));
			return  backResult;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int SendQueryLockCmd(String simNo){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("simNo", simNo);
		
		JSONObject backResult;
		try {
			backResult = new JSONObject(BaiduYingYanUtil.queryLock(params));
			String code = backResult.get("code").toString();
			if(code.equals("1")){
				return 1;
			}else{
				return 2;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return 2;
		}
	}
	
	public static int SendUpdateLockCmd(String simNo,String updateVersion){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("simNo", simNo);
		params.put("updateVersion", updateVersion);
		JSONObject backResult;
		try {
			backResult = new JSONObject(BaiduYingYanUtil.updateLock(params));
			String code = backResult.get("code").toString();
			if(code.equals("1")){
				return 1;
			}else{
				return 2;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return 2;
		}
	}

	public static String getBicycleNo(String barcode){
		if (barcode.indexOf("http://www.99bicycle.com") != -1) {
			System.out.println("有效二维码");
			int index = barcode.indexOf("b=");
			System.out.println("index:" + index);
			String bicycleNo = barcode.substring(index + 2);
			System.out.println(bicycleNo);
			return bicycleNo;
		}else {
			return "1";
		}

	}


}
