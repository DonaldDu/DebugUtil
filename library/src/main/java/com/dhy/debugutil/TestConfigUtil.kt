package com.dhy.debugutil

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.dhy.debugutil.data.ConfigRequest
import com.dhy.debugutil.data.ConfigResponse
import com.dhy.debugutil.data.TestConfig
import com.dhy.debugutil.data.TestConfigSetting
import com.dhy.retrofitrxutil.ObserverX
import com.dhy.xintent.interfaces.Callback
import com.dhy.xpreference.XPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class TestConfigUtil(private val context: Context,
                              private val api: TestConfigApi,
                              private val lcId: String,
                              private val lcKey: String,
                              private val configName: String) : AdapterView.OnItemClickListener {

    private var configs: List<TestConfig>
    private var testConfigSetting: TestConfigSetting = XPreferences.get(context)
            ?: TestConfigSetting()
    private lateinit var dialog: Dialog
    private lateinit var listView: ListView
    private val itemLayoutId = android.R.layout.simple_list_item_1
    private val isTestUser = configName == "TestUsers"

    init {
        configs = testConfigSetting.datas[configName] ?: emptyList()
        testConfigSetting.datas[configName] = configs
    }

    fun initOnViewLongClick(view: View) {
        view.setOnLongClickListener {
            show()
            true
        }
    }

    fun show() {
        dialog = Dialog(context)
        listView = ListView(context)
        dialog.setContentView(listView)
        val footer = LayoutInflater.from(context).inflate(itemLayoutId, null) as TextView
        val type = if (isTestUser) "测试用户" else "测试服务器地址"
        footer.text = String.format("点击更新【%s】数据", type)
        listView.addFooterView(footer, null, true)
        listView.onItemClickListener = this
        updateListView()
        dialog.show()
    }

    private fun updateListView() {
        listView.adapter = ArrayAdapter(context, itemLayoutId, configs)
    }

    private fun onGetDatas(configs: List<TestConfig>) {
        this.configs = configs
        testConfigSetting.datas[configName] = configs
        XPreferences.put(context, testConfigSetting)
        updateListView()
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        if (position == parent.adapter.count - 1) {//last item: refresh datas
            refreshDatas(context, api, lcId, lcKey, Callback { result ->
                if (result != null) onGetDatas(result)
                else {
                    val msg = if (isTestUser) "测试用户" else "测试服务器地址"
                    AlertDialog.Builder(context)
                            .setMessage("获取${msg}数据失败")
                            .setPositiveButton("OK", null)
                            .show()
                }
            })
        } else {//use current user
            onConfigSelected(configs[position])
            dismissDialog()
        }
    }

    protected abstract fun onConfigSelected(config: TestConfig)

    private fun refreshDatas(context: Context, api: TestConfigApi, lcId: String, lcKey: String, callback: Callback<List<TestConfig>?>) {
        val request = ConfigRequest(context.packageName, configName)
        api.fetchTestConfigs(lcId, lcKey, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ObserverX<ConfigResponse>(context) {
                    override fun onResponse(response: ConfigResponse) {
                        callback.onCallback(response.configs)
                    }
                })
    }

    private fun dismissDialog() {
        if (dialog.isShowing) dialog.dismiss()
    }
}
