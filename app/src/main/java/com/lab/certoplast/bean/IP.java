package com.lab.certoplast.bean;

/**
 * Created by lxyjyy on 17/12/5.
 */

public class IP extends Base {

    private String ipAddress;
    private String port;


    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
