package com.nido.weatherlogger.data.source.local.room.cities

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface CityDao {
    @Insert
    fun saveData(data: List<CityEntity>)
}
