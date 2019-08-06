package com.dhy.debugutil.data

import java.io.Serializable

class TestConfig() : Serializable {
    private var name: String? = null
    private var server: String? = null
    private var value: String? = null
    var remark: String = ""

    constructor(value: String, remark: String) : this() {
        this.value = value
        this.remark = remark
    }

    fun getValue(): String? {
        return value ?: (server ?: name)
    }

    override fun toString(): String {
        return remark + "\n" + getValue()
    }
}
