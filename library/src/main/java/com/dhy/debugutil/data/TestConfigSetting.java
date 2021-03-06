package com.dhy.debugutil.data;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Keep
public class TestConfigSetting implements Serializable {
    public final Map<String, List<RemoteConfig>> datas = new HashMap<>();
}
