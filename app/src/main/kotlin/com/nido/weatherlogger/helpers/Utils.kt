package com.nido.weatherlogger.helpers

import com.nido.weatherlogger.helpers.weather.WeatherTypes

fun Long.cityIdById() =
    when (this) {
        587084L -> 1L
        584923L -> 2L
        147613L -> 3L
        585156L -> 4L
        else -> 5L
    }

fun Long.cityNameById() =
    when (this) {
        1L, 587084L -> "Latvia"
        2L, 584923L -> "Riga"
        3L, 147613L -> "Dorbay"
        4L, 585156L -> "Sabile"
        else -> "Talsi"
    }

fun Long.cityIconById() =
    when (this) {
        1L, 587084L -> com.nido.weatherlogger.R.drawable.baku_maiden_tower
        2L, 584923L -> com.nido.weatherlogger.R.drawable.sumgait_industrial_city
        3L, 147613L -> com.nido.weatherlogger.R.drawable.lenkaran_samovar
        4L, 585156L -> com.nido.weatherlogger.R.drawable.shamakhi_observatory_city
        else -> com.nido.weatherlogger.R.drawable.baku_maiden_tower
    }

fun Int.weatherName(): String {
    fun Int.atmosphereWeatherType() =
        when (this) {
            701 -> WeatherTypes.MIST
            711 -> WeatherTypes.SMOKE
            721 -> WeatherTypes.HAZE
            731 -> WeatherTypes.DUST
            741 -> WeatherTypes.FOG
            751 -> WeatherTypes.SAND
            761 -> WeatherTypes.DUST
            762 -> WeatherTypes.ASH
            771 -> WeatherTypes.SQUALL
            781 -> WeatherTypes.TORNADO
            else -> "Unknown weather"
        }

    return when (this) {
        in 200..232 -> WeatherTypes.THUNDERSTORM
        in 300..321 -> WeatherTypes.DRIZZLE
        in 500..531 -> WeatherTypes.RAIN
        in 600..622 -> WeatherTypes.SNOW
        in 700..781 -> atmosphereWeatherType()
        800 -> WeatherTypes.CLEAR
        in 801..804 -> WeatherTypes.CLOUDS
        else -> "Unknown weather"
    }
}

fun Int.weatherToIcon() =
    when (this) {
        in 200..232 -> com.nido.weatherlogger.R.drawable.weather_thunderstorm
        in 300..321 -> com.nido.weatherlogger.R.drawable.weather_drizzle
        in 500..531 -> com.nido.weatherlogger.R.drawable.weather_rain
        in 600..622 -> com.nido.weatherlogger.R.drawable.weather_snow
        in 700..781 -> com.nido.weatherlogger.R.drawable.weather_atmosphere
        800 -> com.nido.weatherlogger.R.drawable.weather_clear
        in 801..804 -> com.nido.weatherlogger.R.drawable.weather_clouds
        else -> throw IllegalArgumentException()
    }
