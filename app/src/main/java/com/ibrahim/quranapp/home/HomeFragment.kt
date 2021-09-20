package com.ibrahim.quranapp.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.quranapp.R
import com.ibrahim.quranapp.adapter.HomeAdapter
import com.ibrahim.quranapp.data.QuranData
import com.ibrahim.quranapp.network.QuranApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), HomeAdapter.OnItemClickListener {

    private lateinit var viewModel: HomeViewModel

    var quranData: List<QuranData>? = null
    var recyclerView: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var testTextView: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("homefragment", "from fragment")
//        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
//
//        viewModel.quranData.observe(viewLifecycleOwner , Observer { quranList ->
//
//        })

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        testTextView = view.findViewById(R.id.tv_test)
        initRetrofit(view)
    }

    private fun recyclerView(view: View, quranData: List<QuranData>) {

        recyclerView = view.findViewById(R.id.rv_home)
        layoutManager = LinearLayoutManager(activity)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = HomeAdapter(quranData, this)

    }

    fun initRetrofit(view: View) {
        QuranApi.retrofitService.getData().enqueue(object : Callback<List<QuranData>> {
            override fun onResponse(
                call: Call<List<QuranData>>,
                response: Response<List<QuranData>>
            ) {
                //testTextView?.text = response.body()?.size.toString()
                quranData = response.body()!!
                recyclerView(view, quranData!!)
            }

            override fun onFailure(call: Call<List<QuranData>>, t: Throwable) {
                //  testTextView?.text = "this is failure state " + t.message
            }


        })
    }

    @SuppressLint("ResourceType")
    override fun onItemClick(position: Int) {
        Toast.makeText(context, "hello : ${quranData?.get(position)?.Server}", Toast.LENGTH_SHORT)
            .show()


        val server = quranData?.get(position)?.Server
        val readerName = quranData?.get(position)?.name
        val rewaya = quranData?.get(position)?.rewaya

        //using saveArgs form navigation component to pass data between fragments
        var action = HomeFragmentDirections.actionHomeFragmentToListFragment(server, readerName, rewaya)
        view?.findNavController()?.navigate(action)


        //when use bundle
//        var navController = view?.findNavController()
//        navController?.navigate(
//            R.id.action_homeFragment_to_listFragment, bundleOf(
//                "server" to server,
//                "readerName" to readerName,
//                "rewaya" to rewaya
//            )
//        )

    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        var inflater : MenuInflater = MenuInflater(context)
//        inflater.inflate(R.menu.home_search_menu , menu)
//
//        var item : MenuItem = menu.findItem(R.id.home_search)
//
//        return
//    }
}
