package com.dhy.debugutil.data;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public class ConfigResponse<CONTENT extends Serializable> implements Serializable {
    private List<ConfigRecord<CONTENT>> results;

    private static class ConfigRecord<CONFIG> implements Serializable {
        List<CONFIG> json;
        String updatedAt;
    }

    @Nullable
    public List<CONTENT> getConfigs() {
        if (results != null && results.size() == 1) {
            return results.get(0).json;
        }
        return null;
    }
}