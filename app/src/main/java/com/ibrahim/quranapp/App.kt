package com.ibrahim.quranapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.content.getSystemService

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var mediaChannel = NotificationChannel(
                MEDIA_CHANNEL_ID,
                "Quran App",
                NotificationManager.IMPORTANCE_HIGH
            )
            mediaChannel.audioAttributes
            mediaChannel.description

            var manger: NotificationManager = getSystemService(NotificationManager::class.java)
            manger.createNotificationChannel(mediaChannel)
        }
    }

}