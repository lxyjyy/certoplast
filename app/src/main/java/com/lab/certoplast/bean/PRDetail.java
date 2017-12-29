package com.lab.certoplast.bean;

/**
 * Created by lxyjyy on 17/11/25.
 *
 * 生产领用详情
 */

public class PRDetail extends Base {

    private String ID;
    private String Product_ID;
    private String time;
    private String sc_shuliang;
    private String scyl_ID;
    private String gongwei;
    private String shenqingren;
    private String status;
    private String product_pid;
    private String Froms;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(String product_ID) {
        Product_ID = product_ID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSc_shuliang() {
        return sc_shuliang;
    }

    public void setSc_shuliang(String sc_shuliang) {
        this.sc_shuliang = sc_shuliang;
    }

    public String getScyl_ID() {
        return scyl_ID;
    }

    public void setScyl_ID(String scyl_ID) {
        this.scyl_ID = scyl_ID;
    }

    public String getGongwei() {
        return gongwei;
    }

    public void setGongwei(String gongwei) {
        this.gongwei = gongwei;
    }

    public String getShenqingren() {
        return shenqingren;
    }

    public void setShenqingren(String shenqingren) {
        this.shenqingren = shenqingren;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProduct_pid() {
        return product_pid;
    }

    public void setProduct_pid(String product_pid) {
        this.product_pid = product_pid;
    }

    public String getFroms() {
        return Froms;
    }

    public void setFroms(String froms) {
        Froms = froms;
    }
}
