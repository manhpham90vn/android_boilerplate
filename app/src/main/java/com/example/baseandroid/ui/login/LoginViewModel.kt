package com.example.baseandroid.ui.login

import androidx.lifecycle.MutableLiveData
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.repository.AppRemoteDataRepositoryInterface
import com.example.baseandroid.ui.base.BaseViewModel
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

sealed class LoginResult {
    object LoginSuccess : LoginResult()
    data class LoginError(val message: String) : LoginResult()
}

class LoginViewModel @Inject constructor() : BaseViewModel() {

    @Inject lateinit var appRemoteDataRepositoryInterface: AppRemoteDataRepositoryInterface

    @Inject lateinit var appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val welcomeString = MutableLiveData<String>()
    val loginResult = MutableLiveData<LoginResult>()

    fun login() {
        email.value = "admin@admin.com"
        welcomeString.value = "Welcome admin@admin.com"
        password.value = "pwd12345"
        isLoading.value = true

        appRemoteDataRepositoryInterface
            .callLogin(email.value.orEmpty(), password.value.orEmpty())
            .subscribe({
                if (!it.token.isNullOrEmpty() && !it.refreshToken.isNullOrEmpty()) {
                    appLocalDataRepositoryInterface.setToken(it.token)
                    appLocalDataRepositoryInterface.setRefreshToken(it.refreshToken)
                    loginResult.value = LoginResult.LoginSuccess
                } else {
                    loginResult.value = LoginResult.LoginError(it.message ?: "Server error")
                }
                isLoading.value = false
            }, {})
            .addTo(compositeDisposable)
    }

    fun isLogin(): Boolean {
        return appLocalDataRepositoryInterface.isLogin()
    }
}
