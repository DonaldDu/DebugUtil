package com.dhy.debugutil.data

import java.io.Serializable

class ConfigResponse : Serializable {
    private val results: List<ConfigRecord>? = null

    val configs: List<TestConfig>?
        get() = if (results?.size == 1) results.first().json else null

    private class ConfigRecord : Serializable {
        internal var json: List<TestConfig>? = null
    }
}