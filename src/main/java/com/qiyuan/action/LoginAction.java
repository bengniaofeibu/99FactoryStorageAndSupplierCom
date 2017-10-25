package com.qiyuan.action;

//import java.util.ArrayList;
//import java.util.List;

//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.qiyuan.common.HttpRequest;
import com.qiyuan.pojo.LockFactoryInfo;
import com.qiyuan.pojo.LockTerminalInfo;
import com.qiyuan.service.ILockFactoryInfoService;
import com.qiyuan.service.ILockTerminalService;
import com.qiyuan.serviceImpl.LockTerminalServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

//import com.qiyuan.common.CommonUtils;
//import com.daoshun.guangda.pojo.AdminInfo;
//import com.daoshun.guangda.service.ICUserService;

@ParentPackage("default")
@Scope("prototype")
@Controller
public class LoginAction extends BaseAction {

	/**
		 * 
		 */
	private static final long serialVersionUID = 2715755623041150341L;

	private static  Log logger=LogFactory.getLog(LoginAction.class);

// @Resource
// private ICUserService cuserService;

//	@Resource
//	private IAdminService adminService;

	private String adminName;

	private String password;
	
	private int type;

	private String errmessage;

	private String issession;

	/**location = "/index.jsp"
	 * 登陆
	 * @return
	 */
//	@Action(value = "login", results = { @Result(name = SUCCESS,location = "/jumpToIndex.do", type="redirect"),
//			@Result(name = ERROR, location = "/login.jsp") })
//	public String login() {
//		if (CommonUtils.parseInt(issession, 0) == 1) {
//			errmessage = "长时间未操作、或非正常登陆";
//			return ERROR;
//		}
//		if (CommonUtils.isEmptyString(adminName)) {
//			errmessage = "请输入登录用户名";
//			return ERROR;
//		}
//		if (CommonUtils.isEmptyString(password)) {
//			errmessage = "请输入密码";
//			return ERROR;
//		}
//
//		// HttpServletResponse response = ServletActionContext.getResponse();
//		HttpSession session = ServletActionContext.getRequest().getSession();
//		session.setAttribute("session_err", "");

//		AppAdminInfo admin = adminService.getAdminByAdminName(adminName);
//		if (admin != null) {
//			if(type==2){
//				if (admin.getPassword().equals(password)) {
//					session.setAttribute("userName", admin.getRealName());
//					session.setAttribute("userId", admin.getAdminId());
//					session.setAttribute("userType", admin.getAdminType());
//					return SUCCESS;
//				} else {
//					errmessage = "用户、密码错误";
//					return ERROR;
//				}
//			}else{
//				if (admin.getPassword().equals(CommonUtils.MD5(password))) {
//					session.setAttribute("userName", admin.getRealName());
//					session.setAttribute("userId", admin.getAdminId());
//					session.setAttribute("userType", admin.getAdminType());
//					return SUCCESS;
//				} else {
//					errmessage = "用户、密码错误";
//					return ERROR;
//				}
//			}
//			
//			
//			
//		} else {
//			errmessage = "用户、密码错误";
//			return ERROR;
//		}
//	}
	@Resource(name = "lockFactoryInfoService")
	public ILockFactoryInfoService iLockFactoryInfoService;


	@GetMapping(value = "/test")
	@ResponseBody
	public  Object test(){
		LockFactoryInfo LockFactoryInfo=iLockFactoryInfoService.getLockFactoryInfoByLockFactoryNo("12312");
		logger.debug("========================="+LockFactoryInfo.getId());
		return LockFactoryInfo.getId();
	}

	/**
	 * 跳转首页
	 * @return
	 */
//	@Action(value = "jumpToIndex", results = { @Result(name = SUCCESS, location = "/index.jsp")})
	@GetMapping(value = "/jumpToIndex")
	public String jumpToIndex() {

		return "index";
	}
	

	/**
	 * 退出登陆
	 * @return
	 */
	/*@Action(value = "logout", results = { @Result(name = SUCCESS, location = "/login.jsp")})

	@ApiImplicitParams({
			@ApiImplicitParam(name = "user", required = true, value = "用户的密码", dataType = "User")
	})*/
	@GetMapping(value ="/logout")
	public String logout(HttpServletRequest request) {
		logger.debug("========================");

		/*HttpSession session = ServletActionContext.getRequest().getSession();
		session.invalidate();*/
		request.getSession().invalidate();
		return "login";
	}


	@Action(value = "jumpToHelp", results = { @Result(name = SUCCESS, location = "/operationHelp.jsp")})
	public String jumpToHelp() {
		return SUCCESS;
	}
	
	@Action(value = "jumpUserManage", results = { @Result(name = SUCCESS, location = "/userManage.jsp")})
	public String jumpUserManage() {
		return SUCCESS;
	}
	
	/**
	 * @return the adminName
	 */
	public String getAdminName() {
		return adminName;
	}

	/**
	 * @param adminName
	 *            the adminName to set
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the errmessage
	 */
	public String getErrmessage() {
		return errmessage;
	}

	/**
	 * @param errmessage
	 *            the errmessage to set
	 */
	public void setErrmessage(String errmessage) {
		this.errmessage = errmessage;
	}

	/**
	 * @return the issession
	 */
	public String getIssession() {
		return issession;
	}

	/**
	 * @param issession
	 *            the issession to set
	 */
	public void setIssession(String issession) {
		this.issession = issession;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	
}
