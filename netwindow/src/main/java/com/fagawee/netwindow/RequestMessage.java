package com.fagawee.netwindow;

/**
 * Created by Mr.Tian on 2020/1/7.
 */

public class RequestMessage {
    public String interfacename="";
    public String request;
    public long id;

    public String getInterfacename() {
        return interfacename;
    }

    public RequestMessage setInterfacename(String interfacename) {
        this.interfacename = interfacename;
        return this;
    }

    public String getRequest() {
        return request;
    }

    public RequestMessage setRequest(String request) {
        this.request = request;
        return this;
    }


    public long getId() {
        return id;
    }

    public RequestMessage setId(long id) {
        this.id = id;
        return this;
    }
}
