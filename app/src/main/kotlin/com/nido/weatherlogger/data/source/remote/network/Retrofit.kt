package com.nido.weatherlogger.data.source.remote.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(15, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    // .addInterceptor(LoggingInterceptor())
    // .addNetworkInterceptor(LoggingInterceptor())
    .build()

// Build the Moshi object that Retrofit will be using, making
// sure to add the Kotlin adapter for full Kotlin compatibility.
private val moshiConverterFactory: MoshiConverterFactory = MoshiConverterFactory.create()

/**
 * Use the retrofit builder to build a retrofit object using a moshi
 * converter with our [MoshiConverterFactory] object pointing to the desired URL
 */
val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://api.openweathermap.org/")
    .client(okHttpClient)
    .addConverterFactory(moshiConverterFactory)
    .build()
