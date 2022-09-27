package com.example.baseandroid.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ConnectivityService {
    val isNetworkConnection: Boolean
}

class ConnectivityServiceImpl @Inject constructor(@ApplicationContext val context: Context) :
    ConnectivityService {
    override val isNetworkConnection: Boolean
        get() = context.getSystemService<ConnectivityManager>()?.let {
            return@let it.isNetworkConnection()
        } ?: false
}

private fun ConnectivityManager.isNetworkConnection(): Boolean {
    val activeNetwork = getNetworkCapabilities(activeNetwork) ?: return false
    return activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
}
