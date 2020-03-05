package com.nido.weatherlogger.data

import com.nido.weatherlogger.data.source.local.room.forecast_data.ForecastDataEntity
import com.nido.weatherlogger.helpers.weather.Forecast

interface ForecastsDataSource {
    suspend fun getForecastData(): Result<List<Forecast>>
    suspend fun saveForecastData(forecastData: List<ForecastDataEntity>)
    suspend fun getSingleCityForecastData(cityId: Long): Result<List<Forecast>>
}
