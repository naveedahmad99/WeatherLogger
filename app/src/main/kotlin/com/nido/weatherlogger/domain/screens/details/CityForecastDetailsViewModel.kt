package com.nido.weatherlogger.domain.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nido.weatherlogger.data.IForecastsRepository
import com.nido.weatherlogger.data.Result

class CityForecastDetailsViewModel(
    forecastRepository: IForecastsRepository,
    cityId: Long
) : ViewModel() {
    val cityForecastDetails = liveData {
        when (val data = forecastRepository.getCityForecastData(cityId)) {
            is Result.Success -> emit(data.data)
            else -> emit(listOf())
        }
    }
}
