package com.weather.report.ui

import com.weather.report.data.repository.WeatherRepository
import com.weather.report.domain.Location
import com.weather.report.domain.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWeatherRepositoryImpl(
    // use mutable list to allow control from other classes for deterministic results
    var locationList: MutableList<Location> = mutableListOf()
) : WeatherRepository {

    override suspend fun getWeatherForecastFor(city: String): Resource<List<Location>> {
        return Resource.Success(locationList.filter { it.name == city })
    }

    override fun getAllSavedLocationsWithWeather(): Flow<List<Location>> = flow {
        emit(locationList)
    }
}
