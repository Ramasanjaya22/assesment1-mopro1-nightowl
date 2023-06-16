package com.d3if3021.myapplication.network

import com.d3if3021.myapplication.data.DataModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.akuari.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface TebakGambarService {
    @GET("games/tebakgambar")
    suspend fun getTebakGambar(): Response<DataModel>
}

object TebakGambarApi {
    val service: TebakGambarService by lazy {
        retrofit.create(TebakGambarService::class.java)
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }
