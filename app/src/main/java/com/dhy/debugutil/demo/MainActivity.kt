package com.dhy.debugutil.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dhy.apiholder.ApiHolderUtil
import com.dhy.debugutil.TestServerUtil
import com.dhy.debugutil.TestUserUtil
import com.dhy.debugutil.data.TestConfig
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
        buttonServer.setOnClickListener {

        }

        val user = TestUserUtil(this, api, null, BuildConfig.X_LC_ID, BuildConfig.X_LC_KEY)
        user.initOnViewLongClick(buttonUser)
        buttonUser.setOnClickListener {
            user.show()
        }
        val server = object : TestServerUtil(this, api, BuildConfig.X_LC_ID, BuildConfig.X_LC_KEY) {
            override fun onConfigSelected(config: TestConfig) {

            }
        }
        server.initOnViewLongClick(buttonServer)
        buttonServer.setOnClickListener {
            server.show()
        }
    }
}
