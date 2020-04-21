package com.dhy.debugutil;

class Config {
    @TestConfig("http://test.user.com")
    public static final String USER_URL = "http://www.user.com";

    @TestConfig(name = "bz", value = "http://test.bz.com")
    public static final String BZ_URL = "http://www.bz.com";

    public static final String OTHER_URL = "http://www.other.com";
}
