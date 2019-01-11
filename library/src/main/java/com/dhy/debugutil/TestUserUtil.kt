package com.dhy.debugutil

import android.content.Context
import android.widget.EditText
import com.dhy.debugutil.data.*
import com.dhy.retrofitrxutil.ObserverX
import com.dhy.retrofitrxutil.subscribeX
import com.dhy.xintent.interfaces.Callback

class TestUserUtil(context: Context, api: TestConfigApi,
                   private val userEt: EditText?,
                   TEST_CONFIG_TOKEN: String,
                   private val sqlId: String = "5ba30e1b570c350067a6cd89") : TestConfigUtil<TestUser>(context, api, TEST_CONFIG_TOKEN, Setting.testUser, TestUser::class.java) {

    override fun getConfigClass(): Class<TestUser> {
        return TestUser::class.java
    }

    override fun onConfigSelected(config: TestUser) {
        userEt?.setText(config.name)
    }

    override fun refreshDatas(context: Context, api: TestConfigApi, token: String, callback: Callback<List<TestUser>?>) {
        val data = ConfigRequestData(context.packageName)
        val req = ConfigRequest(context.packageName, token, sqlId, data)
        api.fetchTestUsers(req)
                .subscribeX(context) {
                    callback.onCallback(it.configs)
                }
    }
}
