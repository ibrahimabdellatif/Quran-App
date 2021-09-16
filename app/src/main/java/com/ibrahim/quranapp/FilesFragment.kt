package com.ibrahim.quranapp

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.quranapp.adapter.FilesAdapter
import com.ibrahim.quranapp.data.Data
import com.ibrahim.quranapp.data.SurahData
import com.ibrahim.quranapp.network.SurahApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilesFragment : Fragment(), FilesAdapter.OnItemClickListener {
    var testTextView: TextView? = null
    var recyclerView: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var data: List<Data>? = null
    var serverValue: String? = null

    //private var uri:Uri? =null //Uri.parse("https://server8.mp3quran.net/ahmad_huth/001.mp3")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_files, container, false)

        serverValue = arguments?.getString("server")
        Log.i("on file fragment ", serverValue.toString())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testTextView = view.findViewById(R.id.tv_test)
        initRetrofit(view)
//        surahRecyclerView(view , getFakeData())
    }

    fun surahRecyclerView(view: View, surahData: List<SurahData>) {
        recyclerView = view.findViewById(R.id.rv_files)
        layoutManager = LinearLayoutManager(activity)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = FilesAdapter(surahData, this)
    }

    fun initRetrofit(view: View) {
        SurahApi.retrofitService.getSurahData().enqueue(object : Callback<Data> {
            override fun onResponse(
                call: Call<Data>,
                response: Response<Data>
            ) {
                var newData = response
                newData.body()?.let { surahRecyclerView(view, it.data) }
                testTextView?.visibility = View.GONE
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                testTextView?.visibility = View.VISIBLE
                testTextView?.text = "this is failure state " + t.message
            }

        })

    }

    override fun onItemClick(position: Int) {
        super.onItemClick(position)

        //
        var testUrl = ""
        if (position + 1 in 1..9) {
            testUrl = "$serverValue/00${position + 1}.mp3"
        } else if (position + 1 in 10..99) {
            testUrl = "$serverValue/0${position + 1}.mp3"
        } else {
            testUrl = "$serverValue/${position + 1}.mp3"
        }

        var uri = Uri.parse(testUrl)
        mediaPlayer(uri)
        Toast.makeText(context, "${position + 1}", Toast.LENGTH_SHORT).show()


    }

    fun mediaPlayer(uri: Uri) {
        var uri = uri
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
        mediaPlayer.start()
    }

    fun getFakeData(): List<SurahData> = listOf(
        SurahData(0, "سورة البقرة", "albaqarh", "maccan"),
        SurahData(0, "سورة البقرة", "albaqarh", "maccan"),
        SurahData(0, "سورة البقرة", "albaqarh", "maccan"),
        SurahData(0, "سورة البقرة", "albaqarh", "maccan"),
        SurahData(0, "سورة البقرة", "albaqarh", "maccan"),
        SurahData(0, "سورة البقرة", "albaqarh", "maccan"),
        SurahData(0, "سورة البقرة", "albaqarh", "maccan"),
        SurahData(0, "سورة البقرة", "albaqarh", "maccan")


    )


}