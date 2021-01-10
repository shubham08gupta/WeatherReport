package com.weather.report.data.repository

import com.weather.report.data.local.LocationDao
import com.weather.report.data.remote.WeatherService
import com.weather.report.data.remote.error.toErrorType
import com.weather.report.domain.ErrorType
import com.weather.report.domain.Location
import com.weather.report.domain.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface WeatherRepository {

    /***
     * Fetches the weather forecast for the given city
     * @param city The city for which the weather forecast is required
     */
    suspend fun getWeatherForecastFor(city: String): Resource<List<Location>>

    /***
     * returns all the saved locations with their weather information
     */
    fun getAllSavedLocationsWithWeather(): Flow<List<Location>>
}

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val weatherService: WeatherService
) : WeatherRepository {
    override suspend fun getWeatherForecastFor(city: String): Resource<List<Location>> {
        try {
            // always try to fetch data first
            val response = weatherService.getWeatherForecast(city)
            if (response.isSuccessful) {
                response.body()?.let { body ->

                    return if (!body.success) {
                        // searched city is not available
                        Resource.Failure(ErrorType.Network.ClientError.ValidationError)
                    } else if (body.location != null) {

                        // add weather information into the location object
                        val location = body.location.copy(weather = body.weather)

                        // update or insert the location
                        locationDao.upsert(location)

                        // filter location by city. We cannot search by user entered city because
                        // if a country is searched by the user, the API returns the capital city
                        // data. Thus it would be wrong to search for a country instead of a city.
                        Resource.Success(locationDao.getLocationByCity(body.location.name))
                    } else {
                        // unwanted response
                        Resource.Failure(ErrorType.Network.OtherNetworkError)
                    }
                }
                return Resource.Failure(ErrorType.IO)
            } else {
                // non 200-300 error
                return Resource.Failure(ErrorType.Network.ConnectionError.OtherError)
            }
        } catch (e: Exception) {
            // try showing data from db
            val savedLocation = locationDao.getLocationByCity(city)
            return if (savedLocation.isNullOrEmpty()) {
                Resource.Failure(e.toErrorType())
            } else {
                Resource.Success(savedLocation)
            }
        }
    }

    override fun getAllSavedLocationsWithWeather(): Flow<List<Location>> {
        return locationDao.getAllSavedLocations()
    }
}