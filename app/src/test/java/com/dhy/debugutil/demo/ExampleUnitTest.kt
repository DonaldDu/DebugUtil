package com.dhy.debugutil.demo

import com.dhy.debugutil.data.RemoteConfig
import com.google.gson.Gson
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val configs = RemoteConfig.getConfigs(Config::class.java)
        println(Gson().toJson(configs))
    }
}
