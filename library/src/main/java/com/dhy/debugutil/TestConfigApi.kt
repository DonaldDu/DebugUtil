package com.dhy.debugutil

import com.dhy.apiholder.BaseUrl
import com.dhy.debugutil.data.ConfigRequest
import com.dhy.debugutil.data.ConfigResponse
import com.dhy.debugutil.data.LCResponse
import io.reactivex.Observable
import retrofit2.http.*

@BaseUrl("https://api.leancloud.cn/1.1/")
interface TestConfigApi {
    /**
     * @param where {"name":"TestXXs","applicationId":"com.wwgps.ect"}
     * */
    @GET("classes/Config")
    fun fetchTestConfigs(@Header("X-LC-Id") LC_ID: String, @Header("X-LC-Key") LC_KEY: String, @Query("where") where: ConfigRequest): Observable<ConfigResponse>

    @POST("classes/Config")
    fun createTestConfigs(@Header("X-LC-Id") LC_ID: String, @Header("X-LC-Key") LC_KEY: String, @Body data: ConfigRequest): Observable<LCResponse>
}