package com.dhy.debugutil.data;

import java.io.Serializable;

public class TestServer implements Serializable {
    public String server;
    public String remark;

    @Override
    public String toString() {
        return remark + "\n" + server;
    }

    public TestServer() {

    }

    public TestServer(String remark, String server) {
        this.remark = remark;
        this.server = server;
    }
}
