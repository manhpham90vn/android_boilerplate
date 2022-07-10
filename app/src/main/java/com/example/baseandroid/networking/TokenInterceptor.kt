package com.example.baseandroid.networking

import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(private val appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain
                .request()
                .run {
                    newBuilder()
                        .apply {
                            val currentAccessToken = appLocalDataRepositoryInterface.getToken()
                            if (currentAccessToken.isNotEmpty()) {
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