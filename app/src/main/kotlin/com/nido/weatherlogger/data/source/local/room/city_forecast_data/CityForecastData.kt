package com.nido.weatherlogger.data.source.local.room.city_forecast_data

import androidx.room.Embedded
import androidx.room.Relation
import com.nido.weatherlogger.data.source.local.room.cities.CityEntity
import com.nido.weatherlogger.data.source.local.room.forecast_data.ForecastDataEntity

data class CityForecastData(
    @Embedded val city: CityEntity,
    @Relation(
        parentColumn = "city_id",
        entity = ForecastDataEntity::class,
        entityColumn = "city_id"
    )
    val cityForecastData: List<ForecastDataEntity>
)
