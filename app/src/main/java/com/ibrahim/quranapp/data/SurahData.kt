package com.ibrahim.quranapp.data

import com.google.gson.annotations.SerializedName

data class SurahData(
    //number of surah
    @SerializedName("number")
    var number: Int = 0,
    // name of surah in arabic
    @SerializedName("name")
    var name: String = "",
    // name of surah in english
    @SerializedName("englishName")
    var englishName: String = "",
    // The surah type is Meccan or Medinan
    @SerializedName("revelationType")
    var revelationType: String = "",

    var surahData: List<SurahData>? = null
)
