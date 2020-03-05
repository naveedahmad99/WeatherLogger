package com.nido.weatherlogger.data.source.remote.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class ApiModel(
    @Json(name = "list")
    val currentWeather: List<WeatherList>
) {
    @JsonClass(generateAdapter = true)
    data class WeatherList(
        val main: Main,
        val weather: List<Weather>,
        val wind: Wind,
        @Json(name = "sys")
        val sunEvents: SunEvents,
        @Json(name = "id")
        val cityId: Int,
        @Transient
        val requestTime: Date = Date()
    ) {
        @JsonClass(generateAdapter = true)
        data class Main(val humidity: Int, val pressure: Int, val temp: Float)

        @JsonClass(generateAdapter = true)
        data class SunEvents(val sunrise: Int, val sunset: Int)

        @JsonClass(generateAdapter = true)
        data class Weather(val id: Int)

        @JsonClass(generateAdapter = true)
        data class Wind(val speed: Float)
    }
}
