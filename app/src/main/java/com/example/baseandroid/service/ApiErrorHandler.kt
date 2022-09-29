package com.example.baseandroid.service

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.baseandroid.R
import com.example.baseandroid.data.remote.Api
import com.example.baseandroid.models.ErrorResponse
import com.example.baseandroid.networking.ApiException
import com.example.baseandroid.networking.AppError
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.ui.base.ScreenType
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class ApiErrorHandler @Inject constructor(
    val context: Context,
    val gson: Gson,
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
                        Toast.makeText(context, "NoInternetConnectionException screen:$screenType", Toast.LENGTH_SHORT).show()
                    }
                    is ApiException.ActionAlreadyPerformingException -> {
                        Toast.makeText(context, "ActionAlreadyPerformingException screen:$screenType", Toast.LENGTH_SHORT).show()
                    }
                    is ConnectException -> Toast.makeText(context, "ConnectException screen:$screenType", Toast.LENGTH_SHORT).show()
                    is TimeoutException -> Toast.makeText(context, "TimeoutException screen:$screenType", Toast.LENGTH_SHORT).show()
                    is SocketTimeoutException -> Toast.makeText(context, "SocketTimeoutException screen:$screenType", Toast.LENGTH_SHORT).show()
                    is HttpException -> {
                        when (throwable.api) {
                            Api.Login -> defaultHandleError(throwable.throwable, screenType, appCompatActivity, callback)
                            else -> defaultHandleError(throwable.throwable, screenType, appCompatActivity, callback)
                        }
                    }
                    else -> {
                        Toast.makeText(context, "UnknownException", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else -> Toast.makeText(context, "UnknownException", Toast.LENGTH_SHORT).show()
        }
    }

    private fun defaultHandleError(exception: HttpException, screenType: ScreenType, appCompatActivity: AppCompatActivity, callback: () -> Unit) {
        val adapter = gson.getAdapter(ErrorResponse::class.java)
        try {
            val json = adapter.fromJson(exception.response()?.errorBody()?.string()) as ErrorResponse
            Toast.makeText(context, "message:${json.message} screen:$screenType", Toast.LENGTH_SHORT).show()
        } catch (error: IOException) {
            val alert = AlertDialog.Builder(appCompatActivity).apply {
                setTitle(R.string.app_name)
                setMessage("UnknownException")
                setPositiveButton(R.string.retry) { _, _ ->
                    callback.invoke()
                }
                setNegativeButton(R.string.cancel, null)
            }
            alert.show()
        }
    }

    private fun cleanLocalData() {
        localDataRepositoryInterface.cleanToken()
        localDataRepositoryInterface.cleanRefreshToken()
    }
}
