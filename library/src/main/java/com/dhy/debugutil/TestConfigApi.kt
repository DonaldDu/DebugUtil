package com.dhy.debugutil

import com.dhy.apiholder.BaseUrl
import com.dhy.debugutil.data.ConfigRequest
import com.dhy.debugutil.data.ConfigResponse
import com.dhy.debugutil.data.TestServer
import com.dhy.debugutil.data.TestUser
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

@BaseUrl("http://xlog.leanapp.cn/")
interface TestConfigApi {
    @POST("xlog/sql#TestUser")
    fun fetchTestUsers(@Body request: ConfigRequest): Observable<ConfigResponse<TestUser>>

    @POST("xlog/sql#TestServer")
    fun fetchTestServers(@Body request: ConfigRequest): Observable<ConfigResponse<TestServer>>
}