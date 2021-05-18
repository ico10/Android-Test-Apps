package com.sfa.android.currentbitcointracker.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.coindesk.com/v1/bpi/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface BitcoinApiService {
    @GET("currentprice.json")
    fun getPriceIndexAsync(): Deferred<PriceIndex>
}

object BitcoinApi {
    val retrofitService: BitcoinApiService by lazy {
        retrofit.create(BitcoinApiService::class.java)
    }
}
