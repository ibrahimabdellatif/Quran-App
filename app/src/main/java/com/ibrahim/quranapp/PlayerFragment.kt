package com.ibrahim.quranapp

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class PlayerFragment : Fragment() {

    var bundleUri = ""
    var bundleReaderName = ""
    var bundleSurahName = ""
    var bundleRewaya = ""
    lateinit var runnable: Runnable
    private var handler = Handler()
    var mediaValue :Int = 0

     var title:TextView?=null
     var rewaya :TextView?=null
     var readerName :TextView?=null
    lateinit var seekBar: SeekBar
    lateinit var pause :Button
    lateinit var next :Button
    lateinit var previous :Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_player, container, false)
        bundleUri = arguments?.getString("url").toString()
        bundleSurahName = arguments?.getString("surahName").toString()
        bundleReaderName = arguments?.getString("readerName").toString()
        bundleRewaya = arguments?.getString("rewaya").toString()

        Log.i("onview" , bundleUri)

        initUIElements(view)
        mediaPlayer(bundleUri)

        return view
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

        mediaPlayer.start()
        pause.setOnClickListener {
            if (mediaPlayer.isPlaying){
                mediaPlayer.pause()
            }else{
                mediaPlayer.start()
            }
        }

        //to change seekBar position while file is playing we need to runnable object and handler
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, position: Int, changed: Boolean) {
                //now if seekBar position is changed the mp3 file will go to this position
                if (changed){
                    mediaPlayer.seekTo(position)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        runnable = Runnable {
            seekBar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable , 1000)
        }
        handler.postDelayed(runnable , 1000)
        mediaPlayer.setOnCompletionListener {
            seekBar.progress = 0
        }


        next.setOnClickListener {
            nextMedia()
            mediaPlayer.pause()
            Log.i("test" , bundleUri)
            mediaPlayer.start()
        }
//
//        previous.setOnClickListener {
//            mediaPlayer.
//        }

    }

    fun initUIElements(view: View){
        title = view.findViewById(R.id.tv_title)
        rewaya = view.findViewById(R.id.tv_rewaya_name_mp)
        readerName = view.findViewById(R.id.tv_reader_name_mp)
        seekBar = view.findViewById(R.id.seek_bar)
        pause = view.findViewById(R.id.btn_pause)
        next = view.findViewById(R.id.btn_next)
        previous = view.findViewById(R.id.btn_previous)


        title?.text = bundleSurahName
        readerName?.text = bundleReaderName
        rewaya?.text = bundleRewaya
    }

    fun nextMedia(){
        var fileFragment = FilesFragment()
        var bundleNext = Bundle()
        mediaValue++
        bundleNext.putInt("next" ,mediaValue)
        fileFragment.arguments = bundleNext
    }
    fun previousMedia(){

    }
}