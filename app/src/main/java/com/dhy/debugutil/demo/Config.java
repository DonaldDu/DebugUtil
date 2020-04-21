package com.dhy.debugutil.demo;

import com.dhy.debugutil.TestConfig;

public class Config {
    @TestConfig("http://test.user.com")
    public static final String USER_URL = "http://www.user.com";

    @TestConfig(name = "bz", value = "http://test.bz.com")
    public static final String BZ_URL = "http://www.bz.com";

    @TestConfig(name = "other", value = "http://test.other.com")
    public static final String OTHER_URL = "http://www.other.com";
}
