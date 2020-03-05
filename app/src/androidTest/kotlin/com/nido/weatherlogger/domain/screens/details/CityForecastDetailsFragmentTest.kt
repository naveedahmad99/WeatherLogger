package com.nido.weatherlogger.domain.screens.details

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.nido.weatherlogger.R
import com.nido.weatherlogger.ServiceLocator
import com.nido.weatherlogger.data.FakeForecastRepository
import com.nido.weatherlogger.data.IForecastsRepository
import com.nido.weatherlogger.data.source.local.room.forecast_data.ForecastDataEntity
import com.nido.weatherlogger.helpers.Constants
import com.nido.weatherlogger.helpers.weather.Forecast
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.ocpsoft.prettytime.PrettyTime
import java.util.Date

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class CityForecastDetailsFragmentTest {

    private lateinit var context: Context
    private lateinit var fakeRepository: IForecastsRepository

    @Before
    fun initRepository() {
        context = ApplicationProvider.getApplicationContext()
        fakeRepository = FakeForecastRepository()
        ServiceLocator.forecastsRepository = fakeRepository
    }

    @After
    fun cleanUpDb() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun bakuCityForecastDetails_displayedInUi() = runBlockingTest {
        // GIVEN - add forecast data to the db
        val bakuForecast = Forecast(Constants.LATVIA_CITY_ID, "Baku", R.drawable.baku_maiden_tower, 761, 9, 758, 65, 5.2f, 123456789, 987654321, Date())
        with(bakuForecast) {
            fakeRepository.saveForecastData(
                listOf(
                    ForecastDataEntity(
                        cityId,
                        weatherType,
                        temperature,
                        pressure,
                        humidity,
                        windSpeed,
                        sunrise,
                        sunset,
                        requestTime
                    )
                )
            )
        }

        // WHEN - details fragment launched to display forecast details
        val bundle = CityForecastDetailsFragmentArgs(bakuForecast.cityId).toBundle()
        launchFragmentInContainer<CityForecastDetailsFragment>(bundle, R.style.CityForecastDetailsDialogTheme)

        // THEN - forecast details launched to display task
        // make sure that the forecast details are all shown and correct
        onView(withId(R.id.text_measured)).check(matches(withText(String.format(context.getString(R.string.measured), PrettyTime().format(bakuForecast.requestTime)))))
        onView(withId(R.id.text_measured)).check(matches(isDisplayed()))

        onView(withId(R.id.textView11)).check(matches(withText(String.format(context.getString(R.string.temperature), bakuForecast.temperature))))
        onView(withId(R.id.textView11)).check(matches(isDisplayed()))

        onView(withId(R.id.textView12)).check(matches(withText(String.format(context.getString(R.string.pressure), bakuForecast.pressure))))
        onView(withId(R.id.textView12)).check(matches(isDisplayed()))

        onView(withId(R.id.textView13)).check(matches(withText(String.format(context.getString(R.string.humidity), bakuForecast.humidity))))
        onView(withId(R.id.textView13)).check(matches(isDisplayed()))

        onView(withId(R.id.textView14)).check(matches(withText(String.format(context.getString(R.string.wind), bakuForecast.windSpeed))))
        onView(withId(R.id.textView14)).check(matches(isDisplayed()))
    }
}
