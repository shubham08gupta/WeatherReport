package com.weather.report.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.report.data.repository.WeatherRepository
import com.weather.report.domain.ErrorType
import com.weather.report.domain.Location
import com.weather.report.domain.Resource
import kotlinx.coroutines.launch

class WeatherDetailViewModel @ViewModelInject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<ErrorType>()
    val errorType: LiveData<ErrorType> get() = _error

    private val _data = MutableLiveData<List<Location>>()
    val data: LiveData<List<Location>> get() = _data

    fun getWeatherForecastFor(city: String) = viewModelScope.launch {
        _isLoading.value = true
        val resource = weatherRepository.getWeatherForecastFor(city)
        when (resource) {
            is Resource.Loading -> {
                _isLoading.value = true
            }
            is Resource.Success -> {
                _data.value = resource.data
                _isLoading.value = false
            }
            is Resource.Failure -> {
                _error.value = resource.errorType
                _isLoading.value = false
            }
        }
    }

}