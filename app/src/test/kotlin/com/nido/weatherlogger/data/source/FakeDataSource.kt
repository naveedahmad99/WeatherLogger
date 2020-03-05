package com.nido.weatherlogger.data.source

import com.nido.weatherlogger.data.ForecastsDataSource
import com.nido.weatherlogger.data.Result
import com.nido.weatherlogger.data.source.local.room.forecast_data.ForecastDataEntity
import com.nido.weatherlogger.helpers.cityNameById
import com.nido.weatherlogger.helpers.toForecast
import com.nido.weatherlogger.helpers.weather.Forecast

class FakeDataSource(private val forecasts: MutableList<Forecast>? = mutableListOf()) : ForecastsDataSource {
    override suspend fun getForecastData(): Result<List<Forecast>> {
        forecasts?.let { return Result.Success(it) }
        return Result.Error(Exception("Forecasts not found"))
    }

    override suspend fun saveForecastData(forecastData: List<ForecastDataEntity>) {
        forecasts?.addAll(forecastData.toForecast())
    }

    override suspend fun getSingleCityForecastData(cityId: Long): Result<List<Forecast>> {
        forecasts?.let { list -> return Result.Success(list.filter { it.cityId == cityId }) }
        return Result.Error(Exception("No forecast data found for ${cityId.cityNameById()}"))
    }
}
