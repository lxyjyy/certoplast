package com.lab.certoplast.bean;

/**
 * Created by lxyjyy on 17/11/23.
 */

public class Fahuo1Order2 extends Base {


    private String fahuo_id;
    private String baozhuang_bili;
    private String baozhuang_froms;
    private String Product_ID;


    public String getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(String product_ID) {
        Product_ID = product_ID;
    }

    public String getBaozhuang_bili() {
        return baozhuang_bili;
    }

    public void setBaozhuang_bili(String baozhuang_bili) {
        this.baozhuang_bili = baozhuang_bili;
    }

    public String getBaozhuang_froms() {
        return baozhuang_froms;
    }

    public void setBaozhuang_froms(String baozhuang_froms) {
        this.baozhuang_froms = baozhuang_froms;
    }

    public String getFahuo_id() {
        return fahuo_id;
    }

    public void setFahuo_id(String fahuo_id) {
        this.fahuo_id = fahuo_id;
    }
}
