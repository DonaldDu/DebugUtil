package com.dhy.debugutil

import android.content.Context
import android.widget.EditText
import com.dhy.debugutil.data.ConfigRequest
import com.dhy.debugutil.data.ConfigResponse
import com.dhy.debugutil.data.Setting
import com.dhy.debugutil.data.TestUser
import com.dhy.retrofitrxutil.ObserverX
import com.dhy.xintent.interfaces.Callback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TestUserUtil(context: Context, api: TestConfigApi, private val userEt: EditText?) : TestConfigUtil<TestUser>(context, api, Setting.testUser, TestUser::class.java) {

    override fun getConfigClass(): Class<TestUser> {
        return TestUser::class.java
    }

    override fun onConfigSelected(config: TestUser) {
        userEt?.setText(config.name)
    }

    override fun refreshDatas(context: Context, api: TestConfigApi, lcId: String, lcKey: String, callback: Callback<List<TestUser>?>) {
        val req = ConfigRequest(context.packageName, "TestUsers")
        api.fetchTestUsers(lcId, lcKey, req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ObserverX<ConfigResponse<TestUser>>(context) {
                    override fun onResponse(response: ConfigResponse<TestUser>) {
                        callback.onCallback(response.configs)
                    }
                })
    }
}
