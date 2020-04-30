package com.dhy.debugutil.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestConfigSetting implements Serializable, IConfig {
    public final Map<String, List<RemoteConfig>> datas = new HashMap<>();
}
