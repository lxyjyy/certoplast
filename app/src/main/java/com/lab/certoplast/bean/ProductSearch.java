package com.lab.certoplast.bean;

/**
 * Created by lxyjyy on 17/12/10.
 */

public class ProductSearch extends Base {

    private String Type;
    private String baozhuang_bili;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getBaozhuang_bili() {
        return baozhuang_bili;
    }

    public void setBaozhuang_bili(String baozhuang_bili) {
        this.baozhuang_bili = baozhuang_bili;
    }
}
