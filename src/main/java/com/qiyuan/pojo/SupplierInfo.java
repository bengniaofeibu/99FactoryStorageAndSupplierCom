package com.qiyuan.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_supplier_info")
public class SupplierInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3749416441555925162L;

	@Id
	@GenericGenerator(name="uuid",strategy="uuid")
	@GeneratedValue(generator="uuid")
	@Column(name = "id", length = 64, nullable = false)
	private String id;
	
	//供应商名字
	@Column(name = "supplier_name",length = 50)
	private String supplierName;
	
	//供应商联系人
	@Column(name = "supplier_contact",length = 50)
	private String supplierContact;
	
	//供应商联系方式
	@Column(name = "supplier_phone")
	private String supplierPhone;
	
	//供应商密码
	@Column(name = "supplier_password")
	private String supplierPassword;
	
	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "add_time")
	private Date addTime;
	
	// 登陆状态 1、未登录 2、登录
	@Column(name = "loginState", length = 1, columnDefinition = "INT default 1")
	private Integer loginState = 1;
		
	// 登陆时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "loginTime")
	private Date loginTime;

	public Integer getLoginState() {
		return loginState;
	}

	public void setLoginState(Integer loginState) {
		this.loginState = loginState;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getSupplierPassword() {
		return supplierPassword;
	}

	public void setSupplierPassword(String supplierPassword) {
		this.supplierPassword = supplierPassword;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierContact() {
		return supplierContact;
	}

	public void setSupplierContact(String supplierContact) {
		this.supplierContact = supplierContact;
	}

	public String getSupplierPhone() {
		return supplierPhone;
	}

	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}
	
	
}
