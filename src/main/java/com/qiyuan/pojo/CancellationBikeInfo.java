package com.qiyuan.pojo;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.qiyuan.utils.CustomJsonDateDeserializer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="t_cancellation_bike_info")
public class CancellationBikeInfo {
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

    @Column(name = "admin_id",length = 64)
    private String adminId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "delete_date")
    private Date deleteDate;
    /*
    注销车辆恢复置位符，1：已恢复，0：未恢复（默认0）
     */
    @Column(name = "restore_flag",length = 2)
    private int restoreFlag;

    public CancellationBikeInfo(int bicycleNo, String newKey, String newPassword) {
        this.bicycleNo = bicycleNo;
        this.newKey = newKey;
        this.newPassword = newPassword;
    }

    public Integer getRecallStatus() {
        return recallStatus;
    }

    public void setRecallStatus(Integer recallStatus) {
        this.recallStatus = recallStatus;
    }

    public CancellationBikeInfo() {
    }

    public int getRestoreFlag() {
        return restoreFlag;
    }

    public void setRestoreFlag(int restoreFlag) {
        this.restoreFlag = restoreFlag;
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Integer getShutdownStatus() {
        return shutdownStatus;
    }

    public void setShutdownStatus(Integer shutdownStatus) {
        this.shutdownStatus = shutdownStatus;
    }

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public Date getShutdownTime() {
        return shutdownTime;
    }

    public void setShutdownTime(Date shutdownTime) {
        this.shutdownTime = shutdownTime;
    }

    public Date getLastUsedTime() {
        return lastUsedTime;
    }

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setLastUsedTime(Date lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public String getFenceId() {
        return fenceId;
    }

    public void setFenceId(String fenceId) {
        this.fenceId = fenceId;
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

    public Date getLastChargeTime() {
        return lastChargeTime;
    }

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setLastChargeTime(Date lastChargeTime) {
        this.lastChargeTime = lastChargeTime;
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

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
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

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setLastConnectTime(Date lastConnectTime) {
        this.lastConnectTime = lastConnectTime;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }
}
