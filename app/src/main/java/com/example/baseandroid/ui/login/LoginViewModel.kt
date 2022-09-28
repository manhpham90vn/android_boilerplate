package com.example.baseandroid.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.ui.base.BaseViewModel
import com.example.baseandroid.usecase.LoginUseCase
import com.example.baseandroid.usecase.LoginUseCaseParams
import com.example.baseandroid.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

sealed class LoginResult {
    object LoginSuccess : LoginResult()
    data class LoginError(val message: String) : LoginResult()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface,
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val welcomeString = MutableLiveData<String>()

    private val _loginResult = SingleLiveEvent<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    init {
        loginUseCase.apply {
            succeeded
                .subscribe {
                    if (it) {
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
                    isLoadingSingleLive.postValue(it)
                }
                .addTo(compositeDisposable)
        }
    }

    fun login() {
        email.value = "admin@admin.com"
        welcomeString.value = "Welcome admin@admin.com"
        password.value = "pwd12345"
        val params = LoginUseCaseParams(email.value.orEmpty(), password.value.orEmpty())
        loginUseCase.execute(params)
    }

    fun retryLogin() {
        loginUseCase.retry()
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
