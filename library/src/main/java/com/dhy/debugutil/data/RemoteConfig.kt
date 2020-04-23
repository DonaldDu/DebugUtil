package com.dhy.debugutil.data

import com.dhy.debugutil.TestConfig
import com.dhy.debugutil.TestConfigUtil
import java.io.Serializable

class RemoteConfig : Serializable {
    var name = ""
    var values: MutableList<String> = mutableListOf()
    fun add(name: String, value: String) {
        values.add("$name@$value")
    }

    override fun toString(): String {
        return TestConfigUtil.configFormatter.format(this)
    }

    fun isRelease(): Boolean {
        return name.toLowerCase() == "release"
    }

    private fun toConfigs(): List<Config> {
        return values.map {
            val kv = it.split("@")
            Config(kv.first(), kv.last())
        }
    }

    /**
     * key:releaseValue,value:testValue
     * */
    fun toConfigs(configClass: Class<*>): List<Config> {
        val servers = toConfigs()
        configClass.declaredFields.forEach {
            val testConfig = it.getAnnotation(TestConfig::class.java)
            if (testConfig != null) {
                if (!it.isAccessible) it.isAccessible = true
                val server = servers.find { s -> s.name == testConfig.name }!!
                server.defaultValue = it.get(null).toString()
            }
        }
        return servers
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
                    test.add(config.name, config.value)

                    it.isAccessible = true
                    release.add(config.name, it.get(null).toString())
                }
            }
            return listOf(test, release)
        }

        @JvmStatic
        fun getReleaseConfig(configClass: Class<*>): RemoteConfig {
            return getConfigs(configClass).find { it.isRelease() }!!
        }
    }
}
