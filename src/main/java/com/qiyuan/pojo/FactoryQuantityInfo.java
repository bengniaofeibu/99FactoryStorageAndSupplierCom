package com.qiyuan.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "t_factory_quantity_info")
public class FactoryQuantityInfo extends BasePojo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7685225558813970995L;
	
	@Id
	@GenericGenerator(name="uuid",strategy="uuid")
	@GeneratedValue(generator="uuid")
	@Column(name = "id", length = 64, nullable = false)
	private String id;
	
	//供应商id
	@Column(name = "supplier_id", length = 64)
	private String supplierId;
	
	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;
	
	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "add_time")
	private Date addTime;
	
	@Column(name = "bicycle_quantity", length = 10)
	private Integer bicycleQuantity;
	
	@Column(name = "electric_bicycle_quantity", length = 10)
	private Integer electricBicycleQuantity;
			
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getBicycleQuantity() {
		return bicycleQuantity;
	}

	public void setBicycleQuantity(Integer bicycleQuantity) {
		this.bicycleQuantity = bicycleQuantity;
	}

	public Integer getElectricBicycleQuantity() {
		return electricBicycleQuantity;
	}

	public void setElectricBicycleQuantity(Integer electricBicycleQuantity) {
		this.electricBicycleQuantity = electricBicycleQuantity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
