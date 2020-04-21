package com.dhy.debugutil.data;

import java.io.Serializable;

public class LCResponse implements Serializable {
    private int code;
    public String error;

    public boolean isSuccess() {
        return code == 0;
    }
}
