package com.weather.report.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.report.data.repository.WeatherRepository
import com.weather.report.domain.ErrorType
import com.weather.report.domain.Location
import com.weather.report.domain.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<WeatherDetailUiState> =
        MutableStateFlow(WeatherDetailUiState.Loading)
    val uiState: StateFlow<WeatherDetailUiState> = _uiState

    fun getWeatherForecastFor(city: String) = viewModelScope.launch {
        when (val resource = weatherRepository.getWeatherForecastFor(city)) {
            is Resource.Loading -> {
                _uiState.value = WeatherDetailUiState.Loading
            }
            is Resource.Success -> {
                _uiState.value = WeatherDetailUiState.Success(resource.data)
            }
            is Resource.Failure -> {
                _uiState.value = WeatherDetailUiState.Error(resource.errorType)
            }
        }
    }

}

sealed class WeatherDetailUiState {
    object Loading : WeatherDetailUiState()
    data class Success(val data: List<Location>) : WeatherDetailUiState()
    data class Error(val errorType: ErrorType) : WeatherDetailUiState()
}