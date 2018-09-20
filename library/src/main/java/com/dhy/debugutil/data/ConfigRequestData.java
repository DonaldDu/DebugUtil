package com.dhy.debugutil.data;

import java.io.Serializable;

public class ConfigRequestData implements Serializable {
    private String applicationId;

    public ConfigRequestData(String applicationId) {
        this.applicationId = applicationId;
    }
}
