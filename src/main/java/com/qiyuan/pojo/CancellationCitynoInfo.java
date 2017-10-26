package com.qiyuan.pojo;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="t_cancellation_cityno_info")
public class CancellationCitynoInfo extends  BasePojo{


      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      @Column(name = "id", length = 11, nullable = false)
      private  int id;

      /** 地址 **/
      @Column(name="address")
      private  String address;

      /** 车辆序列号 **/
      @Column(name = "bicycle_no")
      private  String bicycleNo;

      /**  **/
      @Column(name = "canceled_by")
      private  String canceled_by;

      /** 城市标识 **/
      @Column(name = "pre_city_no")
      private int preCityNo;

      /** sim 号 **/
      @Column(name="sim_no")
      private  String simNo;

      /**  bluetooth_mac 地址 **/
      @Column(name = "bluetooth_mac")
      private  String bluetoothMac;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBicycleNo() {
        return bicycleNo;
    }

    public void setBicycleNo(String bicycleNo) {
        this.bicycleNo = bicycleNo;
    }

    public String getCanceled_by() {
        return canceled_by;
    }

    public void setCanceled_by(String canceled_by) {
        this.canceled_by = canceled_by;
    }

    public int getPreCityNo() {
        return preCityNo;
    }

    public void setPreCityNo(int preCityNo) {
        this.preCityNo = preCityNo;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public String getBluetoothMac() {
        return bluetoothMac;
    }

    public void setBluetoothMac(String bluetoothMac) {
        this.bluetoothMac = bluetoothMac;
    }
}
