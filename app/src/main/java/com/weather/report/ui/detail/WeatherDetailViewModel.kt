package com.weather.report.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.weather.report.data.repository.WeatherRepository
import com.weather.report.domain.ErrorType
import com.weather.report.domain.Location
import com.weather.report.domain.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherDetailViewModel @AssistedInject constructor(
    private val weatherRepository: WeatherRepository,
    @Assisted private val city: String,
) : ViewModel() {

    private val _uiState: MutableStateFlow<WeatherDetailUiState> =
        MutableStateFlow(WeatherDetailUiState.Loading)
    val uiState: StateFlow<WeatherDetailUiState> = _uiState

    init {
        getWeatherForecastFor(city)
    }

    private fun getWeatherForecastFor(city: String) = viewModelScope.launch {
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


    /***
     * Use an assisted factory to inject manual parameters
     */
    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(@Assisted city: String): WeatherDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            city: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(city) as T
            }
        }
    }
}

sealed class WeatherDetailUiState {
    object Loading : WeatherDetailUiState()
    data class Success(val data: List<Location>) : WeatherDetailUiState()
    data class Error(val errorType: ErrorType) : WeatherDetailUiState()
}