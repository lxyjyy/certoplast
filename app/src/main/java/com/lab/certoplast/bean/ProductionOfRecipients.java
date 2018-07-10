package com.lab.certoplast.bean;

/**
 * Created by lxyjyy on 17/11/12.
 *
 *
 * 生产领用对象
 */

public class ProductionOfRecipients extends Base {

    //领用单号
    private String singleno;
    //计划号
    private String product_id;

    public String getSingleno() {
        return singleno;
    }

    public void setSingleno(String singleno) {
        this.singleno = singleno;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
