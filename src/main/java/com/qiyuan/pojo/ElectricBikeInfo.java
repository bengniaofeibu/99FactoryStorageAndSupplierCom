package com.qiyuan.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="t_electric_bike_info")
public class ElectricBikeInfo extends BasePojo {


    @Id
    @GenericGenerator(name="uuid",strategy="uuid")
    @GeneratedValue(generator="uuid")
    @Column(name = "id", length = 64, nullable = false)
    private String id;

    @Column(name = "bicycle_no", length = 10, nullable = false)
    private int bicycleNo;

    @Column(name = "bicycle_status", length = 2, nullable = false)
    private Integer bicycleStatus;

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

    @Column(name = "suspect_break_status",length = 11)
    private int suspectBreakStatus;

    @Column(name = "ver_software")
    private String verSoftware;

    @Column(name = "terminal_type")
    private String terminalType;

    @Column(name = "ver_hardware")
    private String verHardware;

    @Column(name = "bluetooth_name", length = 64)
    private String bluetoothName;

    @Column(name = "bluetooth_mac", length = 64)
    private String bluetoothMac;

    @Column(name = "city_no", length = 10, columnDefinition = "INT default 0")
    private Integer cityNo;

    @Column(name = "manufacture_id")
    private String manufactureId;

    @Column(name = "lock_series",length = 11)
    private int lockSeries;

    @Column(name = "gprs_no", length = 50)
    private String gprsNo;

    @Column(name = "new_key")
    private String newKey;

    @Column(name = "new_password")
    private String newPassword;

    @Column(name = "battery_status",length = 11)
    private Integer batteryStatus;

    @Column(name = "binding_status",length = 11)
    private Integer bindingStatus;

    @Column(name = "break_status",length = 11)
    private Integer breakStatus;

    @Column(name = "charge_status",length = 11)
    private Integer chargeStatus;

    @Column(name = "corpse_status",length = 11)
    private Integer corpseStatus;

    @Column(name = "fence_status",length = 11)
    private Integer fenceStatus;

    @Column(name = "recall_status",length = 11)
    private Integer recallStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_charge_time")
    private Date lastChargeTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_return_time")
    private Date lastReturnTime;

    @Column(name = "online_status",length = 11)
    private Integer onlineStatus;

    @Column(name = "ride_status",length = 11)
    private Integer rideStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_used_time")
    private Date lastUsedTime;

    @Column(name="fence_id")
    private String fenceId;

    @Column(name="shutdown_status")
    private Integer shutdownStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "shutdown_time")
    private Date shutdownTime;

    @Column(name = "activity_status",length = 2, columnDefinition = "INT default 0")
    private Integer activityStatus;

    public ElectricBikeInfo() {
    }

    public ElectricBikeInfo(int bicycleNo, Integer bicycleStatus, String currentLongitude, String currentLatitude, Date addTime, String simNo, int suspectBreakStatus, String verSoftware, String terminalType, String verHardware, String bluetoothName, String bluetoothMac, Integer cityNo, String manufactureId, int lockSeries, String gprsNo, String newKey, String newPassword, Integer batteryStatus, Integer bindingStatus, Integer breakStatus, Integer chargeStatus, Integer corpseStatus, Integer fenceStatus, Integer recallStatus, Date lastChargeTime, Date lastReturnTime, Integer onlineStatus, Integer rideStatus, Date lastUsedTime, String fenceId, Integer shutdownStatus, Date shutdownTime, Integer activityStatus) {
        this.bicycleNo = bicycleNo;
        this.bicycleStatus = bicycleStatus;
        this.currentLongitude = currentLongitude;
        this.currentLatitude = currentLatitude;
        this.addTime = addTime;
        this.simNo = simNo;
        this.suspectBreakStatus = suspectBreakStatus;
        this.verSoftware = verSoftware;
        this.terminalType = terminalType;
        this.verHardware = verHardware;
        this.bluetoothName = bluetoothName;
        this.bluetoothMac = bluetoothMac;
        this.cityNo = cityNo;
        this.manufactureId = manufactureId;
        this.lockSeries = lockSeries;
        this.gprsNo = gprsNo;
        this.newKey = newKey;
        this.newPassword = newPassword;
        this.batteryStatus = batteryStatus;
        this.bindingStatus = bindingStatus;
        this.breakStatus = breakStatus;
        this.chargeStatus = chargeStatus;
        this.corpseStatus = corpseStatus;
        this.fenceStatus = fenceStatus;
        this.recallStatus = recallStatus;
        this.lastChargeTime = lastChargeTime;
        this.lastReturnTime = lastReturnTime;
        this.onlineStatus = onlineStatus;
        this.rideStatus = rideStatus;
        this.lastUsedTime = lastUsedTime;
        this.fenceId = fenceId;
        this.shutdownStatus = shutdownStatus;
        this.shutdownTime = shutdownTime;
        this.activityStatus = activityStatus;
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

    public Integer getBicycleStatus() {
        return bicycleStatus;
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

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public int getSuspectBreakStatus() {
        return suspectBreakStatus;
    }

    public void setSuspectBreakStatus(int suspectBreakStatus) {
        this.suspectBreakStatus = suspectBreakStatus;
    }

    public String getVerSoftware() {
        return verSoftware;
    }

    public void setVerSoftware(String verSoftware) {
        this.verSoftware = verSoftware;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getVerHardware() {
        return verHardware;
    }

    public void setVerHardware(String verHardware) {
        this.verHardware = verHardware;
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

    public String getManufactureId() {
        return manufactureId;
    }

    public void setManufactureId(String manufactureId) {
        this.manufactureId = manufactureId;
    }

    public int getLockSeries() {
        return lockSeries;
    }

    public void setLockSeries(int lockSeries) {
        this.lockSeries = lockSeries;
    }

    public String getGprsNo() {
        return gprsNo;
    }

    public void setGprsNo(String gprsNo) {
        this.gprsNo = gprsNo;
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

    public Integer getCorpseStatus() {
        return corpseStatus;
    }

    public void setCorpseStatus(Integer corpseStatus) {
        this.corpseStatus = corpseStatus;
    }

    public Integer getFenceStatus() {
        return fenceStatus;
    }

    public void setFenceStatus(Integer fenceStatus) {
        this.fenceStatus = fenceStatus;
    }

    public Integer getRecallStatus() {
        return recallStatus;
    }

    public void setRecallStatus(Integer recallStatus) {
        this.recallStatus = recallStatus;
    }

    public Date getLastChargeTime() {
        return lastChargeTime;
    }

    public void setLastChargeTime(Date lastChargeTime) {
        this.lastChargeTime = lastChargeTime;
    }

    public Date getLastReturnTime() {
        return lastReturnTime;
    }

    public void setLastReturnTime(Date lastReturnTime) {
        this.lastReturnTime = lastReturnTime;
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

    public Date getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(Date lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public String getFenceId() {
        return fenceId;
    }

    public void setFenceId(String fenceId) {
        this.fenceId = fenceId;
    }

    public Integer getShutdownStatus() {
        return shutdownStatus;
    }

    public void setShutdownStatus(Integer shutdownStatus) {
        this.shutdownStatus = shutdownStatus;
    }

    public Date getShutdownTime() {
        return shutdownTime;
    }

    public void setShutdownTime(Date shutdownTime) {
        this.shutdownTime = shutdownTime;
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }
}
