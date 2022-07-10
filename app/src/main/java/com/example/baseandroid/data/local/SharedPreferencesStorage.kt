package com.example.baseandroid.data.local

import android.content.Context
import javax.inject.Inject

class SharedPreferencesStorage @Inject constructor(context: Context): Storage {

    private val sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    override fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }

    override fun clearString(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}