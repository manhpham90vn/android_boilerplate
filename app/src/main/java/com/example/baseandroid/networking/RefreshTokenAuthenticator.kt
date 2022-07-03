package com.example.baseandroid.networking

import android.util.Log
import com.example.baseandroid.data.LocalStorage
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

enum class RefreshTokenState {
    NOT_NEED_REFRESH,
    IS_REFRESHING,
    REFRESH_SUCCESS,
    REFRESH_ERROR
}

class RefreshTokenValidator {

    companion object {
        @Volatile private var INSTANCE: RefreshTokenValidator? = null
        fun getInstance(): RefreshTokenValidator =  INSTANCE ?: synchronized(this) {
            INSTANCE ?: RefreshTokenValidator().also { INSTANCE = it }
        }
    }

    var refreshTokenState: RefreshTokenState = RefreshTokenState.NOT_NEED_REFRESH
}

class RefreshTokenAuthenticator: Authenticator {

    private val lock: ReentrantLock = ReentrantLock(true)

    companion object {
        val TAG = RefreshTokenAuthenticator::class.java.simpleName
        fun log(message: String) {
            Log.d(TAG, message)
        }
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        lock.withLock {
            val isRefreshTokenRequest = response.request.url.toString().endsWith("refreshToken")
            if (response.code == 401 && !isRefreshTokenRequest) {
                if (RefreshTokenValidator.getInstance().refreshTokenState != RefreshTokenState.IS_REFRESHING) {
                    RefreshTokenValidator.getInstance().refreshTokenState = RefreshTokenState.IS_REFRESHING
                    val token = refreshToken()
                    if (token != null) {
                        LocalStorage.save(LocalStorage.Constants.token, token)
                        RefreshTokenValidator.getInstance().refreshTokenState = RefreshTokenState.REFRESH_SUCCESS
                    } else {
                        RefreshTokenValidator.getInstance().refreshTokenState = RefreshTokenState.REFRESH_ERROR
                    }
                }
                return newRequest(response)
            }
            return null
        }
    }

    private fun newRequest(response: Response): Request? {
        when (RefreshTokenValidator.getInstance().refreshTokenState) {
            RefreshTokenState.NOT_NEED_REFRESH -> return null
            RefreshTokenState.IS_REFRESHING -> {
                while (true) {
                    Thread.sleep(1000)
                    return newRequest(response)
                }
            }
            RefreshTokenState.REFRESH_SUCCESS -> {
                val currentAccessToken = LocalStorage.get(LocalStorage.Constants.token)
                return response
                    .request
                    .newBuilder()
                    .apply {
                        if (!currentAccessToken.isNullOrEmpty()) {
                            removeHeader("authorization")
                            addHeader("authorization", "Bearer $currentAccessToken")
                        }
                    }
                    .build()
            }
            RefreshTokenState.REFRESH_ERROR -> return null
        }
    }

    private fun refreshToken(): String? {
        val refreshToken = LocalStorage.get(LocalStorage.Constants.refreshToken)
        return if (!refreshToken.isNullOrEmpty()) {
            NetworkModule.provideAppApi().refresh(refreshToken).execute().body()?.token
        } else {
            null
        }
    }

}