package com.fagawee.netwindow;

import java.io.Serializable;

/**
 * Created by Mr.Tian on 2020/1/7.
 */

public class LogWindowEntity implements Serializable{
    public String interfacename="";
    public String time="";
    public String request;
    public String result;
    public long id;

    public LogWindowEntity setInterfacename(String interfacename) {
        this.interfacename = interfacename;
        return this;
    }

    public LogWindowEntity setTime(String time) {
        this.time = time;
        return this;
    }

    public LogWindowEntity setRequest(String request) {
        this.request = request;
        return this;
    }

    public LogWindowEntity setResult(String result) {
        this.result = result;
        return this;
    }

    public LogWindowEntity setId(long id) {
        this.id = id;
        return this;
    }

    public String getInterfacename() {
        return interfacename;
    }

    public String getTime() {
        return time;
    }

    public String getRequest() {
        return request;
    }

    public String getResult() {
        return result;
    }

    public long getId() {
        return id;
    }
}
