package com.dhy.debugutil.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dhy.apiholder.ApiHolderUtil
import com.dhy.debugutil.TestServerUtil
import com.dhy.debugutil.TestUserUtil
import com.dhy.debugutil.data.RemoteConfig
import com.dhy.retrofitrxutil.ObserverX
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var api: ApiHolder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ObserverX.setDefaultStyledProgressGenerator(MyStyledProgressGenerator())
        ObserverX.setDefaultErrorHandler(MyNetErrorHandler())
        api = ApiHolderUtil(ApiHolder::class).api
        val context = this
        val user = TestUserUtil(context, api, null)
        user.initOnViewLongClick(buttonUser)
        buttonUser.setOnClickListener {
            user.show()
        }
        DynamicServer.load(this)
        val server = object : TestServerUtil(context, api) {
            override fun onConfigSelected(config: RemoteConfig) {
                DynamicServer.updateServer(config, this@MainActivity)
            }
        }
        server.initOnViewLongClick(buttonServer)
        buttonServer.setOnClickListener {
            server.show()
        }
    }
}
