package com.dhy.debugutil.demo

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface

import com.dhy.retrofitrxutil.IObserverX
import com.dhy.retrofitrxutil.StyledProgress
import com.dhy.retrofitrxutil.StyledProgressGenerator

class MyStyledProgressGenerator : StyledProgressGenerator {
    override fun generate(observer: IObserverX): StyledProgress? {
        val context = observer.context
        return if (context is Activity) {
            DialogProgressStyle(context, DialogInterface.OnCancelListener { observer.cancel() })
        } else {
            null
        }
    }
}

class DialogProgressStyle(private val context: Context, private val cancelListener: DialogInterface.OnCancelListener) : StyledProgress {
    var dialog: Dialog? = null
    override fun dismissProgress(delay: Boolean) {
        dialog?.dismiss()
    }

    override fun showProgress() {
        if (dialog == null) {
            dialog = ProgressDialog.show(context, "", "")
            dialog!!.setOnCancelListener(cancelListener)
        } else dialog!!.show()
    }
}