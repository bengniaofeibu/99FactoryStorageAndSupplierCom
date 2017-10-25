package com.qiyuan.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="t_lock_terminal_info_temp")
public class LockTerminalInfoTemp implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3760598885875054463L;

    @Id
    @GenericGenerator(name="uuid",strategy="uuid")
    @GeneratedValue(generator="uuid")
    @Column(name = "id", length = 64, nullable = false)
    private String id;

    @Column(name = "sim_no")
    private String simNo;

    //软件版本
    @Column(name = "ver_software")
    private String verSoftware;

    //硬件版本
    @Column(name = "ver_hardware")
    private String verHardware;

    //终端型号
    @Column(name = "terminal_type")
    private String terminalType;

    //生产厂家
    @Column(name = "make_factory")
    private String makeFactory;

    //生产批次
    @Column(name = "make_no")
    private String makeNo;

    //安装工
    @Column(name = "waitor")
    private String waitor;

    @Column(name = "install_address")
    private String installAddress;

    //添加时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "add_time")
    private Date addTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "bluetooth_mac", length = 64)
    private String bluetoothMac;

    @Column(name = "gprs_no", length = 50)
    private String gprsNo;

    @Column(name = "new_key")
    private String newKey;

    @Column(name = "new_password")
    private String newPassword;

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

    public String getBluetoothMac() {
        return bluetoothMac;
    }

    public void setBluetoothMac(String bluetoothMac) {
        this.bluetoothMac = bluetoothMac;
    }

    public String getGprsNo() {
        return gprsNo;
    }

    public void setGprsNo(String gprsNo) {
        this.gprsNo = gprsNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public String getVerSoftware() {
        return verSoftware;
    }

    public void setVerSoftware(String verSoftware) {
        this.verSoftware = verSoftware;
    }

    public String getVerHardware() {
        return verHardware;
    }

    public void setVerHardware(String verHardware) {
        this.verHardware = verHardware;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getMakeFactory() {
        return makeFactory;
    }

    public void setMakeFactory(String makeFactory) {
        this.makeFactory = makeFactory;
    }

    public String getMakeNo() {
        return makeNo;
    }

    public void setMakeNo(String makeNo) {
        this.makeNo = makeNo;
    }

    public String getWaitor() {
        return waitor;
    }

    public void setWaitor(String waitor) {
        this.waitor = waitor;
    }

    public String getInstallAddress() {
        return installAddress;
    }

    public void setInstallAddress(String installAddress) {
        this.installAddress = installAddress;
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

}

