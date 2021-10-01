package com.ibrahim.quranapp.data

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("data") var data: List<SurahData>
)