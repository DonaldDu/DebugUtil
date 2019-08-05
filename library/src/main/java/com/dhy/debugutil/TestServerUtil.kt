package com.dhy.debugutil

import android.content.Context
import android.view.View
import android.widget.TextView
import com.dhy.debugutil.data.ConfigRequest
import com.dhy.debugutil.data.ConfigResponse
import com.dhy.debugutil.data.Setting
import com.dhy.debugutil.data.TestServer
import com.dhy.retrofitrxutil.ObserverX
import com.dhy.xintent.XCommon
import com.dhy.xintent.interfaces.Callback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class TestServerUtil(context: Context, api: TestConfigApi) : TestConfigUtil<TestServer>(context, api, Setting.testServer, TestServer::class.java) {

    override fun getConfigClass(): Class<TestServer> {
        return TestServer::class.java
    }

    fun updateServerLabel(serverLabel: TextView, usingTestServer: TestServer) {
        serverLabel.visibility = View.VISIBLE
        XCommon.setTextWithFormat(serverLabel, String.format("%s@%s", usingTestServer.remark, usingTestServer.server))
    }

    override fun refreshDatas(context: Context, api: TestConfigApi, lcId: String, lcKey: String, callback: Callback<List<TestServer>?>) {
        val request = ConfigRequest(context.packageName, "TestServers")
        api.fetchTestServers(lcId, lcKey, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ObserverX<ConfigResponse<TestServer>>(context) {
                    override fun onResponse(response: ConfigResponse<TestServer>) {
                        callback.onCallback(response.configs)
                    }
                })
    }
}
