package com.lab.certoplast.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lxyjyy on 17/11/15.
 *
 * 盘点实体
 *
 *
 * "ID":15771,"WareHouse_ID":2,
 * "WareHouse_Set":"FA244",
 * "Product_ID":"52502525",
 * "shuliang":1512,"Product_PID":"17/09354/22",
 * "ruku_ID":"RK-CP161718","caigou_ID":"","scjh_ID":"",
 * "status":4,"SSMA_TimeStamp":[0,0,0,0,0,1,55,141],
 * "Class":"成品库","Name":"布基胶带",
 * "GuiGe":"25mm*25m/H38/BLACK","Froms":"卷"}
 */

public class Inventory extends Base {

    //库房类型
    @SerializedName("Class")
    private String type;
    //货位ID
    private String WareHouse_Set;
    //数量
    private String shuliang;

    //产品批号
    private String Product_PID;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWareHouse_Set() {
        return WareHouse_Set;
    }

    public void setWareHouse_Set(String wareHouse_Set) {
        WareHouse_Set = wareHouse_Set;
    }

    public String getShuliang() {
        return shuliang;
    }

    public void setShuliang(String shuliang) {
        this.shuliang = shuliang;
    }

    public String getProduct_PID() {
        return Product_PID;
    }

    public void setProduct_PID(String product_PID) {
        Product_PID = product_PID;
    }
}
