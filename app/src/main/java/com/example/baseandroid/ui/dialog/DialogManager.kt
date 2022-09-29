package com.example.baseandroid.ui.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.baseandroid.R
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class DialogManager @Inject constructor(@ActivityContext val context: Context) {

    private var shownDialoged = false

    fun showDialog(
        typeDialog: TypeDialog,
        title: String? = null,
        message: String? = null,
        closeButtonLable: String? = null,
        retryButtonLable: String? = null,
        callbackRetry: (() -> Unit)? = null,
        callbackClose: (() -> Unit)? = null
    ) {
        if (shownDialoged) {
            return
        }
        shownDialoged = true

        when (typeDialog) {
            TypeDialog.CLOSE_DIALOG -> {
                createCloseDialog(title, message, closeButtonLable, callbackClose)
            }
            TypeDialog.RETRY_DIALOG -> {
                createRetryDialog(title, message, closeButtonLable, retryButtonLable, callbackRetry, callbackClose)
            }
            else -> {

            }
        }
    }

    private fun createCloseDialog(
        title: String? = null,
        message: String? = null,
        closeButtonLable: String? = null,
        callbackClose: (() -> Unit)? = null
    ) {
        val alert = AlertDialog.Builder(context).apply {
            setTitle(title ?: context.getString(R.string.tittle))
            setMessage(message ?: context.getString(R.string.message))
            setCancelable(false)
            setNegativeButton(closeButtonLable ?:context.getString(R.string.close)) { _, _ ->
                callbackClose?.invoke()
            }
            setOnDismissListener { shownDialoged = false }
        }
        alert.show()
    }

    private fun createRetryDialog(
        title: String? = null,
        message: String? = null,
        closeButtonLable: String? = null,
        retryButtonLable: String? = null,
        callbackRetry: (() -> Unit)? = null,
        callbackClose: (() -> Unit)? = null
    ) {
        val alert = AlertDialog.Builder(context).apply {
            setTitle(title ?: context.getString(R.string.tittle))
            setMessage(message ?: context.getString(R.string.message))
            setCancelable(false)
            setPositiveButton(closeButtonLable ?: context.getString(R.string.retry)) { _, _ ->
                callbackRetry?.invoke()
            }
            setNegativeButton(retryButtonLable ?: context.getString(R.string.close)) { _, _ ->
                callbackClose?.invoke()
            }
            setOnDismissListener { shownDialoged = false }
        }
        alert.show()
    }
}

enum class TypeDialog {
    CLOSE_DIALOG,
    RETRY_DIALOG
}