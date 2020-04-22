package com.dhy.debugutil

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.dhy.debugutil.data.*
import com.dhy.retrofitrxutil.ObserverX
import com.dhy.xpreference.XPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class TestConfigUtil(private val context: Context,
                              private val api: TestConfigApi,
                              private val configName: String) : AdapterView.OnItemClickListener {

    private var configs: List<RemoteConfig>
    private var testConfigSetting: TestConfigSetting = XPreferences.get(context)
    private lateinit var dialog: Dialog
    private lateinit var listView: ListView
    private val itemLayoutId = android.R.layout.simple_list_item_1
    private val isTestUser = configName == "TestUsers"
    var configClass: Class<*>? = null

    companion object {
        var configFormatter: IConfigFormatter = object : IConfigFormatter {}
    }

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
        footer.text = String.format("更新【%s】数据", type)
        listView.addFooterView(footer, null, true)
        listView.onItemClickListener = this
        updateListView()
        dialog.show()
    }

    private fun updateListView() {
        if (configs.isEmpty() && configClass != null) onGetDatas(RemoteConfig.getConfigs(configClass!!))
        listView.adapter = ArrayAdapter(context, itemLayoutId, configs)
    }

    private fun onGetDatas(configs: List<RemoteConfig>) {
        this.configs = configs
        testConfigSetting.datas[configName] = configs
        XPreferences.put(context, testConfigSetting)
        updateListView()
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        if (position == parent.adapter.count - 1) {//last item: refresh datas
            refreshDatas()
        } else {//use current user
            onConfigSelected(configs[position])
            dismissDialog()
        }
    }

    private fun refreshDatas() {
        refreshDatas(context, api, getLcId(), getLcKey(), refreshDatasCallback)
    }

    private val refreshDatasCallback: (List<RemoteConfig>?) -> Unit = { result ->
        if (result != null) onGetDatas(result)
        else {
            val msg = if (isTestUser) "测试用户" else "测试服务器地址"
            AlertDialog.Builder(context)
                    .setMessage("获取${msg}数据失败")
                    .setNegativeButton("关闭", null)
                    .setPositiveButton("创建默认数据") { _, _ ->
                        createConfigs()
                    }.show()
        }
    }

    private fun createConfigs() {
        if (configClass != null) {
            createConfigs(configClass!!) {
                if (it.isSuccess) refreshDatas()
                val tip = if (it.isSuccess) "创建数据成功" else it.error
                Toast.makeText(context, tip, Toast.LENGTH_LONG).show()
            }
        } else Toast.makeText(context, "请设置 configClass", Toast.LENGTH_LONG).show()
    }

    protected open fun onConfigSelected(config: RemoteConfig) {}

    private fun refreshDatas(context: Context, api: TestConfigApi, lcId: String, lcKey: String, callback: (List<RemoteConfig>?) -> Unit) {
        val request = FetchConfigRequest(context.packageName, configName)
        api.fetchTestConfigs(lcId, lcKey, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ObserverX<ConfigResponse>(context) {
                    override fun onResponse(response: ConfigResponse) {
                        callback(response.configs)
                    }
                })
    }

    private fun createConfigs(configClass: Class<*>, callback: (LCResponse) -> Unit) {
        val lcId = getLcId()
        val lcKey = getLcKey()
        val request = CreateConfigRequest(context.packageName, configName)
        request.data = RemoteConfig.getConfigs(configClass)
        api.createTestConfigs(lcId, lcKey, request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ObserverX<LCResponse>(context) {
                    override fun onResponse(response: LCResponse) {
                        callback(response)
                    }
                })
    }

    private fun dismissDialog() {
        if (dialog.isShowing) dialog.dismiss()
    }

    private fun getLcId(): String {
        return context.getString(R.string.X_LC_ID)
    }

    private fun getLcKey(): String {
        return context.getString(R.string.X_LC_KEY)
    }
}
