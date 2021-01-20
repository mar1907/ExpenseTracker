package com.example.expensetracker.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.exchangeratesapi.io/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ExchangeRateAPIService {
    @GET("latest?symbols=USD,EUR&base=RON")
    suspend fun getProperties(): ExchangeRateJSON
}

object ExchangeRateAPI {
    val retrofitService: ExchangeRateAPIService by lazy {
        retrofit.create(ExchangeRateAPIService::class.java)
    }
}
