package com.fagawee.netwindow;

/**
 * Created by Mr.Tian on 2020/1/7.
 */

public class ResponseMessage {
    public String interfacename="";
    public String time="";
    public String result;
    public long id;

    public String getInterfacename() {
        return interfacename;
    }

    public ResponseMessage setInterfacename(String interfacename) {
        this.interfacename = interfacename;
        return this;
    }

    public String getTime() {
        return time;
    }

    public ResponseMessage setTime(String time) {
        this.time = time;
        return this;
    }

    public String getResult() {
        return result;
    }

    public ResponseMessage setResult(String result) {
        this.result = result;
        return this;
    }

    public long getId() {
        return id;
    }

    public ResponseMessage setId(long id) {
        this.id = id;
        return this;
    }
}
