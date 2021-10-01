package com.ibrahim.quranapp.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ibrahim.quranapp.data.QuranData

class HomeViewModel : ViewModel() {
    var quranData = MutableLiveData<QuranData?>()

    //    var quranData =
    init {
        Log.i("homeViewModel", "home view model is created")
        quranData = MutableLiveData()
    }


}