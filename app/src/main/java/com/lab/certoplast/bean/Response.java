package com.lab.certoplast.bean;

/**
 *"response": "success",
 "info":"success"
 "msg": "返回内容"

 */

public class Response<T> extends Base {

    private String response;
    private String info;
    private T msg;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
