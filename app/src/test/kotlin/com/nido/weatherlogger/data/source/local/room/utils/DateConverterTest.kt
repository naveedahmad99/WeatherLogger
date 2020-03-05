package com.nido.weatherlogger.data.source.local.room.utils

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.util.Date

class DateConverterTest {

    @Test
    fun dateToLong_nullValue_returnsNull() {
        val date: Date? = null

        val result = DateConverter().dateToLong(date)

        assertThat(result, `is`(nullValue()))
    }

    @Test
    fun dateToLong_dateValue_returnsTime() {
        val date = Date()

        val result = DateConverter().dateToLong(date)

        assertThat(result, `is`(date.time))
    }

    @Test
    fun longToDate_nullValue_returnsNull() {
        val timeInLong: Long? = null

        val result = DateConverter().longToDate(timeInLong)

        assertThat(result, `is`(nullValue()))
    }

    @Test
    fun longToDate_longValue_returnsDate() {
        val timeInLong = 1572328780L

        val result = DateConverter().longToDate(timeInLong)

        assertThat(result, `is`(Date(timeInLong)))
    }
}
