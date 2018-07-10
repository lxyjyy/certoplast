package com.lab.certoplast.bean;

/**
 * Created by lxyjyy on 17/11/16.
 *
 * 发货收货实体
 */

public class Migo extends Base {

    private String caigou_id;
    private String ruku_id;

    private String remain;

    private String id;
    private String product;
    private String ruku_shuliang;
    private String warehouse;
    private String type;
    private String status_class;
    private String pid;

    private String classID;


    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getRuku_shuliang() {
        return ruku_shuliang;
    }

    public void setRuku_shuliang(String ruku_shuliang) {
        this.ruku_shuliang = ruku_shuliang;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus_class() {
        return status_class;
    }

    public void setStatus_class(String status_class) {
        this.status_class = status_class;
    }

    public String getCaigou_id() {
        return caigou_id;
    }

    public void setCaigou_id(String caigou_id) {
        this.caigou_id = caigou_id;
    }

    public String getRuku_id() {
        return ruku_id;
    }

    public void setRuku_id(String ruku_id) {
        this.ruku_id = ruku_id;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }
}
