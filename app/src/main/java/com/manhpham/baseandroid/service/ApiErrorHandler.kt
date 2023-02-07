package com.manhpham.baseandroid.service

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.manhpham.baseandroid.R
import com.manhpham.baseandroid.data.remote.Api
import com.manhpham.baseandroid.models.ErrorResponse
import com.manhpham.baseandroid.networking.ApiException
import com.manhpham.baseandroid.networking.AppError
import com.manhpham.baseandroid.repository.AppLocalDataRepositoryInterface
import com.manhpham.baseandroid.ui.base.ScreenType
import com.manhpham.baseandroid.ui.dialog.DialogManager
import com.manhpham.baseandroid.ui.dialog.TypeDialog
import dagger.hilt.android.qualifiers.ActivityContext
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class ApiErrorHandler @Inject constructor(
    @ActivityContext val context: Context,
    val gson: Gson,
    private val dialogManager: DialogManager,
    private val localDataRepositoryInterface: AppLocalDataRepositoryInterface
) {

    fun handleError(throwable: Throwable, screenType: ScreenType, appCompatActivity: AppCompatActivity, callback: () -> Unit = {}) {
        when (throwable) {
            is AppError -> {
                when (throwable.throwable) {
                    is ApiException.RefreshTokenException -> {
                        Toast.makeText(context, "RefreshTokenException screen:$screenType", Toast.LENGTH_SHORT).show()
                        cleanLocalData()
                        Navigation
                            .findNavController(appCompatActivity, R.id.proxy_fragment_container)
                            .navigate(R.id.action_homeFragment_to_loginFragment)
                    }
                    is ApiException.NoInternetConnectionException -> {
                        dialogManager.showDialog(TypeDialog.CLOSE_DIALOG, message = context.getString(R.string.no_internet))
                    }
                    is ApiException.ActionAlreadyPerformingException -> {
                        dialogManager.showDialog(
                            TypeDialog.RETRY_DIALOG,
                            message = context.getString(R.string.api_exception),
                            callbackRetry = callback
                        )
                    }
                    is ConnectException -> {
                        dialogManager.showDialog(
                            TypeDialog.RETRY_DIALOG,
                            message = context.getString(R.string.connect_exception),
                            callbackRetry = callback
                        )
                    }
                    is TimeoutException -> {
                        dialogManager.showDialog(
                            TypeDialog.RETRY_DIALOG,
                            message = context.getString(R.string.timeout_exception),
                            callbackRetry = callback
                        )
                    }
                    is SocketTimeoutException -> {
                        dialogManager.showDialog(
                            TypeDialog.RETRY_DIALOG,
                            message = context.getString(R.string.socket_timeout_exception),
                            callbackRetry = callback
                        )
                    }
                    is HttpException -> {
                        when (throwable.api) {
                            Api.Login -> defaultHandleError(throwable.throwable, screenType, callback)
                            else -> defaultHandleError(throwable.throwable, screenType, callback)
                        }
                    }
                    else -> {
                        showDialogUnknownException(callback)
                    }
                }
            }
            else -> showDialogUnknownException(callback)
        }
    }

    private fun defaultHandleError(exception: HttpException, screenType: ScreenType, callback: () -> Unit) {
        val adapter = gson.getAdapter(ErrorResponse::class.java)
        try {
            val json = adapter.fromJson(exception.response()?.errorBody()?.string()) as ErrorResponse
            Toast.makeText(context, "message:${json.message} screen:$screenType", Toast.LENGTH_SHORT).show()
            dialogManager.showDialog(TypeDialog.RETRY_DIALOG, message = "${json.message}", callbackRetry = callback)
        } catch (error: IOException) {
            showDialogUnknownException(callback)
        }
    }

    private fun showDialogUnknownException(callback: () -> Unit) {
        dialogManager.showDialog(
            TypeDialog.CLOSE_DIALOG,
            message = context.getString(R.string.unknown_exception),
            callbackClose = callback
        )
    }

    private fun cleanLocalData() {
        localDataRepositoryInterface.cleanToken()
        localDataRepositoryInterface.cleanRefreshToken()
    }
}
