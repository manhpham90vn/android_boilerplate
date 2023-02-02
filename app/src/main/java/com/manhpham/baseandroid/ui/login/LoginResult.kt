package com.manhpham.baseandroid.ui.login

sealed class LoginResult {
    object LoginSuccess : LoginResult()
    data class LoginError(val message: String) : LoginResult()
}
