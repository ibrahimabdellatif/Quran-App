package com.ibrahim.quranapp.network

import com.google.gson.GsonBuilder
import com.ibrahim.quranapp.data.Data
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//   https://api.alquran.cloud/v1/surah
private const val BASE_URL = "https://api.alquran.cloud/"

//init moshi for retrofit
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val gson = GsonBuilder().serializeNulls().create()

//init retrofit
private val retrofit = Retrofit
    .Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()

/**
 * create public interface to call [getSurahData]
 */
interface SurahApiService {
    @GET("v1/surah")
    fun getSurahData(): Call<Data>
}

object SurahApi {
    val retrofitService: SurahApiService by lazy { retrofit.create(SurahApiService::class.java) }
}