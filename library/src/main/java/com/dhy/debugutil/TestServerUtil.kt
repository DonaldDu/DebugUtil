package com.dhy.debugutil

import android.content.Context
import android.view.View
import android.widget.TextView
import com.dhy.debugutil.data.TestConfig
import com.dhy.xintent.XCommon

abstract class TestServerUtil(context: Context, api: TestConfigApi, lcId: String, lcKey: String) : TestConfigUtil(context, api, lcId, lcKey, "TestServers") {

    fun updateServerLabel(serverLabel: TextView, usingTestServer: TestConfig) {
        serverLabel.visibility = View.VISIBLE
        XCommon.setTextWithFormat(serverLabel, String.format("%s@%s", usingTestServer.remark, usingTestServer.getValue()))
    }
}
