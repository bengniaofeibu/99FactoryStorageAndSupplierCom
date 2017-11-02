package com.qiyuan.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_bike_info")
//@Table(name = "t_electric_bike_info")
public class BikeInfo extends BasePojo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4085024262687597583L;

	@Id 
	@GenericGenerator(name="uuid",strategy="uuid")
	@GeneratedValue(generator="uuid")
	@Column(name = "id", length = 64, nullable = false)
	private String id;
	
	@Column(name = "bicycle_no", length = 10, nullable = false)
	private int bicycleNo;
	
	@Column(name = "bicycle_status", length = 2, nullable = false)
	private Integer bicycleStatus;

	@Column(name="battery_status")
	private  Integer batteryStatus;

	@Column(name="binding_status")
    private  Integer bindingStatus;

	@Column(name = "break_status")
	private Integer  breakStatus;

	@Column(name = "charge_status")
	private  Integer chargeStatus;

	@Column(name = "corpse_status")
	private  Integer corpse_status;

	@Column(name = "fence_status")
	private  Integer fence_status;

	@Column(name = "online_status")
	private Integer onlineStatus;

	@Column(name = "ride_status")
    private  Integer rideStatus;

	@Column(name = "shutdown_status")
	private  Integer shutdownStatus;

	@Column(name = "activity_status")
	private  Integer activityStatus;

	@Column(name = "suspect_break_status")
	private  Integer suspectBreakStatus;

	@Column(name = "recall_status")
	private  Integer recallStatus;

	@Column(name = "current_longitude", length = 50)
	private String currentLongitude;
	
	@Column(name = "current_Latitude", length = 50)
	private String currentLatitude;
	
	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "add_time")
	private Date addTime;
	
	@Column(name = "sim_no")
	private String simNo;
	
	@Column(name = "lock_hard_version", length = 64)
	private String lockHardVersion;
	
	@Column(name = "lock_soft_version", length = 64)
	private String lockSoftVersion;
	
	@Column(name = "bluetooth_name", length = 64)
	private String bluetoothName;
	
	@Column(name = "bluetooth_mac", length = 64)
	private String bluetoothMac;
	
	@Column(name = "city_no", length = 10, columnDefinition = "INT default 0")
	private Integer cityNo;
	
	@Column(name = "bicycle_lock_status", length = 2,nullable = false, columnDefinition = "INT default 0")
	private Integer bicycleLockStatus;
	
	@Column(name = "bicycle_lock_voltage", length = 10)
	private String bicycleLockVoltage;
	
	@Column(name = "handle_flag",length = 2, columnDefinition = "INT default 0")
	private Integer handleFlag;
	
	@Column(name = "update_flag",length = 2, columnDefinition = "INT default 0")
	private Integer updateFlag;
	
	@Column(name = "gprs_no", length = 50)
	private String gprsNo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_connect_time")
	private Date lastConnectTime;
	
	@Column(name = "new_key")
	private String newKey;
	
	@Column(name = "new_password")
	private String newPassword;

	//软件版本
	@Column(name = "ver_software")
	private String verSoftware;

	public String getVerSoftware() {
		return verSoftware;
	}

	public void setVerSoftware(String verSoftware) {
		this.verSoftware = verSoftware;
	}

	public String getNewKey() {
		return newKey;
	}

	public void setNewKey(String newKey) {
		this.newKey = newKey;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
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

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public Integer getBicycleStatus() {
		return bicycleStatus;
	}

	public Integer getBatteryStatus() {
		return batteryStatus;
	}

	public void setBatteryStatus(Integer batteryStatus) {
		this.batteryStatus = batteryStatus;
	}

	public Integer getBindingStatus() {
		return bindingStatus;
	}

	public void setBindingStatus(Integer bindingStatus) {
		this.bindingStatus = bindingStatus;
	}

	public Integer getBreakStatus() {
		return breakStatus;
	}

	public void setBreakStatus(Integer breakStatus) {
		this.breakStatus = breakStatus;
	}

	public Integer getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(Integer chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public Integer getCorpse_status() {
		return corpse_status;
	}

	public void setCorpse_status(Integer corpse_status) {
		this.corpse_status = corpse_status;
	}

	public Integer getFence_status() {
		return fence_status;
	}

	public void setFence_status(Integer fence_status) {
		this.fence_status = fence_status;
	}

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public Integer getRideStatus() {
		return rideStatus;
	}

	public void setRideStatus(Integer rideStatus) {
		this.rideStatus = rideStatus;
	}

	public Integer getShutdownStatus() {
		return shutdownStatus;
	}

	public void setShutdownStatus(Integer shutdownStatus) {
		this.shutdownStatus = shutdownStatus;
	}

	public Integer getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}

	public Integer getSuspectBreakStatus() {
		return suspectBreakStatus;
	}

	public void setSuspectBreakStatus(Integer suspectBreakStatus) {
		this.suspectBreakStatus = suspectBreakStatus;
	}

	public Integer getRecallStatus() {
		return recallStatus;
	}

	public void setRecallStatus(Integer recallStatus) {
		this.recallStatus = recallStatus;
	}

	public void setBicycleStatus(Integer bicycleStatus) {
		this.bicycleStatus = bicycleStatus;
	}

	public String getCurrentLongitude() {
		return currentLongitude;
	}

	public void setCurrentLongitude(String currentLongitude) {
		this.currentLongitude = currentLongitude;
	}

	public String getCurrentLatitude() {
		return currentLatitude;
	}

	public void setCurrentLatitude(String currentLatitude) {
		this.currentLatitude = currentLatitude;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getLockHardVersion() {
		return lockHardVersion;
	}

	public void setLockHardVersion(String lockHardVersion) {
		this.lockHardVersion = lockHardVersion;
	}

	public String getLockSoftVersion() {
		return lockSoftVersion;
	}

	public void setLockSoftVersion(String lockSoftVersion) {
		this.lockSoftVersion = lockSoftVersion;
	}

	public String getBluetoothName() {
		return bluetoothName;
	}

	public void setBluetoothName(String bluetoothName) {
		this.bluetoothName = bluetoothName;
	}

	public String getBluetoothMac() {
		return bluetoothMac;
	}

	public void setBluetoothMac(String bluetoothMac) {
		this.bluetoothMac = bluetoothMac;
	}

	public Integer getCityNo() {
		return cityNo;
	}

	public void setCityNo(Integer cityNo) {
		this.cityNo = cityNo;
	}

	public Integer getBicycleLockStatus() {
		return bicycleLockStatus;
	}

	public void setBicycleLockStatus(Integer bicycleLockStatus) {
		this.bicycleLockStatus = bicycleLockStatus;
	}

	public String getBicycleLockVoltage() {
		return bicycleLockVoltage;
	}

	public void setBicycleLockVoltage(String bicycleLockVoltage) {
		this.bicycleLockVoltage = bicycleLockVoltage;
	}

	public Integer getHandleFlag() {
		return handleFlag;
	}

	public void setHandleFlag(Integer handleFlag) {
		this.handleFlag = handleFlag;
	}

	public Integer getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(Integer updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getGprsNo() {
		return gprsNo;
	}

	public void setGprsNo(String gprsNo) {
		this.gprsNo = gprsNo;
	}

	public Date getLastConnectTime() {
		return lastConnectTime;
	}

	public void setLastConnectTime(Date lastConnectTime) {
		this.lastConnectTime = lastConnectTime;
	}

}
