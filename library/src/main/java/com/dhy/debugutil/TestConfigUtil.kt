package com.dhy.debugutil

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.dhy.debugutil.data.Setting
import com.dhy.xintent.XCommon
import com.dhy.xintent.adapter.IBaseAdapter
import com.dhy.xintent.interfaces.Callback
import com.dhy.xintent.preferences.XPreferences
import java.util.*
import kotlin.collections.ArrayList

abstract class TestConfigUtil<CONFIG>(private val context: Context,
                                      private val api: TestConfigApi,
                                      private val TEST_CONFIG_TOKEN: String,
                                      private val KEY_SETTING: Setting,
                                      configClass: Class<CONFIG>) {

    private val configs: MutableList<CONFIG> = mutableListOf()
    private lateinit var dialog: Dialog
    private lateinit var listView: ListView
    private lateinit var adapter: MyAdapter
    private val itemLayoutId = android.R.layout.simple_list_item_1
    private val isTestUser = KEY_SETTING == Setting.testUser
    private fun isStatic(): Boolean {
//        return XCommon.checkSelfPermission(context, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
        return false
    }

    init {
        val configs = XPreferences.getList(context, KEY_SETTING, isStatic(), configClass)
        if (configs != null) this.configs.addAll(configs)
    }

    protected abstract fun getConfigClass(): Class<CONFIG>

    fun initOnViewLongClick(view: View) {
        view.setOnLongClickListener {
            show()
            true
        }
    }

    fun show() {
        adapter = MyAdapter()
        dialog = AlertDialog.Builder(context)
                .setTitle(if (isTestUser) "测试用户" else "测试服务器地址")
                .setAdapter(adapter, onClickListener)
                .create()
        dialog.show()
    }

    private val onClickListener = DialogInterface.OnClickListener { _, position ->
        if (position == adapter.count - 1) {//last item: refresh datas
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
            onConfigSelected(configs[position])
            dismissDialog()
        }
    }

    private fun onGetDatas(configs: List<CONFIG>) {
        XPreferences.set(context, KEY_SETTING, isStatic(), configs)
        this.configs.clear()
        this.configs.addAll(configs)
        show()
    }

    protected abstract fun onConfigSelected(config: CONFIG)

    protected abstract fun refreshDatas(context: Context, api: TestConfigApi, token: String, callback: Callback<List<CONFIG>?>)

    private fun dismissDialog() {
        if (dialog.isShowing) dialog.dismiss()
    }

    private inner class MyAdapter : IBaseAdapter<CONFIG>(context, configs, itemLayoutId) {
        override fun getCount(): Int {
            return super.getCount() + 1
        }

        override fun getItem(position: Int): CONFIG? {
            return if (position + 1 == count) null else super.getItem(position)
        }

        override fun updateItemView(i: CONFIG?, position: Int, convertView: View, parent: ViewGroup) {
            val tv = convertView as TextView
            tv.text = i?.toString() ?: "点击更新数据"
        }
    }
}
