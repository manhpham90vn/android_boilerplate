package com.example.baseandroid.ui.login

import androidx.lifecycle.MutableLiveData
import com.example.baseandroid.models.ErrorResponse
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.repository.AppRemoteDataRepositoryInterface
import com.example.baseandroid.ui.base.BaseViewModel
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.kotlin.addTo
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import javax.inject.Inject

sealed class LoginResult {
    object LoginSuccess: LoginResult()
    data class LoginError(val message: String): LoginResult()
}



class LoginViewModel @Inject constructor(): BaseViewModel() {

    @Inject lateinit var appRemoteDataRepositoryInterface: AppRemoteDataRepositoryInterface
    @Inject lateinit var appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val welcomeString = MutableLiveData<String>()
    val loginResult = MutableLiveData<LoginResult>()

    fun login() {
        email.value = "admin@admin.com"
        welcomeString.value = "Welcome admin@admin.com"
        password.value = "pwd123451"

        // simple error handle (case HttpException, case ConnectException)
        // need create common handle
        appRemoteDataRepositoryInterface
            .callLogin(email.value.orEmpty(), password.value.orEmpty())
            .subscribe({
                if (!it.token.isNullOrEmpty() && !it.refreshToken.isNullOrEmpty()) {
                    appLocalDataRepositoryInterface.setToken(it.token)
                    appLocalDataRepositoryInterface.setRefreshToken(it.refreshToken)
                    loginResult.value = LoginResult.LoginSuccess
                } else {
                    loginResult.value = LoginResult.LoginError("failed to parse data")
                }
            }, {
                when (it) {
                    is HttpException -> {
                        // parser message error from server
                        val body = it.response()?.errorBody()
                        val gson = GsonBuilder().setLenient().create()
                        val adapter = gson.getAdapter(ErrorResponse::class.java)
                        try {
                            val response = adapter.fromJson(body?.string())
                            loginResult.value = LoginResult.LoginError(response.message ?: "HttpException")
                        } catch (e: IOException) {
                            loginResult.value = LoginResult.LoginError(e.localizedMessage ?: "IOException")
                        }
                    }
                    is ConnectException -> {
                        loginResult.value = LoginResult.LoginError("ConnectException")
                    }
                }
            })
            .addTo(compositeDisposable)
    }

    fun isLogin(): Boolean {
        return appLocalDataRepositoryInterface.isLogin()
    }
}