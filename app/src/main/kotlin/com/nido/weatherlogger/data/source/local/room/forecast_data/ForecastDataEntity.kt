package com.nido.weatherlogger.data.source.local.room.forecast_data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nido.weatherlogger.data.source.local.room.utils.DateConverter
import java.util.Date

@Entity(tableName = "forecast_data")
@TypeConverters(DateConverter::class)
data class ForecastDataEntity(
    @ColumnInfo(name = "city_id") val cityId: Long,
    @ColumnInfo(name = "type") val weatherCode: Int, // cloudy, clear sky etc
    @ColumnInfo(name = "temperature") val weatherTemperature: Int, // in Celsius
    @ColumnInfo(name = "pressure") val weatherPressure: Int, // mmHg; 1 hPa to mmHg = 0.75006 mmHg
    @ColumnInfo(name = "humidity") val weatherHumidity: Int, // %
    @ColumnInfo(name = "wind_speed") val weatherWindSpeed: Float,
    val sunrise: Int,
    val sunset: Int,
    @ColumnInfo(name = "timestamp") val requestTime: Date,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "forecast_data_id") var id: Long = 0L
)
