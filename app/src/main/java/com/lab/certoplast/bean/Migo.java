package com.lab.certoplast.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lxyjyy on 17/11/16.
 *
 * 发货收货实体
 */

public class Migo extends Base {

    private String caigou_ID;
    private String ruku_ID;

    private String ID;
    private String Product;
    private String caigou_Shuliang;
    private String Time;
    private String Status;
    private String user_name;
    private String Product_PID;
    private String scyl_ID;
    private String ordert_ID;
    private String lx_id;

    @SerializedName("Class")
    private String type;
    private String ruku_Shuliang;
    private String warehouse;
    private String Status_Class;

    @SerializedName("warehouse_class.ID")
    private String classId;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRuku_Shuliang() {
        return ruku_Shuliang;
    }

    public void setRuku_Shuliang(String ruku_Shuliang) {
        this.ruku_Shuliang = ruku_Shuliang;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getStatus_Class() {
        return Status_Class;
    }

    public void setStatus_Class(String status_Class) {
        Status_Class = status_Class;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getCaigou_Shuliang() {
        return caigou_Shuliang;
    }

    public void setCaigou_Shuliang(String caigou_Shuliang) {
        this.caigou_Shuliang = caigou_Shuliang;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getProduct_PID() {
        return Product_PID;
    }

    public void setProduct_PID(String product_PID) {
        Product_PID = product_PID;
    }

    public String getScyl_ID() {
        return scyl_ID;
    }

    public void setScyl_ID(String scyl_ID) {
        this.scyl_ID = scyl_ID;
    }

    public String getOrdert_ID() {
        return ordert_ID;
    }

    public void setOrdert_ID(String ordert_ID) {
        this.ordert_ID = ordert_ID;
    }

    public String getLx_id() {
        return lx_id;
    }

    public void setLx_id(String lx_id) {
        this.lx_id = lx_id;
    }

    public String getCaigou_ID() {
        return caigou_ID;
    }

    public void setCaigou_ID(String caigou_ID) {
        this.caigou_ID = caigou_ID;
    }

    public String getRuku_ID() {
        return ruku_ID;
    }

    public void setRuku_ID(String ruku_ID) {
        this.ruku_ID = ruku_ID;
    }
}
