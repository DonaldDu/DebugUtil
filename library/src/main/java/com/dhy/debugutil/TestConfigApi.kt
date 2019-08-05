package com.dhy.debugutil

import com.dhy.apiholder.BaseUrl
import com.dhy.debugutil.data.ConfigRequest
import com.dhy.debugutil.data.ConfigResponse
import com.dhy.debugutil.data.TestServer
import com.dhy.debugutil.data.TestUser
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

@BaseUrl("https://api.leancloud.cn/1.1/")
interface TestConfigApi {
    /**
     * @param where {"name":"TestServers","applicationId":"com.wwgps.ect"}
     * */
    @GET("classes/Config")
    fun fetchTestServers(@Header("X-LC-Id") LC_ID: String, @Header("X-LC-Key") LC_KEY: String, @Query("where") where: ConfigRequest): Observable<ConfigResponse<TestServer>>

    /**
     * @param where {"name":"TestServers","applicationId":"com.wwgps.ect"}
     * */
    @GET("classes/Config")
    fun fetchTestUsers(@Header("X-LC-Id") LC_ID: String, @Header("X-LC-Key") LC_KEY: String, @Query("where") where: ConfigRequest): Observable<ConfigResponse<TestUser>>
}