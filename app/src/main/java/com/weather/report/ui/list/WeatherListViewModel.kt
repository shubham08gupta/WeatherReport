package com.weather.report.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.weather.report.data.repository.WeatherRepository
import com.weather.report.domain.Location
import kotlinx.coroutines.flow.Flow

class WeatherListViewModel @ViewModelInject constructor(
    weatherRepository: WeatherRepository
) : ViewModel() {

    val allLocationsWithWeather: Flow<List<Location>> =
        weatherRepository.getAllSavedLocationsWithWeather()

}