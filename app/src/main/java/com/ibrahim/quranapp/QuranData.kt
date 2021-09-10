package com.ibrahim.quranapp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class QuranData(
    @SerializedName("id")
    var readerID: Long = 0L,

    @SerializedName("name")
    var readerName: String="",

    @SerializedName("server")
    var serverUrl: String="",

    @SerializedName("rewaya")
    var rewaya: String="",

    @SerializedName("count")
    var numOfFiles: Int =0,

    @SerializedName("letter")
    var fLetterOfReader: Char='a',

    @SerializedName("suras")
    var surasNumber: Int =0
) :Serializable
