package com.lab.certoplast.bean;

/**
 * Created by lxyjyy on 18/1/1.
 */

public class HalfPro extends Base {

    private String Product_PID;
    private String amount;
    private String froms;
    private String gongwei;
    private String real_box;

    private String product_id;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getReal_box() {
        return real_box;
    }

    public void setReal_box(String real_box) {
        this.real_box = real_box;
    }

    public String getProduct_PID() {
        return Product_PID;
    }

    public void setProduct_PID(String product_PID) {
        Product_PID = product_PID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFroms() {
        return froms;
    }

    public void setFroms(String froms) {
        this.froms = froms;
    }

    public String getGongwei() {
        return gongwei;
    }

    public void setGongwei(String gongwei) {
        this.gongwei = gongwei;
    }
}
