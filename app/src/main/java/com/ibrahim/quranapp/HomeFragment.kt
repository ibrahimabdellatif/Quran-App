package com.ibrahim.quranapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.quranapp.Adapters.HomeAdapter
import com.ibrahim.quranapp.network.QuranApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    var recyclerView: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var testTextView:TextView?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView(view)
        testTextView = view.findViewById(R.id.tv_test)
        initRetrofit()
    }

    private fun recyclerView(view: View) {

        recyclerView = view.findViewById(R.id.rv_home)
        layoutManager = LinearLayoutManager(activity)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = HomeAdapter(getFakeData())

    }

    //init some data
    fun getFakeData(): List<QuranData> = listOf(
        QuranData(0, "مشاري بن راشد", "", "حفص عن عاصم"),
        QuranData(0, "مشاري بن راشد", "", "حفص عن عاصم"),
        QuranData(0, "مشاري بن راشد", "", "حفص عن عاصم"),
        QuranData(0, "مشاري بن راشد", "", "حفص عن عاصم"),
        QuranData(0, "مشاري بن راشد", "", "حفص عن عاصم"),
        QuranData(0, "مشاري بن راشد", "", "حفص عن عاصم"),
        QuranData(0, "مشاري بن راشد", "", "حفص عن عاصم"),
        QuranData(0, "مشاري بن راشد", "", "حفص عن عاصم"),
        QuranData(0, "مشاري بن راشد", "", "حفص عن عاصم"),
        QuranData(0, "مشاري بن راشد", "", "حفص عن عاصم"),
        QuranData(0, "مشاري بن راشد", "", "حفص عن عاصم"),
        QuranData(0, "مشاري بن راشد", "", "حفص عن عاصم"),

        )

     fun initRetrofit(){
        QuranApi.retrofitService.getData().enqueue(object :Callback<List<QuranData>>{
            override fun onResponse(
                call: Call<List<QuranData>>,
                response: Response<List<QuranData>>
            ) {
                testTextView?.text = response.body()?.size.toString()
            }

            override fun onFailure(call: Call<List<QuranData>>, t: Throwable) {
                testTextView?.text = "this is failure state " + t.message
            }


        })
    }
}
