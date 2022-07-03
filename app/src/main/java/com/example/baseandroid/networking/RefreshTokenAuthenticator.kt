package com.example.baseandroid.networking

import android.content.Context
import android.util.Log
import com.example.baseandroid.data.LocalStorage
import com.example.baseandroid.models.RefreshTokenResponse
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Call
import retrofit2.Callback

class RefreshTokenAuthenticator(private val context: Context): Authenticator {

    var count = 0

    companion object {
        val TAG = RefreshTokenAuthenticator::class.java.simpleName
        fun log(message: String) {
            Log.d(TAG, message)
        }
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val isRefreshTokenRequest = response.request.url.toString().endsWith("refreshToken")
        if (response.code == 401 && !isRefreshTokenRequest) {
            if (count < 1) {
                count += 1
                refreshToken()
            }
        }
        return null
    }

    private fun newRequest(response: Response): Request? {
        val currentAccessToken = LocalStorage(context).get(LocalStorage.token)
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

    private fun refreshToken() {
        val refreshToken = LocalStorage(context).get(LocalStorage.refreshToken)
        if (!refreshToken.isNullOrEmpty()) {
            NetworkModule(context).provideAppApi().refresh(refreshToken).enqueue(object: Callback<RefreshTokenResponse> {
                override fun onResponse(
                    call: Call<RefreshTokenResponse>,
                    response: retrofit2.Response<RefreshTokenResponse>
                ) {
                    Log.d("RefreshTokenAuthenticator", response.body()?.token ?: "Error get refresh token")
                }

                override fun onFailure(call: Call<RefreshTokenResponse>, t: Throwable) {
                    Log.d("RefreshTokenAuthenticator", "Error get refresh token")
                }
            })
        }
    }

}

