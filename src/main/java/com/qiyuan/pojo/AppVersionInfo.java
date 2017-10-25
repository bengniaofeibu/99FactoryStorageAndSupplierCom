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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_app_version_info")
public class AppVersionInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4397321472028930446L;
	@Id
	@GenericGenerator(name="uuid",strategy="uuid")
	@GeneratedValue(generator="uuid")
	@Column(name = "id", length = 64, nullable = false)
	private String id;
	
	@Column(name = "version_id", length = 10, nullable = false)
	private String versionId;
	
	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime", nullable = false)
	private Date addtime;
	
	@Column(name = "remarks")
	private String remarks;

	@Column(name="url",length = 255)
	private  String url;

	public AppVersionInfo(String versionId, Date addtime, String remarks, String url) {
		this.versionId = versionId;
		this.addtime = addtime;
		this.remarks = remarks;
		this.url = url;
	}

	public AppVersionInfo(String id, String versionId, Date addtime, String remarks, String url) {
		this.id = id;
		this.versionId = versionId;
		this.addtime = addtime;
		this.remarks = remarks;
		this.url = url;
	}

	public AppVersionInfo(){ }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}
