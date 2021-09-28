package com.ibrahim.quranapp

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.IBinder
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.ibrahim.quranapp.player.PlayerFragment

class QuranPlayerService : Service() {

    var player: SimpleExoPlayer? = null
    lateinit var playerNotificationManager: PlayerNotificationManager

    // later define media session variables
    var quranUrl = ""
    var notifyTitle = ""
    var notifyDescription = ""
    var mediaItem: MediaItem? = null
    override fun onCreate() {
        super.onCreate()
    }

    @SuppressLint("WrongConstant")
    fun initPlayer(url: String) {
        mediaItem = MediaItem.fromUri(url)

        player = ExoPlayerFactory.newSimpleInstance(applicationContext, DefaultTrackSelector())
        val defaultTrackSelector =
            DefaultDataSourceFactory(
                applicationContext,
                Util.getUserAgent(applicationContext, "hello")
            )
        var mediaSource = ExtractorMediaSource.Factory(defaultTrackSelector)
            .createMediaSource(mediaItem!!)

        player!!.prepare(mediaSource)

        var audioAttributes = com.google.android.exoplayer2.audio.AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .build()
        player!!.setAudioAttributes(audioAttributes, true)
        player!!.playWhenReady = true
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
                    notificationId: Int
                ) {
                    stopSelf()
                }
            }
        )
        playerNotificationManager.setPlayer(player)

    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        super.onStartCommand(intent, flags, startId)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        quranUrl = intent?.getStringExtra(GET_URI).toString()
        notifyTitle = intent?.getStringExtra(GET_SURAH_NAME).toString()
        notifyDescription = intent?.getStringExtra(GET_READER_NAME).toString()
        initPlayer(quranUrl)
    }


    override fun onDestroy() {
        playerNotificationManager.setPlayer(null)
        player?.release()
//        player = null
        player?.stop()
        super.onDestroy()
        stopSelf()
    }

    //destroy the app when remove form recent applications
//    override fun onTaskRemoved(rootIntent: Intent?) {
//        super.onTaskRemoved(rootIntent)
//        onDestroy()
//    }


}