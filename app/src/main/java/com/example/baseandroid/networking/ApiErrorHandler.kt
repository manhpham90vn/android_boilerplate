package com.example.baseandroid.networking

import android.content.Context
import android.widget.Toast
import com.example.baseandroid.models.ErrorResponse
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.ui.login.LoginActivity
import com.google.gson.Gson
import retrofit2.HttpException
import timber.log.Timber
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

    fun handleError(throwable: Throwable) {
        Timber.d(throwable.stackTraceToString())
        when (throwable) {
            // throw by use case
            is ApiException.RefreshTokenException -> {
                Toast.makeText(context, "RefreshTokenException", Toast.LENGTH_SHORT).show()
                cleanLocalData()
                LoginActivity.toLoginRefreshToken(context)
            }
            is ApiException.NoInternetConnectionException -> {
                Toast.makeText(context, "NoInternetConnectionException", Toast.LENGTH_SHORT).show()
            }
            is ApiException.ActionAlreadyPerformingException -> {
                Toast.makeText(context, "ActionAlreadyPerformingException", Toast.LENGTH_SHORT).show()
            }
            is ApiException.TimeOutException -> {
                Toast.makeText(context, "TimeOutException api:${throwable.api}", Toast.LENGTH_SHORT).show()
            }
            is ApiException.ParseJSONException -> {
                Toast.makeText(context, "ParseJSONException api:${throwable.api}", Toast.LENGTH_SHORT).show()
            }
            is ApiException.UnknownException -> {
                Toast.makeText(context, "UnknownException api:${throwable.api}", Toast.LENGTH_SHORT).show()
            }
            is ApiException.ServerErrorException -> {
                Toast.makeText(context, "message:${throwable.message} api:${throwable.api}", Toast.LENGTH_SHORT).show()
            }

            // throw by paging
            is HttpException -> {
                val adapter = gson.getAdapter(ErrorResponse::class.java)
                try {
                    val json = adapter.fromJson(throwable.response()?.errorBody()?.string()) as ErrorResponse
                    Toast.makeText(context, "message:${json.message}", Toast.LENGTH_SHORT).show()
                } catch (error: IOException) {
                    Toast.makeText(context, "ParseJSONException", Toast.LENGTH_SHORT).show()
                }
            }
            is ConnectException -> Toast.makeText(context, "NoInternetConnectionException", Toast.LENGTH_SHORT).show()
            is TimeoutException -> Toast.makeText(context, "TimeOutException", Toast.LENGTH_SHORT).show()
            is SocketTimeoutException -> Toast.makeText(context, "TimeOutException", Toast.LENGTH_SHORT).show()
            is AppError -> {
                handleError(throwable.throwable)
            }

            else -> Toast.makeText(context, "UnknownException", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cleanLocalData() {
        localDataRepositoryInterface.cleanToken()
        localDataRepositoryInterface.cleanRefreshToken()
        RefreshTokenValidator.getInstance().lastFailedDate = null
    }
}
