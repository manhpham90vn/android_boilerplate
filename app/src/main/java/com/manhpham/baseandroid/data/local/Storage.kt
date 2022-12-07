package com.manhpham.baseandroid.data.local

object StorageConstants {
    const val token = "token"
    const val refreshToken = "refreshToken"
    const val fcmToken = "fcmToken"
}

interface Storage {
    fun setString(key: String, value: String)
    fun getString(key: String): String
    fun clearString(key: String)
}
