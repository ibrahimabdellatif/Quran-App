package com.ibrahim.quranapp.files

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.quranapp.player.PlayerFragment
import com.ibrahim.quranapp.R
import com.ibrahim.quranapp.adapter.FilesAdapter
import com.ibrahim.quranapp.data.Data
import com.ibrahim.quranapp.data.SurahData
import com.ibrahim.quranapp.network.SurahApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilesFragment : Fragment(), FilesAdapter.OnItemClickListener {
    var recyclerView: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    var bundleServer: String? = null

    //    var bundleSurasValues:String?=null
    var bundleReaderName: String? = null
    var bundleRewaya: String? = null
    var url = ""
    var surahDataList: List<SurahData>? = null


    //bundle url to player fragment
    val playerFragment = PlayerFragment()
    val bundle = Bundle()

    //private var uri:Uri? =null //Uri.parse("https://server8.mp3quran.net/ahmad_huth/001.mp3")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_files, container, false)

        bundleServer = arguments?.getString("server")
        bundleReaderName = arguments?.getString("readerName")
        bundleRewaya = arguments?.getString("rewaya")
//        testTextView = view.findViewById(R.id.tv_test)

        initRetrofit(view)
        return view
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
                newData.body()?.let {
                    surahDataList = it.data
                    surahRecyclerView(view, surahDataList!!)
                }
//                testTextView?.visibility = View.GONE
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
//                testTextView?.visibility = View.VISIBLE
//                testTextView?.text = "this is failure state " + t.message
            }

        })

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onItemClick(position: Int) {
        super.onItemClick(position)



        bundle.putString("serverUrl", bundleServer)
        bundle.putString("readerName", bundleReaderName)
        bundle.putString("rewaya", bundleRewaya)
        bundle.putString("surahName", surahDataList?.get(position)?.name)
        bundle.putInt("filePosition", position + 1)


        playerFragment.arguments = bundle

        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment, playerFragment)?.commit()



        Toast.makeText(context, "${url}", Toast.LENGTH_SHORT).show()

    }

}