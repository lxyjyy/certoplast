package com.lab.certoplast.bean;

/**
 * Created by lxyjyy on 17/11/23.\
 *
 * 发货
 */

public class Shipping extends Base {

    private String saomiao_ok;

    private String fahuo_id;


    public String getSaomiao_ok() {
        return saomiao_ok;
    }

    public void setSaomiao_ok(String saomiao_ok) {
        this.saomiao_ok = saomiao_ok;
    }

    public String getFahuo_id() {
        return fahuo_id;
    }

    public void setFahuo_id(String fahuo_id) {
        this.fahuo_id = fahuo_id;
    }
}
