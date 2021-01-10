package com.weather.report.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    /***
     * Returns current weather forecast
     */
    @GET("current")
    suspend fun getWeatherForecast(
        @Query("query") cityName: String
    ): Response<WeatherServiceResponse>
}