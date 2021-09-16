package com.ibrahim.quranapp.network

import com.ibrahim.quranapp.data.QuranData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

//   api/reciters/  >> this for get method
private const val BASE_URL = "https://qurani-api.herokuapp.com/"

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
 * create public interface to call [getData]
 */
interface QuranApiService {
    @GET("api/reciters/")
     fun getData(): Call<List<QuranData>>
}

object QuranApi {
    val retrofitService: QuranApiService by lazy { retrofit.create(QuranApiService::class.java) }
}