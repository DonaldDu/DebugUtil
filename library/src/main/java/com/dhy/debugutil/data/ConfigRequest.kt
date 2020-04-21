package com.dhy.debugutil.data

import com.google.gson.Gson
import java.io.Serializable

class ConfigRequest(appId: String, configName: String) : Serializable {
    private var applicationId: String = appId
    private var name: String = configName
    var data: List<RemoteConfig>? = null
    override fun toString(): String {
        return Gson().toJson(this)
    }
}
