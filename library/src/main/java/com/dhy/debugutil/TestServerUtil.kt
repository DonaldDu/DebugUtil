package com.dhy.debugutil

import android.content.Context
import android.view.View
import android.widget.TextView
import com.dhy.debugutil.data.RemoteConfig
import com.dhy.xintent.XCommon

open class TestServerUtil(context: Context, api: TestConfigApi) : TestConfigUtil(context, api, "TestServers") {
    override fun loadData(): List<RemoteConfig> {
        return super.loadData().filter { it.isValid() }
    }

    companion object {
        @JvmStatic
        fun updateServerLabel(serverLabel: TextView, usingTestServer: RemoteConfig) {
            serverLabel.visibility = View.VISIBLE
            XCommon.setTextWithFormat(serverLabel, usingTestServer.toString())
        }
    }
}
