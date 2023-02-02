package com.manhpham.baseandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.manhpham.baseandroid.repository.AppLocalDataRepositoryInterface
import com.manhpham.baseandroid.ui.base.BaseViewModel
import com.manhpham.baseandroid.ui.login.LoginResult
import com.manhpham.baseandroid.usecase.LoginUseCase
import com.manhpham.baseandroid.usecase.LoginUseCaseParams
import com.manhpham.baseandroid.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

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

            complete
                .subscribe {
                    _loginResult.postValue(LoginResult.LoginSuccess)
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
