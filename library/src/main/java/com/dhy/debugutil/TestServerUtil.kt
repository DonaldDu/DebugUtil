package com.dhy.debugutil

import android.content.Context
import android.view.View
import android.widget.TextView
import com.dhy.debugutil.data.TestConfig
import com.dhy.xintent.XCommon

open class TestServerUtil(context: Context, api: TestConfigApi) : TestConfigUtil(context, api, "TestServers") {
    fun updateServerLabel(serverLabel: TextView, usingTestServer: TestConfig) {
        serverLabel.visibility = View.VISIBLE
        XCommon.setTextWithFormat(serverLabel, String.format("%s@%s", usingTestServer.remark, usingTestServer.getValue()))
    }
}
