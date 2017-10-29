package com.qiyuan.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "t_lock_gps_real_Data")
public class LockGPSRealData extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3434409456509530511L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int iD;
	
	@Column(name = "sim_no")
	private String simNo;
	
	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "add_time")
	private Date addTime;
	
	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;
	
	//终端发送时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "send_time")
	private Date sendTime;
	
	//经度
	@Column(name = "longitude")
	private double longitude;
	
	//纬度
	@Column(name = "latitude")
	private double latitude;
	
	//速度
	@Column(name = "speed")
	private double speed;
	
	//海拔
	@Column(name = "altitude")
	private double altitude;
	
	//地理位置信息
	@Column(name = "location")
	private String location;
	
	//方向
	@Column(name = "direction")
	private int direction;
	
	//状态
	@Column(name = "status")
	private int status;
	
	//报警标志
	@Column(name = "alarmState")
	private Integer alarmState;
	
	//信号强度
	@Column(name = "signal_intensity")
	private int signalIntensity;
	
	//卫星数
	@Column(name = "satellite_num")
	private int satelliteNum;
	
	//开锁次数
	@Column(name = "open_lock_times")
	private int openLockTimes;
	
	//锁状态
	@Column(name = "lock_status")
	private String lockStatus;
	
	//电池电量
	@Column(name = "battery_level")
	private int batteryLevel;

	//电池电压
	@Column(name = "battery_voltage")
	private double batteryVoltage;
	
	//充电电压
	@Column(name = "charge_voltage")
	private double chargeVoltage;
	
	// GPS设备在线状态, false代表不在线
	@Column(name = "online")
	private boolean online;

	public final boolean getOnline() {
		return online;
	}

	public final void setOnline(boolean value) {
		online = value;
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getAlarmState() {
		return alarmState;
	}

	public void setAlarmState(Integer alarmState) {
		this.alarmState = alarmState;
	}

	public int getSignalIntensity() {
		return signalIntensity;
	}

	public void setSignalIntensity(int signalIntensity) {
		this.signalIntensity = signalIntensity;
	}

	public int getSatelliteNum() {
		return satelliteNum;
	}

	public void setSatelliteNum(int satelliteNum) {
		this.satelliteNum = satelliteNum;
	}

	public int getOpenLockTimes() {
		return openLockTimes;
	}

	public void setOpenLockTimes(int openLockTimes) {
		this.openLockTimes = openLockTimes;
	}

	public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	public int getBatteryLevel() {
		return batteryLevel;
	}

	public void setBatteryLevel(int batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	public double getBatteryVoltage() {
		return batteryVoltage;
	}

	public void setBatteryVoltage(double batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}

	public double getChargeVoltage() {
		return chargeVoltage;
	}

	public void setChargeVoltage(double chargeVoltage) {
		this.chargeVoltage = chargeVoltage;
	}

	
}
