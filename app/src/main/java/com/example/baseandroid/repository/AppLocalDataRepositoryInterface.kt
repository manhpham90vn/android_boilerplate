package com.example.baseandroid.repository

import com.example.baseandroid.data.local.Storage
import com.example.baseandroid.data.local.StorageConstants
import javax.inject.Inject

interface AppLocalDataRepositoryInterface {
    fun isLogin(): Boolean

    fun getToken(): String
    fun setToken(token: String)
    fun cleanToken()

    fun getRefreshToken(): String
    fun setRefreshToken(token: String)
    fun cleanRefreshToken()

    fun getFCMToken(): String
    fun setFCMToken(token: String)
    fun cleanFCMToken()
}

class AppLocalDataRepository @Inject constructor(private val storage: Storage): AppLocalDataRepositoryInterface {
    override fun isLogin(): Boolean {
        return storage.getString(StorageConstants.token).isNotEmpty() && storage.getString(
            StorageConstants.refreshToken).isNotEmpty()
    }

    override fun getToken(): String {
        return storage.getString(StorageConstants.token)
    }

    override fun setToken(token: String) {
        storage.setString(StorageConstants.token, token)
    }

    override fun cleanToken() {
        storage.clearString(StorageConstants.token)
    }

    override fun getRefreshToken(): String {
        return storage.getString(StorageConstants.refreshToken)
    }

    override fun setRefreshToken(token: String) {
        storage.setString(StorageConstants.refreshToken, token)
    }

    override fun cleanRefreshToken() {
        storage.clearString(StorageConstants.refreshToken)
    }

    override fun getFCMToken(): String {
        return storage.getString(StorageConstants.fcmToken)

    }

    override fun setFCMToken(token: String) {
        storage.setString(StorageConstants.fcmToken, token)
    }

    override fun cleanFCMToken() {
        storage.clearString(StorageConstants.fcmToken)
    }

}