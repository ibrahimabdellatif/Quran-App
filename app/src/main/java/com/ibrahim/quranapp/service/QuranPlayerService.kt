package com.ibrahim.quranapp.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.os.IBinder
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.ibrahim.quranapp.*
import com.ibrahim.quranapp.player.PlayerFragment

class QuranPlayerService : Service() {

    private var player: SimpleExoPlayer? = null
    private lateinit var playerNotificationManager: PlayerNotificationManager
    private var quranUrl = ""
    private var serverUrl = ""
    private var positionOfItem = 0
    private var notifyTitle = ""
    private var notifyDescription = ""
    private var mediaItem: MediaItem? = null
    private lateinit var playList: ArrayList<MediaItem>
    private var playWhenReady = true
    private val currentWindow = 0
    private val playbackPosition = 0L

    @SuppressLint("WrongConstant")
    fun initPlayer(url: String) {


        //init the simple player
        player = SimpleExoPlayer.Builder(applicationContext)
            .build()
            .also {

                for (i in 0..113) {

                    playList = arrayListOf()
                    serverLink(serverUrl, i, positionOfItem)

                    mediaItem = MediaItem.fromUri(quranUrl)
                    playList.add(mediaItem!!)
                    it.addMediaItems(playList)
                }

                it.playWhenReady = playWhenReady
                it.seekTo(currentWindow, playbackPosition)
                it.prepare()
            }


        //adding audio attributes
        val audioAttributes = com.google.android.exoplayer2.audio.AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .build()

        player!!.setAudioAttributes(audioAttributes, true)


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

    private fun serverLink(server: String, next: Int, position: Int) {
        quranUrl = if (position + 1 in 1..9) {
            "$server/00${position + next}.mp3"
        } else if (position + 1 in 10..99) {
            "$server/0${position + next}.mp3"
        } else {
            "$server/${position + next}.mp3"
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        super.onStartCommand(intent, flags, startId)

        serverUrl = intent?.getStringExtra(GET_SERVER_ARGS).toString()
        positionOfItem = intent?.getIntExtra(GET_SERVER_ARGS_POSITION, 0)!!
        notifyTitle = intent.getStringExtra(GET_SURAH_NAME).toString()
        notifyDescription = intent.getStringExtra(GET_READER_NAME).toString()

        serverLink(serverUrl, 0, positionOfItem)
        initPlayer(quranUrl)

        return START_STICKY
    }


    override fun onBind(intent: Intent?): IBinder? {
        return QuranServiceBinder()
    }


    override fun onDestroy() {
        playerNotificationManager.setPlayer(null)
        player?.release()
        player?.stop()
        super.onDestroy()
        stopSelf()
    }

    // destroy the app when remove form recent applications
    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        onDestroy()
    }


    inner class QuranServiceBinder : Binder() {
        fun getExoPlayerInstance() = player
        fun getPlayWhenReadyInstance() = playWhenReady
    }

}