package com.example.baseandroid.networking

import android.content.Context
import com.example.baseandroid.data.LocalStorage
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class RefreshTokenAuthenticator(private val context: Context): Authenticator {

    var count = 0

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401 || response.request.header("authorization").isNullOrEmpty()) {
            if (count < 1) {
                count += 1
                return newRequest(response)
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

}