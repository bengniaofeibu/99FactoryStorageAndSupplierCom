package com.qiyuan.pojo;



import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="t_electric_lock_gps_real_data")
public class ElectricLockGpsRealDataInfo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int iD;

    @Column(name = "sim_no")
    private String simNo;

    @Column(name = "big_battery_level",length = 11)
    private int bigBatteryLevel;

    @Column(name = "big_battery_soft_version",length = 11)
    private int bigBatterySoftVersion;

    @Column(name = "big_battery_no",length = 11)
    private int bigBatteryNo;

    @Column(name = "big_battery_status",length = 11)
    private int bigBatteryStatus;

    @Column(name = "big_battery_temperature",length = 11)
    private String bigBatteryTemperature;

    @Column(name = "big_battery_times")
    private int bigBatteryTimes;

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
    private int alarmState;

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

    //充电信息
    @Column(name="last_charge_time")

    private Date lastChargeTime;

    @Column(name = "is_charging")
    private Integer isCharging;

    public ElectricLockGpsRealDataInfo(String simNo, int bigBatteryLevel, int bigBatterySoftVersion, int bigBatteryNo, int bigBatteryStatus, String bigBatteryTemperature, int bigBatteryTimes, Date addTime, Date updateTime, Date sendTime, double longitude, double latitude, double speed, double altitude, String location, int direction, int status, int alarmState, int signalIntensity, int satelliteNum, int openLockTimes, String lockStatus, int batteryLevel, double batteryVoltage, double chargeVoltage, boolean online, Date lastChargeTime, Integer isCharging) {
        this.simNo = simNo;
        this.bigBatteryLevel = bigBatteryLevel;
        this.bigBatterySoftVersion = bigBatterySoftVersion;
        this.bigBatteryNo = bigBatteryNo;
        this.bigBatteryStatus = bigBatteryStatus;
        this.bigBatteryTemperature = bigBatteryTemperature;
        this.bigBatteryTimes = bigBatteryTimes;
        this.addTime = addTime;
        this.updateTime = updateTime;
        this.sendTime = sendTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.speed = speed;
        this.altitude = altitude;
        this.location = location;
        this.direction = direction;
        this.status = status;
        this.alarmState = alarmState;
        this.signalIntensity = signalIntensity;
        this.satelliteNum = satelliteNum;
        this.openLockTimes = openLockTimes;
        this.lockStatus = lockStatus;
        this.batteryLevel = batteryLevel;
        this.batteryVoltage = batteryVoltage;
        this.chargeVoltage = chargeVoltage;
        this.online = online;
        this.lastChargeTime = lastChargeTime;
        this.isCharging = isCharging;
    }

    public ElectricLockGpsRealDataInfo() {
    }


    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public int getBigBatteryLevel() {
        return bigBatteryLevel;
    }

    public void setBigBatteryLevel(int bigBatteryLevel) {
        this.bigBatteryLevel = bigBatteryLevel;
    }

    public int getBigBatterySoftVersion() {
        return bigBatterySoftVersion;
    }

    public void setBigBatterySoftVersion(int bigBatterySoftVersion) {
        this.bigBatterySoftVersion = bigBatterySoftVersion;
    }

    public int getBigBatteryNo() {
        return bigBatteryNo;
    }

    public void setBigBatteryNo(int bigBatteryNo) {
        this.bigBatteryNo = bigBatteryNo;
    }

    public int getBigBatteryStatus() {
        return bigBatteryStatus;
    }

    public void setBigBatteryStatus(int bigBatteryStatus) {
        this.bigBatteryStatus = bigBatteryStatus;
    }

    public String getBigBatteryTemperature() {
        return bigBatteryTemperature;
    }

    public void setBigBatteryTemperature(String bigBatteryTemperature) {
        this.bigBatteryTemperature = bigBatteryTemperature;
    }

    public int getBigBatteryTimes() {
        return bigBatteryTimes;
    }

    public void setBigBatteryTimes(int bigBatteryTimes) {
        this.bigBatteryTimes = bigBatteryTimes;
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

    public int getAlarmState() {
        return alarmState;
    }

    public void setAlarmState(int alarmState) {
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

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Date getLastChargeTime() {
        return lastChargeTime;
    }

    public void setLastChargeTime(Date lastChargeTime) {
        this.lastChargeTime = lastChargeTime;
    }

    public Integer getIsCharging() {
        return isCharging;
    }

    public void setIsCharging(Integer isCharging) {
        this.isCharging = isCharging;
    }
}
