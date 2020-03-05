package com.nido.weatherlogger.helpers

object Constants {
    private const val API_URL = "https://api.openweathermap.org/data/2.5/"
    private const val API_KEY = "52419933504a3eb9b0575266edb9b77c"

    const val LATVIA_CITY_ID = 1L
    const val RIGA_CITY_ID = 2L
    const val DORBAY_CITY_ID = 3L
    const val SABILE_CITY_ID = 4L
    const val TALSI_CITY_ID = 5L
    val CITY_IDS = listOf(
        LATVIA_CITY_ID,
        RIGA_CITY_ID,
        DORBAY_CITY_ID,
        SABILE_CITY_ID,
        TALSI_CITY_ID
    )

    private const val LATVIA = 648615
    private const val RIGA = 456172
    private const val DORBAY = 7628304
    private const val SABILE = 455937
    private const val TALSI = 454970

    fun buildApiUrl() = StringBuilder()
        .append(API_URL).append("group?id=")
        .append(LATVIA).append(',')
        .append(RIGA).append(',')
        .append(DORBAY).append(',')
        .append(SABILE).append(',')
        .append(TALSI).append("&units=metric&appid=")
        .append(API_KEY).toString()
}
