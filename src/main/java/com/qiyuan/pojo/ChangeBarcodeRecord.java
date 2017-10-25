package com.qiyuan.pojo;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_change_barcode_record")
public class ChangeBarcodeRecord implements Serializable{

    @Id
    @GenericGenerator(name="uuid",strategy="uuid")
    @GeneratedValue(generator="uuid")
    @Column(name = "id", length = 64, nullable = false)
    private String id;

    @Column(name = "sim_no")
    private String simNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "add_time")
    private Date addTime;

    @Column(name = "bicycle_no")
    private String bicycleNo;

    @Column(name = "changed_by")
    private String changedBy;

    @Column(name="pre_bicycle_no")
    private String preBicycleNo;

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

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getBicycleNo() {
        return bicycleNo;
    }

    public void setBicycleNo(String bicycleNo) {
        this.bicycleNo = bicycleNo;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public String getPreBicycleNo() {
        return preBicycleNo;
    }

    public void setPreBicycleNo(String preBicycleNo) {
        this.preBicycleNo = preBicycleNo;
    }
}
