package com.nido.weatherlogger.data.source.local.room.utils

import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE

interface BaseDao<E> {
    @Insert(onConflict = IGNORE)
    suspend fun saveData(data: List<E>)

    @Insert(onConflict = IGNORE)
    suspend fun saveData(data: E)
}
