package com.dhy.debugutil.data;

import java.io.Serializable;

public class TestUser implements Serializable {
    public String name;
    public String remark;

    public TestUser() {

    }

    public TestUser(String remark, String name) {
        this.remark = remark;
        this.name = name;
    }

    @Override
    public String toString() {
        return remark + "\n" + name;
    }
}
