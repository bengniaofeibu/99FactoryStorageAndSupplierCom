package com.qiyuan.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_bike_out_storge_info")
public class BikeOutStorgeInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -726817750257377236L;
	@Id
	@GenericGenerator(name="uuid",strategy="uuid")
	@GeneratedValue(generator="uuid")
	@Column(name = "id", length = 64, nullable = false)
	private String id;
	@Column(name = "bicycle_no", length = 10, nullable = false)
	private int bicycleNo;
	@Column(name = "bicycle_type", length = 2, nullable = false)
	private int bicycleType;
	//供应商名字
	@Column(name = "supplier_name",length = 50)
	private String supplierName;

	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "add_time")
	private Date addTime;

	//删除标志(0:正常；1:删除)
	@Column(name = "del_flag",length = 2,nullable = false)
	private Integer delFlag;

	//车辆型号(0:一代车，1:二代车)
	@Column(name = "bicycle_model",length = 2,nullable = false)
	private String bicycleModel;

	public String getBicycleModel() {
		return bicycleModel;
	}

	public void setBicycleModel(String bicycleModel) {
		this.bicycleModel = bicycleModel;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getBicycleNo() {
		return bicycleNo;
	}

	public void setBicycleNo(int bicycleNo) {
		this.bicycleNo = bicycleNo;
	}

	public int getBicycleType() {
		return bicycleType;
	}

	public void setBicycleType(int bicycleType) {
		this.bicycleType = bicycleType;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

}
