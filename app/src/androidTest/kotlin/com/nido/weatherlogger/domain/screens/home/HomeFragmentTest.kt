package com.nido.weatherlogger.domain.screens.home

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.nido.weatherlogger.R
import com.nido.weatherlogger.ServiceLocator
import com.nido.weatherlogger.data.FakeForecastRepository
import com.nido.weatherlogger.data.IForecastsRepository
import com.nido.weatherlogger.data.source.local.room.forecast_data.ForecastDataEntity
import com.nido.weatherlogger.helpers.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.Date

@MediumTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {

    private lateinit var fakeRepository: IForecastsRepository
    private val forecasts = listOf(
        ForecastDataEntity(Constants.LATVIA_CITY_ID, 802, 14, 762, 67, 28f, 32131132, 1231124, Date()), // data for Baku
        ForecastDataEntity(Constants.RIGA_CITY_ID, 511, 16, 762, 82, 36f, 32131132, 1231124, Date()),   // data for Sumgait
        ForecastDataEntity(Constants.DORBAY_CITY_ID, 622, 35, 762, 44, 14f, 32131132, 1231124, Date()),  // data for Lenkaran
        ForecastDataEntity(Constants.SABILE_CITY_ID, 701, 15, 762, 67, 65f, 32131132, 1231124, Date()),  // data for Shamakhi
        ForecastDataEntity(Constants.TALSI_CITY_ID, 200, 9, 762, 67, 87f, 32131132, 1231124, Date())    // data for Goychay
    )

    @Before
    fun initRepository() {
        fakeRepository = FakeForecastRepository()
        ServiceLocator.forecastsRepository = fakeRepository
    }

    @After
    fun cleanUp() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun clickForecast_navigateToForecastDetailsFragmentOfBaku() = runBlockingTest {
        // GIVEN - on the main screen with a forecast
        fakeRepository.saveForecastData(forecasts)

        val scenario = launchFragmentInContainer<HomeFragment>(Bundle(), R.style.AppTheme)

        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // WHEN - click on the first item
        onView(withId(R.id.forecast_view)).perform(click())

        // THEN - verify that we navigate to the first detail screen
        verify(navController).navigate(
            HomeFragmentDirections.actionFragmentHomeToFragmentCityForecastDetails(forecasts[0].cityId)
        )
    }

//    @Test
//    fun clickForecast_navigateToForecastDetailsFragmentOfLenkaran() = runBlockingTest {
//        // GIVEN - on the main screen with a forecast
//        fakeRepository.saveForecastData(forecasts)
//
//        val scenario = launchFragmentInContainer<HomeFragment>(Bundle(), R.style.AppTheme)
//
//        val navController = mock(NavController::class.java)
//
//        scenario.onFragment {
//            Navigation.setViewNavController(it.view!!, navController)
//        }
//
//        // WHEN - click on the first item
//        onView(withId(R.id.forecast_city_picker)).perform(
//            RecyclerViewActions.scrollToPosition<ForecastAdapter.ForecastViewHolder>(2)
//        ) // scroll to Lenkaran city
//
//        delay(2_000)
//        onView(withId(R.id.forecast_view)).perform() // swipe 2 times to go to Lenkaran city
//
//        // THEN - verify that we navigate to the first detail screen
//        verify(navController).navigate(
//            HomeFragmentDirections.actionFragmentHomeToFragmentCityForecastDetails(forecasts[2].cityId)
//        )
//    }
}
