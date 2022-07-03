package com.example.baseandroid.data

import android.content.Context
import com.example.baseandroid.MyApplication

object LocalStorage {
    object Constants {
        const val token = "token"
        const val refreshToken = "refreshToken"
    }

    private val sharedPreferences = MyApplication.getContext().getSharedPreferences(MyApplication.getContext().packageName, Context.MODE_PRIVATE)

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