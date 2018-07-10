package com.lab.certoplast.bean;

/**
 * Created by lxyjyy on 17/12/10.
 */

public class SaoMiao extends Base {

    private String product_id;
    private String outshulinag;

    private String baozhuang_bili;
    private String saomiao_ok;

    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOutshulinag() {
        return outshulinag;
    }

    public void setOutshulinag(String outshulinag) {
        this.outshulinag = outshulinag;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getBaozhuang_bili() {
        return baozhuang_bili;
    }

    public void setBaozhuang_bili(String baozhuang_bili) {
        this.baozhuang_bili = baozhuang_bili;
    }

    public String getSaomiao_ok() {
        return saomiao_ok;
    }

    public void setSaomiao_ok(String saomiao_ok) {
        this.saomiao_ok = saomiao_ok;
    }
}
