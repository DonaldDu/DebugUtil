package com.dhy.debugutil.data

import java.io.Serializable

class ConfigResponse<CONTENT : Serializable> : Serializable {
    private val results: List<ConfigRecord<CONTENT>>? = null

    val configs: List<CONTENT>?
        get() = if (results?.size == 1) results.first().json else null

    private class ConfigRecord<CONFIG> : Serializable {
        internal var json: List<CONFIG>? = null
    }
}