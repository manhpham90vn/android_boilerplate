package com.example.baseandroid.networking

import com.example.baseandroid.data.LocalStorage
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain
                .request()
                .run {
                    newBuilder()
                        .apply {
                            val currentAccessToken = LocalStorage.get(LocalStorage.Constants.token)
                            if (!currentAccessToken.isNullOrEmpty()) {
                                removeHeader("authorization")
                                addHeader("authorization", "Bearer $currentAccessToken")
                            }
                        }
                        .method(method, body)
                        .build()
                }
        )
    }

}