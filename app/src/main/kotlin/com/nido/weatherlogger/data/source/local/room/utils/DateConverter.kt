package com.nido.weatherlogger.data.source.local.room.utils

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun dateToLong(date: Date?) =
        date?.time

    @TypeConverter
    fun longToDate(time: Long?) =
        if (time == null)
            null
        else
            Date(time)
}
