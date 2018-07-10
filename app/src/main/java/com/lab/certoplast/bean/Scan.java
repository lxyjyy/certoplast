package com.lab.certoplast.bean;

/**
 * Created by lxyjyy on 17/11/30.
 *
 */

public class Scan extends Base {

    private String Product_pid;
    private String warehouse_set;
    private String outshuliang;
    private String status;
    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_pid() {
        return Product_pid;
    }

    public void setProduct_pid(String product_pid) {
        Product_pid = product_pid;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
