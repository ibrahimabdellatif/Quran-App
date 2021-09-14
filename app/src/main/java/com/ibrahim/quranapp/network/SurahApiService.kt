package com.ibrahim.quranapp.network

import com.ibrahim.quranapp.Data.SurahData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

//   https://api.alquran.cloud/v1/surah
private const val BASE_URL = "https://api.alquran.cloud/"

//init moshi for retrofit
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//init retrofit
private val retrofit = Retrofit
    .Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * create public interface to call [getSurahData]
 */
interface SurahApiService {
    @GET("v1/surah")
    fun getSurahData(): Call<List<SurahData>>
}

object SurahApi {
    val retrofitService: SurahApiService by lazy { retrofit.create(SurahApiService::class.java) }
}