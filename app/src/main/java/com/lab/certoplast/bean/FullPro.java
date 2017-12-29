package com.lab.certoplast.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lxyjyy on 17/11/30.
 */

public class FullPro extends Base {

    private String ID;
    private String Name;
    private String warehouse;
    @SerializedName("Class")
    private String type;
    private String Status_Class;
    private String Froms;
    private String Product_ID;
    //类别
    @SerializedName("类别")
    private String category;
    @SerializedName("生产计划号")
    private String product_plan_id;
    @SerializedName("材料代码")
    private String material_code;
    @SerializedName("数量")
    private String amount;
    @SerializedName("日期")
    private String date;
    @SerializedName("状态")
    private String status;

    @SerializedName("scjhitem.User_Name")
    private String user_name;
    private String Product_PID;
    private String gongwei;
    private String caozuogong;
    private String rk_time;
    private String qrusername;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus_Class() {
        return Status_Class;
    }

    public void setStatus_Class(String status_Class) {
        Status_Class = status_Class;
    }

    public String getFroms() {
        return Froms;
    }

    public void setFroms(String froms) {
        Froms = froms;
    }

    public String getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(String product_ID) {
        Product_ID = product_ID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProduct_plan_id() {
        return product_plan_id;
    }

    public void setProduct_plan_id(String product_plan_id) {
        this.product_plan_id = product_plan_id;
    }

    public String getMaterial_code() {
        return material_code;
    }

    public void setMaterial_code(String material_code) {
        this.material_code = material_code;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getProduct_PID() {
        return Product_PID;
    }

    public void setProduct_PID(String product_PID) {
        Product_PID = product_PID;
    }

    public String getGongwei() {
        return gongwei;
    }

    public void setGongwei(String gongwei) {
        this.gongwei = gongwei;
    }

    public String getCaozuogong() {
        return caozuogong;
    }

    public void setCaozuogong(String caozuogong) {
        this.caozuogong = caozuogong;
    }

    public String getRk_time() {
        return rk_time;
    }

    public void setRk_time(String rk_time) {
        this.rk_time = rk_time;
    }

    public String getQrusername() {
        return qrusername;
    }

    public void setQrusername(String qrusername) {
        this.qrusername = qrusername;
    }
}
