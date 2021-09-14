package com.ibrahim.quranapp.Data

data class SurahData(
    //number of surah
    var number: Int = 0,
    // name of surah in arabic
    var name: String = "",
    // name of surah in english
    var englishName: String = "",
    // The surah type is Meccan or Medinan
    var revelationType: String = ""
)
