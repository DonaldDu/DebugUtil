package com.dhy.debugutil.data

import com.dhy.debugutil.TestConfig
import com.dhy.debugutil.TestConfigUtil
import java.io.Serializable

class RemoteConfig : Serializable {
    var name = ""
    var servers: MutableList<String> = mutableListOf()
    fun getValue(): String {
        return servers.joinToString()
    }

    fun addServer(remark: String?, server: String) {
        if (remark.isNullOrEmpty()) servers.add(server)
        else servers.add("$remark@$server")
    }

    override fun toString(): String {
        return TestConfigUtil.configFormatter.format(this)
    }

    fun isRelease(): Boolean {
        return name.toLowerCase() == "release"
    }

    companion object {
        @JvmStatic
        fun getConfigs(configClass: Class<*>): List<RemoteConfig> {
            val test = RemoteConfig().apply {
                name = "Test"
            }
            val release = RemoteConfig().apply {
                name = "Release"
            }
            configClass.declaredFields.forEach {
                val config = it.getAnnotation(TestConfig::class.java)
                if (config != null) {
                    test.addServer(config.name, config.value)

                    it.isAccessible = true
                    release.addServer(config.name, it.get(null).toString())
                }
            }
            return listOf(test, release)
        }
    }
}
