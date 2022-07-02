package com.example.baseandroid.data

import android.content.Context

class LocalStorage(private val context: Context) {
    companion object {
        const val token = "token"
        const val refreshToken = "refreshToken"
    }

    private val sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun save(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun get(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun clear(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}