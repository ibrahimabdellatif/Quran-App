package com.ibrahim.quranapp

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.session.MediaSession
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat

class QuranNotification {

    lateinit var mediaSession :MediaSession
    @SuppressLint("RemoteViewLayout", "RestrictedApi")
    fun customNotification(context: Context){
        var remoteView = RemoteViews(context.packageName , R.layout.notifiaction_player)

        var notificationCompat : NotificationCompat.Builder =  NotificationCompat.Builder(context , MEDIA_CHANNEL_ID)

        var notificationManager :NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //todo make it with PlayerFragment instead of MainActivity with pending intent
        var intentNotify = Intent(context , MainActivity::class.java)
        intentNotify.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intentNotify.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        var play = Intent("ACTION_PLAY")
        var pause = Intent("ACTION_PAUSE")
        var next = Intent("ACTION_NEXT")
        var previous = Intent("ACTION_PREVIOUS")

        var pPlay = PendingIntent.getBroadcast(context , 0 , play , PendingIntent.FLAG_UPDATE_CURRENT)
        remoteView.setOnClickPendingIntent(R.id.btn_play_notify , pPlay)
        var pPause = PendingIntent.getBroadcast(context , 0 , pause , PendingIntent.FLAG_UPDATE_CURRENT)
        remoteView.setOnClickPendingIntent(R.id.btn_pause_notify , pPause)
        var pNext = PendingIntent.getBroadcast(context , 0 , next , PendingIntent.FLAG_UPDATE_CURRENT)
        remoteView.setOnClickPendingIntent(R.id.btn_next_notify , pNext)
        var pPrevious = PendingIntent.getBroadcast(context , 0 , previous , PendingIntent.FLAG_UPDATE_CURRENT)
        remoteView.setOnClickPendingIntent(R.id.btn_previous_notify , pPrevious)

        var pendingIntent = PendingIntent.getActivity(context , 0 , intentNotify , PendingIntent.FLAG_UPDATE_CURRENT)
        notificationCompat.setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_music_note_24)
            .setAutoCancel(true)
            .setCustomBigContentView(remoteView)
            .setContentTitle("surah name here")
            .setContentText("reader name is here")
            .addAction(R.drawable.ic_skip_previous_24 , "previous" , pPrevious)
            .addAction(R.drawable.ic_play_arrow_24 , "play" , pPlay)
            .addAction(R.drawable.ic_skip_next_24 , "next" , pNext)
//            .bigContentView.setTextViewText(R.id.tv_surah_name_notify , "سورة الاعلي")
            .build()

        notificationManager.notify(1 , notificationCompat.build())
    }
}