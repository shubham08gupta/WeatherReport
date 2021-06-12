package com.weather.report.ui.list

import androidx.lifecycle.ViewModel
import com.weather.report.data.repository.WeatherRepository
import com.weather.report.domain.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    weatherRepository: WeatherRepository
) : ViewModel() {

    val allLocationsWithWeather: Flow<List<Location>> =
        weatherRepository.getAllSavedLocationsWithWeather()

}