package com.nido.weatherlogger.data.source.local

import com.nido.weatherlogger.data.ForecastsDataSource
import com.nido.weatherlogger.data.Result
import com.nido.weatherlogger.data.source.local.room.city_forecast_data.CityForecastDataDao
import com.nido.weatherlogger.data.source.local.room.forecast_data.ForecastDataDao
import com.nido.weatherlogger.data.source.local.room.forecast_data.ForecastDataEntity
import com.nido.weatherlogger.helpers.toForecast
import com.nido.weatherlogger.helpers.weather.Forecast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Exception

class ForecastsLocalDataSource internal constructor(
    private val forecastDataDao: ForecastDataDao,
    private val cityForecastDataDao: CityForecastDataDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ForecastsDataSource {

    override suspend fun getForecastData(): Result<List<Forecast>> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(forecastDataDao.getCitiesLastForecastData().toForecast().sortedBy { it.cityId })
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getSingleCityForecastData(cityId: Long): Result<List<Forecast>> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(cityForecastDataDao.getCityForecastData(cityId).toForecast())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun saveForecastData(forecastData: List<ForecastDataEntity>) =
        withContext(ioDispatcher) {
            forecastDataDao.saveData(forecastData)
        }
}
