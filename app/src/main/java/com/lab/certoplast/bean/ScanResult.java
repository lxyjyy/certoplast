package com.lab.certoplast.bean;

import java.util.List;

/**
 * Created by lxyjyy on 18/1/1.
 */

public class ScanResult extends Base {

    private String sl;
    private List<Scan> msg;

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public List<Scan> getMsg() {
        return msg;
    }

    public void setMsg(List<Scan> msg) {
        this.msg = msg;
    }
}
