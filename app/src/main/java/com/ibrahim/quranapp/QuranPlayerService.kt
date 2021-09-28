package com.ibrahim.quranapp

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.IBinder
import android.util.Log
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.ibrahim.quranapp.player.PlayerFragment

class QuranPlayerService : Service() {

    var player: SimpleExoPlayer? = null
    lateinit var playerNotificationManager: PlayerNotificationManager
    var quranUrl = ""
    var notifyTitle = ""
    var notifyDescription = ""
    var mediaItem: MediaItem? = null
    override fun onCreate() {
        super.onCreate()
        Log.i("service quran url", quranUrl)
        Log.i("service surahname", notifyTitle)

//        "https://server8.mp3quran.net/ahmad_huth/001.mp3"
        mediaItem = MediaItem.fromUri(quranUrl)


        val trackSelector = DefaultTrackSelector(this).apply {
            setParameters(buildUponParameters().setMaxVideoSizeSd())
        }
        player = SimpleExoPlayer.Builder(this)
            .setTrackSelector(trackSelector)
            .build()

        player = SimpleExoPlayer.Builder(this)
            .build().also { exoPlayer ->

                mediaItem?.let { exoPlayer.setMediaItem(it) }
                exoPlayer.playWhenReady = true
//                exoPlayer.seekTo(0 , 0L)
                exoPlayer.prepare()
            }

        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
            applicationContext, PLAYBACK_CHANNEL_ID, R.string.PLAYBACK_NOTIFICATION_NAME,
            R.string.PLAYBACK_NOTIFICATION_ID,
            object : PlayerNotificationManager.MediaDescriptionAdapter {
                override fun getCurrentContentTitle(player: Player): CharSequence {
                    return notifyTitle
                }

                override fun createCurrentContentIntent(player: Player): PendingIntent? {
                    val intent = Intent(applicationContext, PlayerFragment::class.java)
                    return PendingIntent.getActivity(
                        applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }

                override fun getCurrentContentText(player: Player): CharSequence? {
                    return notifyDescription
                }

                override fun getCurrentLargeIcon(
                    player: Player,
                    callback: PlayerNotificationManager.BitmapCallback
                ): Bitmap? {
                    return null
                }
            }, object : PlayerNotificationManager.NotificationListener {
                override fun onNotificationPosted(
                    notificationId: Int,
                    notification: Notification,
                    ongoing: Boolean
                ) {
                    startForeground(notificationId, notification)
                }

                override fun onNotificationCancelled(
                    notificationId: Int,
                    dismissedByUser: Boolean
                ) {
                    stopSelf()
                }
            }
        )
        playerNotificationManager.setPlayer(player)

    }

    override fun onDestroy() {
        playerNotificationManager.setPlayer(null)
        player?.release()
        player = null
        super.onDestroy()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        super.onStartCommand(intent, flags, startId)

//        if (intent == null) return  START_STICKY
        quranUrl = intent?.getStringExtra("theurl").toString()
        notifyTitle = intent?.getStringExtra("thesurahname").toString()
        notifyDescription = intent?.getStringExtra("thereadername").toString()

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        quranUrl = intent?.getStringExtra("theurl").toString()
        return null
    }
}