package com.lab.certoplast.bean;

/**
 * Created by lxyjyy on 17/11/30.
 *
 */

public class Scan extends Base {

    private String scyl_id;
    private String product_id;
    private String product_pid;
    private String warehouse_set;
    private String outshuliang;
    private String intime;
    private String username;
    private String gongwei;

    public String getScyl_id() {
        return scyl_id;
    }

    public void setScyl_id(String scyl_id) {
        this.scyl_id = scyl_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_pid() {
        return product_pid;
    }

    public void setProduct_pid(String product_pid) {
        this.product_pid = product_pid;
    }

    public String getWarehouse_set() {
        return warehouse_set;
    }

    public void setWarehouse_set(String warehouse_set) {
        this.warehouse_set = warehouse_set;
    }

    public String getOutshuliang() {
        return outshuliang;
    }

    public void setOutshuliang(String outshuliang) {
        this.outshuliang = outshuliang;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGongwei() {
        return gongwei;
    }

    public void setGongwei(String gongwei) {
        this.gongwei = gongwei;
    }
}
