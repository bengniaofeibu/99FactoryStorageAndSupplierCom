package com.qiyuan.common;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

import java.io.IOException;

public class PushtoSingle {
	public void pushsingle(String cid, String content) {

			IGtPush push = new IGtPush(Constant.HOST, Constant.APPKEY, Constant.MASTER);
			try {
				push.connect();
				TransmissionTemplate template = transmissionTemplateDemo(content);
				SingleMessage message = new SingleMessage();
				message.setOffline(true);
				// 离线有效时间，单位为毫秒，可选
				message.setOfflineExpireTime(24 * 3600 * 1000);
				message.setData(template);
				message.setPushNetWorkType(0); // 可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
				Target target = new Target();
				target.setAppId(Constant.APPID);
				target.setClientId(cid);
				// 用户别名推送，cid和用户别名只能2者选其一
				IPushResult ret = push.pushMessageToSingle(message, target);
				System.out.println(ret.getResponse().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}

	public static TransmissionTemplate transmissionTemplateDemo(String content) {
		//content 内容为推送显示内容
		TransmissionTemplate template = new TransmissionTemplate();

		template.setAppId(Constant.APPID);
		template.setAppkey(Constant.APPKEY);

		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动

		template.setTransmissionType(2);
		template.setTransmissionContent(content);

		 APNPayload payload = new APNPayload();
		    payload.setBadge(1);
//		    payload.setContentAvailable(1);
		    payload.setSound("default");
		    payload.setCategory("$由客户端定义");
		    String[] split = content.split(":");
//		    payload.setAlertMsg(new APNPayload.SimpleAlertMsg("换车信息"));
//		    payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hahhaha"));
		    //字典模式使用下者
		    payload.setAlertMsg(getDictionaryAlertMsg(split));
		    template.setAPNInfo(payload);
		return template;

	}
	
	private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String[] split){
	    APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
	    alertMsg.setBody(split[1]);
	    alertMsg.setActionLocKey("阅读通知");//主界面下部滑动文字
	    alertMsg.setLocKey("尊敬的用户，您有新的通知");//主界面主体文字
	    alertMsg.addLocArg("loc-args");
	    alertMsg.setLaunchImage("launch-image");
	    // IOS8.2以上版本支持
	    //alertMsg.setTitle("Title");
	    alertMsg.setTitleLocKey("赳赳");//通知下拉Title
	    alertMsg.addTitleLocArg("TitleLocArg");
	    return alertMsg;
	}

}
