package com.qiyuan.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_bike_update_info")
public class BikeUpdateInfo extends  BasePojo {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 20, nullable = false)
    private Long id;

    @Column(name = "bicycle_no_old",nullable = false)
    private  String bicycleNoOld;

    @Column(name = "bicycle_no_new",nullable = false)
    private String bicycleNoNew;

    @Column(name = "bluetooth_mac_old",nullable = false)
    private  String bluetoothMcOld;

    @Column(name = "bluetooth_mac_new",nullable = false)
    private String bluetoothMacNew;

    @Column(name = "new_key_old",nullable = false)
    private String newKeyOld;

    @Column(name = "new_key_new",nullable = false)
    private String newKeyNew;

    @Column(name = "new_password_old",nullable = false)
    private String newPasswordOld;

    @Column(name = "new_password_new",nullable = false)
    private String newPasswordNew;

    @Column(name = "sim_no",nullable = false)
    private String simNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "add_time_old",nullable = false)
    private Date add_time_old;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "add_time_new",nullable = false)
    private Date addTimeNew;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time",nullable = false)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time",nullable = false)
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBicycleNoOld() {
        return bicycleNoOld;
    }

    public void setBicycleNoOld(String bicycleNoOld) {
        this.bicycleNoOld = bicycleNoOld;
    }

    public String getBicycleNoNew() {
        return bicycleNoNew;
    }

    public void setBicycleNoNew(String bicycleNoNew) {
        this.bicycleNoNew = bicycleNoNew;
    }

    public String getBluetoothMcOld() {
        return bluetoothMcOld;
    }

    public void setBluetoothMcOld(String bluetoothMcOld) {
        this.bluetoothMcOld = bluetoothMcOld;
    }

    public String getBluetoothMacNew() {
        return bluetoothMacNew;
    }

    public void setBluetoothMacNew(String bluetoothMacNew) {
        this.bluetoothMacNew = bluetoothMacNew;
    }

    public String getNewKeyOld() {
        return newKeyOld;
    }

    public void setNewKeyOld(String newKeyOld) {
        this.newKeyOld = newKeyOld;
    }

    public String getNewKeyNew() {
        return newKeyNew;
    }

    public void setNewKeyNew(String newKeyNew) {
        this.newKeyNew = newKeyNew;
    }

    public String getNewPasswordOld() {
        return newPasswordOld;
    }

    public void setNewPasswordOld(String newPasswordOld) {
        this.newPasswordOld = newPasswordOld;
    }

    public String getNewPasswordNew() {
        return newPasswordNew;
    }

    public void setNewPasswordNew(String newPasswordNew) {
        this.newPasswordNew = newPasswordNew;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public Date getAdd_time_old() {
        return add_time_old;
    }

    public void setAdd_time_old(Date add_time_old) {
        this.add_time_old = add_time_old;
    }

    public Date getAddTimeNew() {
        return addTimeNew;
    }

    public void setAddTimeNew(Date addTimeNew) {
        this.addTimeNew = addTimeNew;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "BikeUpdateInfo{" +
                "id=" + id +
                ", bicycleNoOld='" + bicycleNoOld + '\'' +
                ", bicycleNoNew='" + bicycleNoNew + '\'' +
                ", bluetoothMcOld='" + bluetoothMcOld + '\'' +
                ", bluetoothMacNew='" + bluetoothMacNew + '\'' +
                ", newKeyOld='" + newKeyOld + '\'' +
                ", newKeyNew='" + newKeyNew + '\'' +
                ", newPasswordOld='" + newPasswordOld + '\'' +
                ", newPasswordNew='" + newPasswordNew + '\'' +
                ", simNo='" + simNo + '\'' +
                ", add_time_old=" + add_time_old +
                ", addTimeNew=" + addTimeNew +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
