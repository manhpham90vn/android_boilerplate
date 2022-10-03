package com.example.baseandroid.extensions

import android.util.Patterns

fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()