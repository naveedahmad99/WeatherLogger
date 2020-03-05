package com.nido.weatherlogger.helpers

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ConstantsTest {

    @Test
    fun buildApiUrl_returnsFullApiUrl() {
        val apiValidUrl = "https://api.openweathermap.org/data/2.5/group?id=648615,456172,7628304,455937,454970&units=metric&appid=52419933504a3eb9b0575266edb9b77c"

        val generatedUrl = Constants.buildApiUrl()

        assertThat(generatedUrl, `is`(apiValidUrl))
    }
}