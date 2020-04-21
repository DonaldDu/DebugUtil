package com.dhy.debugutil

import android.content.Context
import android.widget.EditText
import com.dhy.debugutil.data.RemoteConfig

open class TestUserUtil(context: Context, api: TestConfigApi, private val userEt: EditText?) : TestConfigUtil(context, api, "TestUsers") {

    override fun onConfigSelected(config: RemoteConfig) {
        userEt?.setText(config.getValue())
    }
}
