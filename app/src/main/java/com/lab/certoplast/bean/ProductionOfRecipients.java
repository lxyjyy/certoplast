package com.lab.certoplast.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lxyjyy on 17/11/12.
 *
 *
 * 生产领用对象
 */

public class ProductionOfRecipients extends Base {


    private String ID;
    @SerializedName("生产计划号")
    private String product_id;
    @SerializedName("订单号")
    private String orderNum;
    private String user_name;
    private String time;

    private String guocheng;
    private String qrusername;


    public String getGuocheng() {
        return guocheng;
    }

    public void setGuocheng(String guocheng) {
        this.guocheng = guocheng;
    }

    public String getQrusername() {
        return qrusername;
    }

    public void setQrusername(String qrusername) {
        this.qrusername = qrusername;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getTime() {
        //sy+time+ID
        String result = null;
        String regex = "\\d*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(time);
        String s = "";
        int count = 0;
        while (m.find())
        {

            s = m.group();

            if (!TextUtils.isEmpty(s) && s.length() > 0){

                String ti = m.group();
                long time1 = Long.parseLong(ti);
                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = new Date(time1);
                String str = sdf.format(date);

                result = "sy" + str.replace("-","") + ID;
            }

        }
        return result;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
