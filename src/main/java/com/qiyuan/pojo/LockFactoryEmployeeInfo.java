package com.qiyuan.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_lock_factory_employee")
public class LockFactoryEmployeeInfo extends BasePojo{

	/**
	 *
	 */

	@Id
	@GenericGenerator(name="uuid",strategy="uuid")
	@GeneratedValue(generator="uuid")

	@Column(name = "id", length = 64, nullable = false)
	private String id;
		
	// 登录密码
	@Column(name = "password")
	private String Password;

	// 员工姓名
	@Column(name = "real_name", length = 50)
	private String realName;
		
	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "add_time")
	private Date addTime;
	
	// 物联锁厂编号
	@Column(name = "lock_factory_no")
	private String lockFactoryNo;
	
	// 物联锁厂名
	@Column(name = "factory_name")
	private String factoryName;
	
	// 员工头像Url
	@Column(name = "photoUrl")
	private String photoUrl;

	// 登陆状态 1、未登录 2、登录
	@Column(name = "login_state", length = 1, columnDefinition = "INT default 1")
	private Integer loginState = 1;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "login_time")
	private Date loginTime;

//	public LockFactoryEmployeeInfo(String id, String password, String realName, Date addTime, String lockFactoryNo, String factoryName, String photoUrl, String loginState) {
//		this.id = id;
//		Password = password;
//		this.realName = realName;
//		this.addTime = addTime;
//		this.lockFactoryNo = lockFactoryNo;
//		this.factoryName = factoryName;
//		this.photoUrl = photoUrl;
//		this.loginState = loginState;
//	}
//
//	public LockFactoryEmployeeInfo(String id, String realName, Date addTime, String lockFactoryNo, String factoryName, String photoUrl, String loginState) {
//		this.id = id;
//		this.realName = realName;
//		this.addTime = addTime;
//		this.lockFactoryNo = lockFactoryNo;
//		this.factoryName = factoryName;
//		this.photoUrl = photoUrl;
//		this.loginState = loginState;
//	}
	public LockFactoryEmployeeInfo(String id, String realName, Date addTime, String lockFactoryNo, String factoryName,
							   String photoUrl) {
	super();
	this.id = id;
	this.realName = realName;
	this.addTime = addTime;
	this.lockFactoryNo = lockFactoryNo;
	this.factoryName = factoryName;
	this.photoUrl = photoUrl;
}

	public LockFactoryEmployeeInfo(String id, String password, String realName, Date addTime, String lockFactoryNo,
								   String factoryName, String photoUrl) {
		super();
		this.id = id;
		Password = password;
		this.realName = realName;
		this.addTime = addTime;
		this.lockFactoryNo = lockFactoryNo;
		this.factoryName = factoryName;
		this.photoUrl = photoUrl;
	}


	//id,realName,addTime,lockFactoryNo,factoryName,photoUrl,logState
//	public LockFactoryEmployeeInfo(String id,String realName, Date addTime, String lockFactoryNo, String factoryName,
//			String photoUrl,String loginState) {
//		super();
//		this.id = id;
//		this.realName = realName;
//		this.addTime = addTime;
//		this.lockFactoryNo = lockFactoryNo;
//		this.factoryName = factoryName;
//		this.photoUrl = photoUrl;
//		this.loginState = loginState;
//	}
//
//	public LockFactoryEmployeeInfo(String id,String password, String realName, Date addTime, String lockFactoryNo, String factoryName, String photoUrl, String loginState) {
//		this.id = id;
//		this.Password = password;
//		this.realName = realName;
//		this.addTime = addTime;
//		this.lockFactoryNo = lockFactoryNo;
//		this.factoryName = factoryName;
//		this.photoUrl = photoUrl;
//		this.loginState = loginState;
//	}

	public LockFactoryEmployeeInfo() {
		super();

	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Integer getLoginState() {
		return loginState;
	}

	public void setLoginState(Integer loginState) {
		this.loginState = loginState;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getLockFactoryNo() {
		return lockFactoryNo;
	}

	public void setLockFactoryNo(String lockFactoryNo) {
		this.lockFactoryNo = lockFactoryNo;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}


}
