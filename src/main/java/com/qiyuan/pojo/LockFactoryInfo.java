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
@Table(name="t_lock_factory_info")
public class LockFactoryInfo extends BasePojo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9081007243086080200L;
	
	@Id 
	@GenericGenerator(name="uuid",strategy="uuid")
	@GeneratedValue(generator="uuid")
	@Column(name = "id", length = 64, nullable = false)
	private String id;
	
	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "add_time")
	private Date addTime;
	
	// 物联锁厂编号
	@Column(name = "lock_factory_no")
	private String lockFactoryNo;
	
	// 物联锁厂名
	@Column(name = "factory_name", nullable = false)
	private String factoryName;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "LockFactoryInfo{" +
				"id='" + id + '\'' +
				", addTime=" + addTime +
				", lockFactoryNo='" + lockFactoryNo + '\'' +
				", factoryName='" + factoryName + '\'' +
				'}';
	}
}
