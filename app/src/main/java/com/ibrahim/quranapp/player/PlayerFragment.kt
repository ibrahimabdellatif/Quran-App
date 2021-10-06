package com.ibrahim.quranapp.player

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util
import com.ibrahim.quranapp.*
import com.ibrahim.quranapp.service.QuranPlayerService


class PlayerFragment : Fragment() {

    private var serverArgs = ""
    private var readerNameArgs = ""
    private var rewayaArgs = ""
    private var surahNameArgs = ""
    private var positionArgs = 0

    private val args: PlayerFragmentArgs by navArgs()

    private var title: TextView? = null
    private var rewaya: TextView? = null
    private var readerName: TextView? = null

    lateinit var player: SimpleExoPlayer
    lateinit var playerView: PlayerView

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            if (iBinder is QuranPlayerService.QuranServiceBinder) {

                player = iBinder.getExoPlayerInstance()!!
                playerView.player = player

                player.playWhenReady = iBinder.getPlayWhenReadyInstance()
                Log.d("playerFragment", "starting the service")
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("playerFragment", "service is disconnected")
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_player, container, false)

        getDataFromFileFragment()
        initUIElements(view)

        val intent = Intent(context, QuranPlayerService::class.java).apply {
            putExtra(GET_SURAH_NAME, surahNameArgs)
            putExtra(GET_READER_NAME, readerNameArgs)
            putExtra(GET_SERVER_ARGS, serverArgs)
            putExtra(GET_SERVER_ARGS_POSITION, positionArgs)

        }
        context?.let { Util.startForegroundService(it, intent) }
        context?.bindService(
            Intent(context, QuranPlayerService::class.java),
            connection,
            Context.BIND_AUTO_CREATE
        )
        playerView = view.findViewById(R.id.exo_player_view)
        logs()
        return view
    }

    private fun getDataFromFileFragment() {
        serverArgs = args.server.toString()
        readerNameArgs = args.readerName.toString()
        rewayaArgs = args.rewaya.toString()
        surahNameArgs = args.surahName.toString()
        positionArgs = args.position
    }


    private fun initUIElements(view: View) {
        title = view.findViewById(R.id.tv_title)
        rewaya = view.findViewById(R.id.tv_rewaya_name_mp)
        readerName = view.findViewById(R.id.tv_reader_name_mp)

        title?.text = surahNameArgs
        readerName?.text = readerNameArgs
        rewaya?.text = rewayaArgs
    }


    private fun logs() {
        Log.i("server bundle", serverArgs)
        Log.i("reader bundle", readerNameArgs)
        Log.i("rewaya bundle", rewayaArgs)
        Log.i("surah name bundle", surahNameArgs)
    }

}