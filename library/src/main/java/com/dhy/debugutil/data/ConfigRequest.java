package com.dhy.debugutil.data;

import java.io.Serializable;

public class ConfigRequest implements Serializable {
    private String applicationId;
    private String token;
    private String sqlId;
    private Object data;

    public <DATA extends Serializable> ConfigRequest(String applicationId, String token, String sqlId, DATA data) {
        this.applicationId = applicationId;
        this.token = token;
        this.sqlId = sqlId;
        this.data = data;
    }
}
