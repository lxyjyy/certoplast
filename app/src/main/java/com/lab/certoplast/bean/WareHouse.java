package com.lab.certoplast.bean;

/**
 * Created by lxyjyy on 17/11/17.
 *
 * 货位代码
 */

public class WareHouse extends Base {

    private String WareHouse_ID;
    private String WareHouse_Set;

    private String Product_ID;
    private String shuliang;
    private String ruku_ID;
    private String caigou_ID;

    private String Product_PID;
    private String scjh_ID;
    private String status;


    public String getProduct_PID() {
        return Product_PID;
    }

    public void setProduct_PID(String product_PID) {
        Product_PID = product_PID;
    }

    public String getScjh_ID() {
        return scjh_ID;
    }

    public void setScjh_ID(String scjh_ID) {
        this.scjh_ID = scjh_ID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 标志位
     */
    private boolean flag = false;


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getWareHouse_ID() {
        return WareHouse_ID;
    }

    public void setWareHouse_ID(String wareHouse_ID) {
        WareHouse_ID = wareHouse_ID;
    }

    public String getWareHouse_Set() {
        return WareHouse_Set;
    }

    public void setWareHouse_Set(String wareHouse_Set) {
        WareHouse_Set = wareHouse_Set;
    }

    public String getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(String product_ID) {
        Product_ID = product_ID;
    }

    public String getShuliang() {
        return shuliang;
    }

    public void setShuliang(String shuliang) {
        this.shuliang = shuliang;
    }

    public String getRuku_ID() {
        return ruku_ID;
    }

    public void setRuku_ID(String ruku_ID) {
        this.ruku_ID = ruku_ID;
    }

    public String getCaigou_ID() {
        return caigou_ID;
    }

    public void setCaigou_ID(String caigou_ID) {
        this.caigou_ID = caigou_ID;
    }
}
