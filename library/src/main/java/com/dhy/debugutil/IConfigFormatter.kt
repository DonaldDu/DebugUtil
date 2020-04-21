package com.dhy.debugutil

import com.dhy.debugutil.data.RemoteConfig

interface IConfigFormatter {
    fun format(config: RemoteConfig): String {
        val server = config.values.joinToString("\n")
        return "${config.name}\n$server"
    }
}