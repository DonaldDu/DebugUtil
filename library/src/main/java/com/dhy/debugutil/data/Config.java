package com.dhy.debugutil.data;

import java.io.Serializable;

public class Config implements Serializable {
    public final String name;
    public final String value;

    public Config(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
