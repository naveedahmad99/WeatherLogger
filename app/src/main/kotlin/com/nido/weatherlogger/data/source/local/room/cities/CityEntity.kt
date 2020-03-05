package com.nido.weatherlogger.data.source.local.room.cities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey @ColumnInfo(name = "city_id") val id: Long,
    @ColumnInfo(name = "city_name") val name: String
)
