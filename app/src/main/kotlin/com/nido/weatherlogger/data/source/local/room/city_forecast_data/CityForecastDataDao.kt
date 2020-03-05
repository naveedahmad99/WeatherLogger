package com.nido.weatherlogger.data.source.local.room.city_forecast_data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class CityForecastDataDao {
    @Transaction
    @Query("SELECT * FROM cities WHERE city_id = :cityId ORDER BY city_id;")
    abstract suspend fun getCityForecastData(cityId: Long): CityForecastData
}
