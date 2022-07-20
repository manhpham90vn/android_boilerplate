package com.example.baseandroid.networking

import android.content.Context
import android.widget.Toast
import com.example.baseandroid.ui.login.LoginActivity
import io.reactivex.rxjava3.exceptions.CompositeException
import javax.inject.Inject

class ApiErrorHandler @Inject constructor(val context: Context) {

    fun handleError(throwable: Throwable) {
        when (throwable) {
            is CompositeException -> {
                if (throwable.exceptions.filterIsInstance<ApiException.RefreshTokenException>().firstOrNull() != null) {
                    Toast.makeText(context, "RefreshTokenException", Toast.LENGTH_SHORT).show()
                    LoginActivity.toLoginRefreshToken(context)
                    return
                }
                when (val error = throwable.exceptions.filterIsInstance<ApiException>().first()) {
                    is ApiException.NoInternetConnectionException -> {
                        Toast.makeText(context, "NoInternetConnectionException ${error.api}", Toast.LENGTH_SHORT).show()
                    }
                    is ApiException.TimeOutException -> {
                        Toast.makeText(context, "TimeOutException ${error.api}", Toast.LENGTH_SHORT).show()
                    }
                    is ApiException.ParseJSONException -> {
                        Toast.makeText(context, "ParseJSONException ${error.api}", Toast.LENGTH_SHORT).show()
                    }
                    is ApiException.UnknownException -> {
                        Toast.makeText(context, "UnknownException ${error.api}", Toast.LENGTH_SHORT).show()
                    }
                    is ApiException.ServerErrorException -> {
                        Toast.makeText(context, "${error.message} ${error.api}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
