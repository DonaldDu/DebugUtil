package com.dhy.debugutil

import android.content.Context
import android.widget.EditText
import com.dhy.debugutil.data.TestConfig

open class TestUserUtil(context: Context, api: TestConfigApi, private val userEt: EditText?) : TestConfigUtil(context, api, "TestUsers") {

    override fun onConfigSelected(config: TestConfig) {
        userEt?.setText(config.getValue())
    }
}
