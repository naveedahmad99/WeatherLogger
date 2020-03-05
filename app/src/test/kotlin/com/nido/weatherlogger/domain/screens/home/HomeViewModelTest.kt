package com.nido.weatherlogger.domain.screens.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nido.weatherlogger.CoroutineTestRule
import com.nido.weatherlogger.R
import com.nido.weatherlogger.data.FakeForecastRepository
import com.nido.weatherlogger.getOrAwaitValue
import com.nido.weatherlogger.helpers.Constants
import com.nido.weatherlogger.helpers.weather.Forecast
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

class HomeViewModelTest {

    private lateinit var forecastsRepository: FakeForecastRepository

    // subject under test
    private lateinit var forecastsViewModel: HomeViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setupViewModel() {
        forecastsRepository = FakeForecastRepository()
        val forecastData1 = Forecast(Constants.LATVIA_CITY_ID, "Baku", R.drawable.baku_maiden_tower, 761, 9, 758, 65, 5.2f, 123456789, 987654321, Date())
        val forecastData2 = Forecast(Constants.RIGA_CITY_ID, "Sumgait", R.drawable.sumgait_industrial_city, 761, 9, 758, 65, 5.2f, 123456789, 987654321, Date())
        val forecastData3 = Forecast(Constants.DORBAY_CITY_ID, "Lenkaran", R.drawable.lenkaran_samovar, 761, 9, 758, 65, 5.2f, 123456789, 987654321, Date())
        val forecastData4 = Forecast(Constants.SABILE_CITY_ID, "Shamakhi", R.drawable.shamakhi_observatory_city, 761, 9, 758, 65, 5.2f, 123456789, 987654321, Date())
        val forecastData5 = Forecast(Constants.TALSI_CITY_ID, "Goychay", R.drawable.baku_maiden_tower, 761, 9, 758, 65, 5.2f, 123456789, 987654321, Date())

        forecastsRepository.addForecasts(forecastData1, forecastData2, forecastData3, forecastData4, forecastData5)

        forecastsViewModel = HomeViewModel(forecastsRepository)
    }

    @Test
    fun getForecastData_returnsNotNullValue() {
        // the new forecast value is triggered
        val value = forecastsViewModel.forecastData.getOrAwaitValue() //value
        assertThat(value, not(nullValue()))
    }
}
