package com.ibrahim.quranapp.player

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util
import com.ibrahim.quranapp.*


class PlayerFragment : Fragment() {

    private var serverArgs = ""
    private var readerNameArgs = ""
    private var rewayaArgs = ""
    private var surahNameArgs = ""
    private var positionArgs = 0
    private val args: PlayerFragmentArgs by navArgs()
    private var url = ""
    private var title: TextView? = null
    private var rewaya: TextView? = null
    private var readerName: TextView? = null
    private var nextValue = 0

    lateinit var playerView: PlayerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_player, container, false)

        getDataFromFileFragment()
        initUIElements(view)
        serverLink()

        val intent = Intent(context, QuranPlayerService::class.java).apply {
            putExtra(GET_URI, url)
            putExtra(GET_SURAH_NAME, surahNameArgs)
            putExtra(GET_READER_NAME, readerNameArgs)
        }
        context?.let { Util.startForegroundService(it, intent) }

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

    private fun serverLink() {
        if (positionArgs + 1 in 1..9) {
            url = "$serverArgs/00${positionArgs + nextValue}.mp3"
        } else if (positionArgs + 1 in 10..99) {
            url = "$serverArgs/0${positionArgs + nextValue}.mp3"
        } else {
            url = "$serverArgs/${positionArgs + nextValue}.mp3"
        }
    }

    private fun initUIElements(view: View) {
        title = view.findViewById(R.id.tv_title)
        rewaya = view.findViewById(R.id.tv_rewaya_name_mp)
        readerName = view.findViewById(R.id.tv_reader_name_mp)

        title?.text = surahNameArgs
        readerName?.text = readerNameArgs
        rewaya?.text = rewayaArgs
    }

    fun nextMedia() {
        if (positionArgs + nextValue < 114) nextValue++

        serverLink()


        title?.text = ""
        readerName?.text = readerNameArgs
        rewaya?.text = rewayaArgs
    }

    fun previousMedia() {
        if (positionArgs + nextValue > 1) nextValue--

        serverLink()
    }


    private fun logs() {
        Log.i("url", url)
        Log.i("server bundle", serverArgs)
        Log.i("reader bundle", readerNameArgs)
        Log.i("rewaya bundle", rewayaArgs)
        Log.i("surah name bundle", surahNameArgs)
    }

}