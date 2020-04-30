package com.dhy.debugutil.demo;

import com.dhy.debugutil.TestConfig;
import com.dhy.debugutil.data.IConfig;

public class Config implements IConfig {
    @TestConfig(name = "user", value = "http://test.user.com")
    public static final String USER_URL = "http://www.user.com";

    @TestConfig(name = "bz", value = "http://test.bz.com")
    public static final String BZ_URL = "http://www.bz.com";
}
