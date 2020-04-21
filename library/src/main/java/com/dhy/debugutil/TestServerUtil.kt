package com.dhy.debugutil

import android.content.Context
import android.view.View
import android.widget.TextView
import com.dhy.debugutil.data.RemoteConfig
import com.dhy.xintent.XCommon

open class TestServerUtil(context: Context, api: TestConfigApi, configClass: Class<*>) : TestConfigUtil(context, api, "TestServers") {
    init {
        this.configClass = configClass
    }

    fun updateServerLabel(serverLabel: TextView, usingTestServer: RemoteConfig) {
        serverLabel.visibility = View.VISIBLE
        XCommon.setTextWithFormat(serverLabel, usingTestServer.toString())
    }
}
