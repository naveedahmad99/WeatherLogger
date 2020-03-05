package com.nido.weatherlogger.data.source.local.room.forecast_data

import androidx.annotation.VisibleForTesting
import androidx.room.Dao
import androidx.room.Query
import com.nido.weatherlogger.data.source.local.room.utils.BaseDao

@Dao
abstract class ForecastDataDao : BaseDao<ForecastDataEntity> {
    @VisibleForTesting
    @Query("SELECT * FROM forecast_data;")
    abstract suspend fun getForecastData(): List<ForecastDataEntity>

    @Query("SELECT * FROM forecast_data ORDER BY timestamp DESC LIMIT 5;")
    abstract suspend fun getCitiesLastForecastData(): List<ForecastDataEntity>
}
