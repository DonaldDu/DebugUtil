package com.dhy.debugutil

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.dhy.debugutil.data.Setting
import com.dhy.xintent.XCommon
import com.dhy.xintent.interfaces.Callback
import com.dhy.xintent.preferences.XPreferences
import java.util.*

abstract class TestConfigUtil<CONFIG>(private val context: Context,
                                      private val api: TestConfigApi,
                                      private val TEST_CONFIG_TOKEN: String,
                                      private val KEY_SETTING: Setting,
                                      configClass: Class<CONFIG>) : AdapterView.OnItemClickListener {

    private var configs: List<CONFIG>? = null
    private lateinit var dialog: Dialog
    private lateinit var listView: ListView
    private val itemLayoutId = android.R.layout.simple_list_item_1
    private val isTestUser = KEY_SETTING == Setting.testUser
    private fun isStatic(): Boolean {
        return XCommon.checkSelfPermission(context, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
    }

    init {
        configs = XPreferences.getList(context, KEY_SETTING, isStatic(), configClass)
    }

    protected abstract fun getConfigClass(): Class<CONFIG>


    fun initOnViewLongClick(view: View) {
        view.setOnLongClickListener {
            show()
            true
        }
    }

    private fun show() {
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
        if (configs == null) configs = ArrayList()
        listView.adapter = ArrayAdapter(context, itemLayoutId, configs!!)
    }

    private fun onGetDatas(configs: List<CONFIG>) {
        this.configs = configs
        XPreferences.set(context, KEY_SETTING, isStatic(), configs)
        updateListView()
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        if (position == parent.adapter.count - 1) {//last item: refresh datas
            refreshDatas(context, api, TEST_CONFIG_TOKEN, Callback { result ->
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
            onConfigSelected(configs!![position])
            dismissDialog()
        }
    }

    protected abstract fun onConfigSelected(config: CONFIG)

    protected abstract fun refreshDatas(context: Context, api: TestConfigApi, token: String, callback: Callback<List<CONFIG>?>)

    private fun dismissDialog() {
        if (dialog.isShowing) dialog.dismiss()
    }
}
