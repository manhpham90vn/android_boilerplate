package com.example.baseandroid.networking

import android.content.Context
import android.widget.Toast
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.ui.login.LoginActivity
import javax.inject.Inject

class ApiErrorHandler @Inject constructor(
    val context: Context,
    private val localDataRepositoryInterface: AppLocalDataRepositoryInterface
) {

    fun handleError(throwable: Throwable) {
        when (throwable) {
            is ApiException.RefreshTokenException -> {
                Toast.makeText(context, "RefreshTokenException", Toast.LENGTH_SHORT).show()
                cleanLocalData()
                LoginActivity.toLoginRefreshToken(context)
            }
            is ApiException.NoInternetConnectionException -> {
                Toast.makeText(context, "NoInternetConnectionException", Toast.LENGTH_SHORT).show()
            }
            is ApiException.ActionAlreadyPerformingException -> {
                Toast.makeText(context, "NoInternetConnectionException", Toast.LENGTH_SHORT).show()
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
        }
    }

    private fun cleanLocalData() {
        localDataRepositoryInterface.cleanToken()
        localDataRepositoryInterface.cleanRefreshToken()
        RefreshTokenValidator.getInstance().lastFailedDate = null
    }
}
