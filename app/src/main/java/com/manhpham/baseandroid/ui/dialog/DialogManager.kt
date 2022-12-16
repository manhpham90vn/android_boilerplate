package com.manhpham.baseandroid.ui.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.manhpham.baseandroid.R
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class DialogManager @Inject constructor(@ActivityContext val context: Context) {

    private var isShowedDialog = false

    fun showDialog(
        typeDialog: TypeDialog,
        title: String? = null,
        message: String? = null,
        closeButtonLabel: String? = null,
        retryButtonLabel: String? = null,
        callbackRetry: (() -> Unit)? = null,
        callbackClose: (() -> Unit)? = null
    ) {
        if (isShowedDialog) {
            return
        }
        isShowedDialog = true

        when (typeDialog) {
            TypeDialog.CLOSE_DIALOG -> {
                createCloseDialog(title, message, closeButtonLabel, callbackClose)
            }
            TypeDialog.RETRY_DIALOG -> {
                createRetryDialog(title, message, closeButtonLabel, retryButtonLabel, callbackRetry, callbackClose)
            }
        }
    }

    private fun createCloseDialog(
        title: String? = null,
        message: String? = null,
        closeButtonLabel: String? = null,
        callbackClose: (() -> Unit)? = null
    ) {
        val alert = AlertDialog.Builder(context).apply {
            setTitle(title ?: context.getString(R.string.tittle))
            setMessage(message ?: context.getString(R.string.message))
            setCancelable(false)
            setNegativeButton(closeButtonLabel ?: context.getString(R.string.close)) { _, _ ->
                callbackClose?.invoke()
            }
            setOnDismissListener { isShowedDialog = false }
        }
        alert.show()
    }

    private fun createRetryDialog(
        title: String? = null,
        message: String? = null,
        closeButtonLabel: String? = null,
        retryButtonLabel: String? = null,
        callbackRetry: (() -> Unit)? = null,
        callbackClose: (() -> Unit)? = null
    ) {
        val alert = AlertDialog.Builder(context).apply {
            setTitle(title ?: context.getString(R.string.tittle))
            setMessage(message ?: context.getString(R.string.message))
            setCancelable(false)
            setPositiveButton(closeButtonLabel ?: context.getString(R.string.retry)) { _, _ ->
                callbackRetry?.invoke()
            }
            setNegativeButton(retryButtonLabel ?: context.getString(R.string.close)) { _, _ ->
                callbackClose?.invoke()
            }
            setOnDismissListener { isShowedDialog = false }
        }
        alert.show()
    }
}

enum class TypeDialog {
    CLOSE_DIALOG,
    RETRY_DIALOG
}
