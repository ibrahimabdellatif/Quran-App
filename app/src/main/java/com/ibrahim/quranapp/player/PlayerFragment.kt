package com.ibrahim.quranapp.player

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ibrahim.quranapp.R

class PlayerFragment : Fragment() {

    var serverArgs = ""
    var readerNameArgs = ""
    var rewayaArgs = ""

    var surahNameArgs = ""
    var positionArgs = 0

    var url = ""
    lateinit var runnable: Runnable
    private var handler = Handler()
    private var currentPosition = 0

    var title: TextView? = null
    var rewaya: TextView? = null
    var readerName: TextView? = null
    var startTime: TextView? = null
    var endTime: TextView? = null
    lateinit var seekBar: SeekBar
    lateinit var pause: ImageButton
    lateinit var next: ImageButton
    lateinit var previous: ImageButton
    var nextValue = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_player, container, false)


        getDataFromFileFragment()
        initUIElements(view)
        serverLink()
        logs()

        mediaPlayer(url)
        return view
    }

    fun getDataFromFileFragment() {
        //when using bundle
//        // bundle form home fragment
//        bundleServer = arguments?.getString("serverUrl").toString()
//        bundleReaderName = arguments?.getString("readerName").toString()
//        bundleRewaya = arguments?.getString("rewaya").toString()
//        // bundle form file fragment
//        bundleSurahName = arguments?.getString("surahName").toString()
//        bundlePosition = arguments?.getInt("filePosition")!!

//        using safeArgs
        val args: PlayerFragmentArgs by navArgs()
        serverArgs = args.server.toString()
        readerNameArgs = args.readerName.toString()
        rewayaArgs = args.rewaya.toString()
        surahNameArgs = args.surahName.toString()
        positionArgs = args.position
    }

    fun serverLink() {
        if (positionArgs + 1 in 1..9) {
            url = "$serverArgs/00${positionArgs + nextValue}.mp3"
        } else if (positionArgs + 1 in 10..99) {
            url = "$serverArgs/0${positionArgs + nextValue}.mp3"
        } else {
            url = "$serverArgs/${positionArgs + nextValue}.mp3"
        }
    }

    fun mediaPlayer(url: String) {
        val uri = Uri.parse(url)
        val mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )

            context?.let { setDataSource(it, uri) }
            prepare()
            start()
        }

        seekBar.progress = 0
        seekBar.max = mediaPlayer.duration

        var totalTime = timerLabel(mediaPlayer.duration)
        endTime?.text = totalTime

        mediaPlayer.start()
        pause.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                pause.setImageResource(R.drawable.ic_play_arrow_24)
            } else {
                pause.setImageResource(R.drawable.ic_pause_24)
                mediaPlayer.start()
            }
        }

        //to change seekBar position while file is playing we need to runnable object and handler
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, position: Int, changed: Boolean) {
                //now if seekBar position is changed the mp3 file will go to this position
                if (changed) {
                    mediaPlayer.seekTo(position)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        runnable = Runnable {
            currentPosition = mediaPlayer.currentPosition
            seekBar.progress = currentPosition
            startTime?.text = timerLabel(currentPosition)
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
        mediaPlayer.setOnCompletionListener {
            seekBar.progress = 0
        }


        next.setOnClickListener {
            mediaPlayer.stop()
            nextMedia()
        }

        previous.setOnClickListener {
            mediaPlayer.stop()
            previousMedia()

        }

    }

    fun initUIElements(view: View) {
        title = view.findViewById(R.id.tv_title)
        rewaya = view.findViewById(R.id.tv_rewaya_name_mp)
        readerName = view.findViewById(R.id.tv_reader_name_mp)
        startTime = view.findViewById(R.id.tv_start_time)
        endTime = view.findViewById(R.id.tv_end_time)
        seekBar = view.findViewById(R.id.seek_bar)
        pause = view.findViewById(R.id.btn_pause)
        next = view.findViewById(R.id.btn_next)
        previous = view.findViewById(R.id.btn_previous)

        pause.setImageResource(R.drawable.ic_pause_24)
        next.setImageResource(R.drawable.ic_skip_next_24)
        previous.setImageResource(R.drawable.ic_skip_previous_24)

        title?.text = surahNameArgs
        readerName?.text = readerNameArgs
        rewaya?.text = rewayaArgs
    }

    fun nextMedia() {
        if (positionArgs + nextValue < 114) nextValue++

        serverLink()
        mediaPlayer(url)


        title?.text = ""
        readerName?.text = readerNameArgs
        rewaya?.text = rewayaArgs
    }

    fun previousMedia() {
        if (positionArgs + nextValue > 1) nextValue--

        serverLink()
        mediaPlayer(url)
    }

    fun timerLabel(duration: Int): String {
        var timerLable = ""
        val min = duration / 1000 / 60
        val sec = duration / 1000 % 60

        timerLable += "$min:"
        if (sec < 10) timerLable += "0"
        timerLable += sec

        return timerLable
    }

    private fun logs() {
        Log.i("url", url)
        Log.i("server bundle", serverArgs)
        Log.i("reader bundle", readerNameArgs)
        Log.i("rewaya bundle", rewayaArgs)
        Log.i("surah name bundle", surahNameArgs)
    }

}