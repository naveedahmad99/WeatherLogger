package com.nido.weatherlogger.data.source.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nido.weatherlogger.data.source.local.room.cities.CityDao
import com.nido.weatherlogger.data.source.local.room.cities.CityEntity
import com.nido.weatherlogger.data.source.local.room.city_forecast_data.CityForecastDataDao
import com.nido.weatherlogger.data.source.local.room.city_forecast_data.CityForecastData
import com.nido.weatherlogger.data.source.local.room.forecast_data.ForecastDataDao
import com.nido.weatherlogger.data.source.local.room.forecast_data.ForecastDataEntity
import com.nido.weatherlogger.data.source.local.room.utils.AppDatabase
import com.nido.weatherlogger.helpers.Constants
import java.util.Date
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForecastsLocalDataSourceTest {

    private lateinit var db: AppDatabase
    private lateinit var forecastDataDao: ForecastDataDao
    private lateinit var cityDao: CityDao
    private lateinit var cityForecastDataDao: CityForecastDataDao

    private val forecasts = listOf(
        ForecastDataEntity(Constants.LATVIA_CITY_ID, 802, 14, 762, 67, 28f, 32131132, 1231124, Date(), id = 1L), // data for Baku
        ForecastDataEntity(Constants.LATVIA_CITY_ID, 511, 16, 762, 82, 36f, 32131132, 1231124, Date(), id = 2L),   // data for Baku
        ForecastDataEntity(Constants.LATVIA_CITY_ID, 622, 35, 762, 44, 14f, 32131132, 1231124, Date(), id = 3L),  // data for Baku
        ForecastDataEntity(Constants.DORBAY_CITY_ID, 701, 15, 762, 67, 65f, 32131132, 1231124, Date(), id = 4L),  // data for Lenkaran
        ForecastDataEntity(Constants.DORBAY_CITY_ID, 200, 9, 762, 67, 87f, 32131132, 1231124, Date(), id = 5L)    // data for Lenkaran
    )
    private val cities = listOf(
        CityEntity(Constants.LATVIA_CITY_ID, "BAKU"),
        CityEntity(Constants.RIGA_CITY_ID, "SUMGAIT"),
        CityEntity(Constants.DORBAY_CITY_ID, "LENKARAN"),
        CityEntity(Constants.SABILE_CITY_ID, "SHAMAKHI"),
        CityEntity(Constants.TALSI_CITY_ID, "GOYCHAY")
    )
    private val bakuCityForecastData = CityForecastData(cities[0], forecasts.subList(0, 3))
    private val lenkaranCityForecastData = CityForecastData(cities[2], forecasts.subList(3, 5))

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val ctx = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(ctx, AppDatabase::class.java).build()

        forecastDataDao = db.forecastDataDao()
        cityDao = db.cityDao()
        cityForecastDataDao = db.cityForecastDataDao()
    }

    @Test
    fun saveForecastData_success() {
        runBlocking {
            cityDao.saveData(cities)
            forecastDataDao.saveData(forecasts)

            assertThat(cityForecastDataDao.getCityForecastData(Constants.LATVIA_CITY_ID), `is`(equalTo(bakuCityForecastData)))
            assertThat(cityForecastDataDao.getCityForecastData(Constants.DORBAY_CITY_ID), `is`(equalTo(lenkaranCityForecastData)))

            assertThat(forecastDataDao.getForecastData(), `is`(equalTo(forecasts)))
        }
    }

    @After
    fun closeDb() = db.close()
}
