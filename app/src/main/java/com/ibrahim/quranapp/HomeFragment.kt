package com.ibrahim.quranapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.quranapp.Adapters.HomeAdapter

class HomeFragment : Fragment() {

    var recyclerView: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView(view)
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
}