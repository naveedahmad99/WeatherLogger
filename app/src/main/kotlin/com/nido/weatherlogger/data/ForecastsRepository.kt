package com.nido.weatherlogger.data

import com.nido.weatherlogger.data.source.local.room.forecast_data.ForecastDataEntity
import com.nido.weatherlogger.helpers.weather.Forecast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Concrete implementation to load forecasts from the data sources into a cache.
 *
 * To simplify the sample, this repository only uses the local data source only if
 * the remote data source fails. Remote is the source of truth.
 */
class ForecastsRepository(
    private val forecastsRemoteDataSource: ForecastsDataSource,
    private val forecastsLocalDataSource: ForecastsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : IForecastsRepository {

    override suspend fun getCityForecastData(cityId: Long) =
        withContext(ioDispatcher) {
            return@withContext forecastsLocalDataSource.getSingleCityForecastData(cityId)
        }

    override suspend fun getForecastData(): Result<List<Forecast>> =
        withContext(ioDispatcher) {
            return@withContext fetchDataFromRemoteOrLocal()
        }

    override suspend fun saveForecastData(forecastData: List<ForecastDataEntity>) =
        withContext(ioDispatcher) {
            return@withContext forecastsLocalDataSource.saveForecastData(forecastData)
        }

    private suspend fun fetchDataFromRemoteOrLocal(): Result<List<Forecast>> {
        // remote data first
        when (val remoteForecastData = forecastsRemoteDataSource.getForecastData()) {
            is Result.Error -> {
                val localForecastData = forecastsLocalDataSource.getForecastData()
                if (localForecastData is Result.Success)
                    return localForecastData
            }
            is Result.Success -> {
                refreshLocalDataSource(remoteForecastData.data)
                return remoteForecastData
            }
        }

        return Result.Error(Exception("Error fetching data from remote and local"))
    }

    private suspend fun refreshLocalDataSource(data: List<Forecast>) {
        try {
            val forecastEntityData = data.map {
                ForecastDataEntity(
                    it.cityId,
                    it.weatherType,
                    it.temperature,
                    it.pressure,
                    it.humidity,
                    it.windSpeed,
                    it.sunrise,
                    it.sunset,
                    it.requestTime
                )
            }
            withContext(ioDispatcher) {
                forecastsLocalDataSource.saveForecastData(forecastEntityData)
            }
        } catch (e: Exception) {
            println(e)
        }
    }
}
