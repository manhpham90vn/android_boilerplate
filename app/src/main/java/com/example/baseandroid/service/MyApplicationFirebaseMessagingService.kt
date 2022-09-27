package com.example.baseandroid.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.baseandroid.R
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MyApplicationFirebaseMessagingService : FirebaseMessagingService() {

    @Inject lateinit var appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("From: ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            Timber.d("Message data payload: ${remoteMessage.data}")
        }
        remoteMessage.notification?.let {
            Timber.d("Message Notification Body: ${it.body}")
        }
        sendNotification(remoteMessage)
    }

    override fun onNewToken(token: String) {
        Timber.d("Refreshed token: $token")
        appLocalDataRepositoryInterface.setFCMToken(token)
    }

    private fun sendNotification(message: RemoteMessage) {
        val channelId = getString(R.string.app_name)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        val uuid = UUID.randomUUID().hashCode()
        notificationManager.notify(uuid, notificationBuilder.build())
    }
}
