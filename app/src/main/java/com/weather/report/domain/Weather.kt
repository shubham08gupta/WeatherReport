package com.weather.report.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    @SerialName("observation_time") val observationTime: String,
    val temperature: Double,
    @SerialName("weather_code") val weatherCode: Double,
    @SerialName("weather_icons") val weatherIcons: List<String>,
    @SerialName("weather_descriptions") val weatherDescriptions: List<String>,
    @SerialName("wind_speed") val windSpeed: Double,
    @SerialName("wind_degree") val windDegree: Double,
    @SerialName("wind_dir") val windDirection: String,
    val pressure: Double,
    @SerialName("precip") val precipitation: Double,
    val humidity: Double,
    @SerialName("cloudcover") val cloudCover: Double,
    @SerialName("feelslike") val feelsLike: Double,
    @SerialName("uv_index") val uvIndex: Double,
    val visibility: Double,
    @SerialName("is_day") val isDay: String
)