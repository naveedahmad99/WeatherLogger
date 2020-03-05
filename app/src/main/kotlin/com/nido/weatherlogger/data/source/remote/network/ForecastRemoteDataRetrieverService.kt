package com.nido.weatherlogger.data.source.remote.network

import retrofit2.http.GET

/**
 * A public interface that exposes the [getForecastData] methods.
 */
interface ForecastRemoteDataRetrieverService {
    /**
     * A suspendable function to be executed
     * to get forecast data for different cities.
     */
    @GET("data/2.5/group?id=587084,584923,147613,585156,586482&units=metric&appid=a3f5cd4926414edbf39ba29587ff39ef")
    suspend fun getForecastData(): retrofit2.Response<ApiModel>
}

/**
 * An object that represents [forecastDataRetrieverService]
 * to consume eDoc verification service.
 */
object ForecastRemoteDataRetrieverApi {
    val forecastDataRetrieverService: ForecastRemoteDataRetrieverService by lazy {
        retrofit.create(ForecastRemoteDataRetrieverService::class.java)
    }
}
