package com.lab.certoplast.bean;

/**
 * Created by lxyjyy on 18/1/17.
 */

public class ScanList extends Base {

    private String pid;
    private String warehoust_set;
    private String outbox;
    private String unit;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getWarehoust_set() {
        return warehoust_set;
    }

    public void setWarehoust_set(String warehoust_set) {
        this.warehoust_set = warehoust_set;
    }

    public String getOutbox() {
        return outbox;
    }

    public void setOutbox(String outbox) {
        this.outbox = outbox;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
