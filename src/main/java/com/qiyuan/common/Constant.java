/**
 * 
 */
package com.qiyuan.common;

public class Constant {
	/********************* 接口返回参数 **************************/

	public static final String CODE = "code";

	public static final String MESSAGE = "message";

	public static final String EXCEPTION = "{\"message\":\"网络状况不佳，请稍后再试\",\"code\":100}";

	public static final int frequency = 0;
	
	//短信有效时间
	public static final int SMSDELAY = 5;
	
	public static final int pageSize = 10;
	
	/*************************** 回复标志 ****************************/
	public static final int Success = 1;
	
	public static final int Error_CodeUsed = 101;
	
	public static final int Error_CodeDelay= 102;
	
	public static final int Error_CodeFailed= 103;
	
	public static final int Error_CodeOverUsed= 104;
	
	public static final int Error_CodeVerificationFailed= 105;
	
	public static final int Error_AdminNoExist= 106;
	
	public static final int Error_Server= 107;
	
	public static final int Error_UploadIdCardnum= 108;
	
	public static final int Error_LoginFailed= 109;
	
	public static final int Error_LogOut= 110;
	
	public static final int Error_AnotherLogOut= 111;
	
	public static final int Error_FindPsw= 112;
	
	public static final int Error_ChangePsw= 113;
	
	public static final int Error_Psw= 114;
	
	public static final int Error_ChangePhone= 115;
	
	public static final int Error_IdentityInfo= 116;
	
	public static final int Error_AdminExist= 117;
	
	public static final int Error_PositionFailed= 118;
	
	public static final int Error_LocatingFailed= 119;
	
	public static final int Error_ModifyEstimateParkingNum= 120;
	
	public static final int Error_FaultBikeRegistration= 121;

	public static final int Error_DispatchList= 122;
	
	public static final int Error_ModifyDispatchList= 123;
	
	public static final int Error_DelList= 124;
	
	public static final int Error_ModifyRepairList= 125;
	
	public static final int Error_DelRepairList= 126;
	
	public static final int Error_LocatingRepairPoint= 127;
	
	public static final int Error_AcceptList= 128;
	
	public static final int Error_EndMyAcceptList= 129;
	
	public static final int Error_DelLocatingInfo= 130;
	
	public static final int Error_BingdingCar= 131;
	
	public static final int Error_FaultBikeRelieved= 132;
	
	public static final int Error_UploadParkingNum= 133;
	
	public static final int Error_AddEntity = 502;
	
	public static final int Error_DeletePoi = 503;
	
	public static final int Error_DeleteEntity = 504;

	public static final int Error_UpdateEntity = 505;

	public static final int Error_UploadBicycleNum= 510;
	
	public static final int Error_AddPosPoint = 519;

	public static final int Error_UpdatePosPoi = 515;

	public static final int Error_SupplierLogin = 516;

	public static final int Error_GetFactoryQuantity = 520;

	public static final int Error_AdminOpenLock = 520;
	
	public static final int Error_GetAppVersion = 521;
	
	public static final int Error_AccountNoExist = 522;

	public static final int Error_FaultBarcode = 523;

	public static final int Error_DailyOutput=525;

	public static final int Error_Deregistration=526;

	public static final int Error_SupplierOutLogin = 522;

	/*************************** 用户模块接口名 ****************************/
	
	/**
	 * 注册接口
	 */
	public static final String REGISTER = "REGISTER";
	
	/**
	 * 登陆接口
	 */
	public static final String LOGIN = "LOGIN";
	
	/**
	 * 完善个人信息接口
	 */

	public static final String CHANGEPSW = "CHANGEPSW";

	
	/**
	 * 退出登陆接口
	 */
	public static final String LOGOUT = "LOGOUT";

	
	
	/************************ 出厂信息模块接口名 *****************************/
	
	public static final String UPLOADTERMINALINFO = "UPLOADTERMINALINFO";
	
	public static final String GETBLUETOOTHINFO = "GETBLUETOOTHINFO";

	public static final String GETBLUETOOTHINFOBYID = "GETBLUETOOTHINFOBYID";

	public static final String FACTORYGPRSOPENLOCK = "FACTORYGPRSOPENLOCK";

	public static final String FACTORYGPRSOPENLOCKBYID = "FACTORYGPRSOPENLOCKBYID";
	
	public static final String ISSTORAGE = "ISSTORAGE";
		
	public static final String UPDATELOCK = "UPDATELOCK";
	
	public static final String QUERYLOCK = "QUERYLOCK";
	
	public static final String SENDQUERYLOCKCMD = "SENDQUERYLOCKCMD";
	
	public static final String QUERYLOCKREALDATA = "QUERYLOCKREALDATA";
	
	public static final String GETAPPVERSION = "GETAPPVERSION";
	
	public static final String GETKEYPSW = "GETKEYPSW";

	public static final String QUERYLOCKBYSIMNO = "QUERYLOCKBYSIMNO";

	public static final String SENDQUERYLOCKCMDBYSIMNO = "SENDQUERYLOCKCMDBYSIMNO";

	public static final String UPDATELOCKBYSIMNO = "UPDATELOCKBYSIMNO";

	public static final String FACTORYGPRSOPENLOCKBYSIMNO = "FACTORYGPRSOPENLOCKBYSIMNO";

	public static final String CHANGEBARCODE = "CHANGEBARCODE";

	public static final String GPRSOPENBATTERYLOCK = "GPRSOPENBATTERYLOCK";

	public static final String GPRSCLOSEMOTORLOCK = "GPRSCLOSEMOTORLOCK";

	public static final String GETSIMNOBYBARCODE = "GETSIMNOBYBARCODE";

	public static final String GETBARCODEBYSIMNO = "GETBARCODEBYSIMNO";

	public static  final  String OPEN_BATTERY_LOCK="OPENBATTERYLOCK";

	public static  final  String CONTRO_LELECTRIC_LOCK="CONTROLELECTRICLOCK";

	public static final String GET_CANCELLATION_LOCK_INFO="GETCANCELLATIONLOCKINFO";

	public static final String TEST = "TEST";


	/************************ 客户端模块接口名 *****************************/

	public static final String ADDBICYCLE = "ADDBICYCLE";

	public static final String DELETEBICYCLE = "DELETEBICYCLE";

	public static final String UPDATEBICYCLE = "UPDATEBICYCLE";

	public static final String GETNUM = "GETNUM";

	public static final String ADDBATTERY = "ADDBATTERY";

	public static final String UPDATEBATTERY = "UPDATEBATTERY";

	public static final String SUPPLIERUPLOADBICYCLE = "SUPPLIERUPLOADBICYCLE";

	public static final String ADDPOS = "ADDPOS";

	public static final String UPLOADPOSLOCATION = "UPLOADPOSLOCATION";

	public static final String SUPPLIERLOGIN = "SUPPLIERLOGIN";

	public static final String BICYCLEFACTORYSETTING = "BICYCLEFACTORYSETTING";

	public static final String ISBICYCLENUMONLY = "ISBICYCLENUMONLY";

	public static final String ISBATTERYNUMONLY = "ISBATTERYNUMONLY";

	public static final String GETFACTORYQUANTITY = "GETFACTORYQUANTITY";

	public static final String INBICYCLE = "INBICYCLE";

	public static final String OUTLOGIN = "OUTLOGIN";

	public static final String DAILYOUTPUT = "DAILYOUTPUT";

	public static final String DEREGISTRATION = "DEREGISTRATION";

	public static final String GETBIKEINFO = "GETBIKEINFO";

	public static final  String GET_BICK_SUPPLIER_NAME="GETBICKSUPPLIERNAME";

    public static final  String CHANGE_BICYCLE_LOCK="CHANGEBICYCLELOCK";

	public static final int Fail=524;

	/****************** 获取锁状态的接口 ******************/

     public static final String GET_LIANTONG_LOCK_STATUS="GETLIANTONGLOCKSTATUS";


	/****************** 验证码短信配置 ********************/
	/**
	 * 普讯服务器接口地址
	 */
	public static final String SMS_ADDRESS = "http://202.91.244.252/qd/SMSSendYD?usr=";

	/**
	 * 普讯用户账号
	 */
	public static final String SMS_USER = "7188";

	/**
	 * 普讯密码
	 */
	public static final String SMS_PWD = "qyuan@7188zj";

	/****************** socket编程 ********************/

	/**
	 * 本机端口 联通
	 */
	public static final String SOCKET_IP = "127.0.0.1";


	/**
	 * 程序端口号
	 */
	public static final int SOCKET_PORT = 4009;

	/**************************** 银联支付配置信息 *****************************/

	/**
	 * 银联编码
	 */
	public static final String UNOPENCODING = "GBK";

	/**
	 * 商户号码
	 */
	public static final String UNOPMERID = "自己定义";

	/**
	 * 银联接口版本号
	 */
	public static final String UNOPVERSION = "5.0.0";

	// 后台服务对应的写法参照 FrontRcvResponse.java
	public static final String FRONTURL = "";

	/************************ 个推信息 *************************/
	// 个推ID
	public static final String APPID = "isJrBGmGgQ7wNYtF0fBdp";

	// 个推密钥
	public static final String APPKEY = "1bb7flDKL37IMDQZgIwgV7";

	// 头信息
	public static final String MASTER = "3QL7UMJg3Q8UiJZ60bCln6";

	// 接口地址
	public static final String HOST = "http://sdk.open.api.igexin.com/apiex.htm";

	/****************************** 微信支付信息 ******************************/
	// 移动应用ID（骐远--骐客）
	public static final String WXAPPID = "wx46d0d6a6883e3fc6";

	// 商户ID（骐远--骐客）
	public static final String WXMCH_ID = "1319172901";

	// 商品显示主题
	public static final String WXBODY = "qike";

	// 回调地址
	public static final String WXNOTIFY_URL = CommonUtils.getWebRootUrl() + "weixin_callback";

	// 货币类型
	public static final String WXFEE_TYPE = "CNY";

	// 商家密钥
	public static final String WXKEY = "ldg53v2c1145f321d1g4f3c1v211v65k";

	// 微信接口地址
	public static final String WXPOSTURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	// 拓展字段
	public static final String WXPACKAGE = "Sign=WXPay";
	
	
}
