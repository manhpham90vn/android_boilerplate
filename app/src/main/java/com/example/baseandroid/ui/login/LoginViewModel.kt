package com.example.baseandroid.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.repository.AppRemoteDataRepositoryInterface
import com.example.baseandroid.ui.base.BaseViewModel
import com.example.baseandroid.usecase.LoginUseCase
import com.example.baseandroid.usecase.LoginUseCaseParams
import com.example.baseandroid.utils.SingleLiveEvent
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

sealed class LoginResult {
    object LoginSuccess : LoginResult()
    data class LoginError(val message: String) : LoginResult()
}

class LoginViewModel @Inject constructor() : BaseViewModel() {

    @Inject lateinit var appRemoteDataRepositoryInterface: AppRemoteDataRepositoryInterface

    @Inject lateinit var appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface

    @Inject lateinit var loginUseCase: LoginUseCase

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val welcomeString = MutableLiveData<String>()

    private val _loginResult = SingleLiveEvent<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login() {
        email.value = "admin@admin.com"
        welcomeString.value = "Welcome admin@admin.com"
        password.value = "pwd12345"

        val params = LoginUseCaseParams(email.value.orEmpty(), password.value.orEmpty())

        loginUseCase.apply {
            execute(params)
            succeeded
                .subscribe {
                    if (!it.token.isNullOrEmpty() && !it.refreshToken.isNullOrEmpty()) {
                        appLocalDataRepositoryInterface.setToken(it.token)
                        appLocalDataRepositoryInterface.setRefreshToken(it.refreshToken)
                        _loginResult.postValue(LoginResult.LoginSuccess)
                    } else {
                        _loginResult.postValue(LoginResult.LoginError("Can not get token"))
                    }
                }
                .addTo(compositeDisposable)

            failed
                .subscribe {
                    singleLiveError.postValue(it)
                }
                .addTo(compositeDisposable)

            processing
                .subscribe {
                    isLoading.value = it
                }
                .addTo(compositeDisposable)
        }
    }

    fun isLogin(): Boolean {
        return appLocalDataRepositoryInterface.isLogin()
    }

    fun cleanData() {
        email.value = ""
        password.value = ""
    }

    override fun onCleared() {
        super.onCleared()
        loginUseCase.onCleared()
    }
}
