package com.lab.certoplast.bean;

/**
 * Created by lxyjyy on 18/1/3.
 */

public class FullProDetail2 extends Base {

    private String warehost;
    private String state;
    private String remain;

    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getWarehost() {
        return warehost;
    }

    public void setWarehost(String warehost) {
        this.warehost = warehost;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }
}
