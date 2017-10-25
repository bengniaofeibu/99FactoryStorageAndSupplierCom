package com.qiyuan.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="t_admin_push_info")
public class AdminPushInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6993679038219843043L;
	
	// 推送ID
	@Id
	@GenericGenerator(name="uuid",strategy="uuid")
	@GeneratedValue(generator="uuid")
	@Column(name = "id", length = 64, nullable = false)
	private String id;
			
	//用户ID
	@Column(name = "adminId", length = 64, nullable = false)
	private String adminId;
					
	//Android设备号
	@Column(name = "DeviceToken")
	private String DeviceToken;
			

	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime", nullable = false)
	private Date addtime;
	
	//地域代码（预留）
	@Column(name = "area_no", length = 10)
	private String areaNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getDeviceToken() {
		return DeviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		DeviceToken = deviceToken;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}	

}
