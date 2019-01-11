package com.dhy.debugutil

import android.content.Context
import android.view.View
import android.widget.TextView
import com.dhy.debugutil.data.*
import com.dhy.retrofitrxutil.ObserverX
import com.dhy.retrofitrxutil.subscribeX
import com.dhy.xintent.XCommon
import com.dhy.xintent.interfaces.Callback

abstract class TestServerUtil(context: Context,
                              api: TestConfigApi,
                              TEST_CONFIG_TOKEN: String,
                              private val sqlId: String = "5ba30ef37f6fd3005b544488") : TestConfigUtil<TestServer>(context, api, TEST_CONFIG_TOKEN, Setting.testServer, TestServer::class.java) {

    override fun getConfigClass(): Class<TestServer> {
        return TestServer::class.java
    }

    fun updateServerLabel(serverLabel: TextView, usingTestServer: TestServer) {
        serverLabel.visibility = View.VISIBLE
        XCommon.setTextWithFormat(serverLabel, String.format("%s@%s", usingTestServer.remark, usingTestServer.server))
    }

    override fun refreshDatas(context: Context, api: TestConfigApi, token: String, callback: Callback<List<TestServer>?>) {
        val data = ConfigRequestData(context.packageName)
        val request = ConfigRequest(context.packageName, token, sqlId, data)
        api.fetchTestServers(request)
                .subscribeX(context) {
                    callback.onCallback(it.configs)
                }
    }
}
