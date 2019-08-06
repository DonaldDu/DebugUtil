package com.dhy.debugutil

import android.content.Context
import android.widget.EditText
import com.dhy.debugutil.data.TestConfig

class TestUserUtil(context: Context, api: TestConfigApi, private val userEt: EditText?, lcId: String, lcKey: String) : TestConfigUtil(context, api, lcId, lcKey, "TestUsers") {

    override fun onConfigSelected(config: TestConfig) {
        userEt?.setText(config.getValue())
    }
}
