package com.example.baseandroid.repository

import com.example.baseandroid.data.remote.ApiClient
import com.example.baseandroid.models.RefreshTokenResponse
import retrofit2.Call
import javax.inject.Inject

interface RefreshTokenRepositoryInterface {
    fun refresh(token: String): Call<RefreshTokenResponse>
}

class RefreshTokenRepository @Inject constructor(private val apiClient: ApiClient): RefreshTokenRepositoryInterface {

    override fun refresh(token: String): Call<RefreshTokenResponse> {
        return apiClient.refresh(token)
    }

}