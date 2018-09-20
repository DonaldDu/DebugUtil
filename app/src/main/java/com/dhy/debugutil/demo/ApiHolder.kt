package com.dhy.debugutil.demo

import com.dhy.apiholder.BaseUrl
import com.dhy.debugutil.TestConfigApi

interface ApiHolder : SysApi, TestConfigApi
@BaseUrl("https://www.wwvas.com:9101/vasms-web/")
interface SysApi {

}