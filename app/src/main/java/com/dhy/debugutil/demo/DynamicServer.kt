package com.dhy.debugutil.demo

import android.content.Context
import androidx.annotation.Keep
import com.dhy.debugutil.data.IDynamicServer
import com.dhy.debugutil.data.RemoteConfig
import com.dhy.debugutil.data.getUsingTestServer

enum class DynamicServer(private val release: String, private val test: String) {
    BASE_URL("http://www.abc1.com", "http://192.168.141.34:8093"),
    YW_URL("http://www.abc2.com", "http://192.168.141.34:8085/");

    override fun toString(): String {
        return RemoteConfig.serversMap[name] ?: release
    }

    private class Server(val server: DynamicServer) : IDynamicServer {
        override val name: String
            get() = server.name
        override val release: String
            get() = server.release
        override val test: String
            get() = server.test
    }

    companion object {
        fun load(context: Context) {
            if (BuildConfig.DEBUG) {
                RemoteConfig.dynamicServers = values().map { Server(it) }
                updateServer(context.getUsingTestServer())
            }
        }

        fun updateServer(config: RemoteConfig, context: Context? = null) {
            config.updateServersMap(context)
//            RetrofitManager.update()//todo
        }
    }
}

operator fun DynamicServer.plus(other: String): String {
    return toString() + other
}