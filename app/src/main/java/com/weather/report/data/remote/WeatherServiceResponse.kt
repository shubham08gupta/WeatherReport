package com.weather.report.data.remote

import com.weather.report.domain.Location
import com.weather.report.domain.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherServiceResponse(
    val success: Boolean = true,
    @SerialName("request") val request: Request? = null,
    @SerialName("current") val weather: Weather? = null,
    @SerialName("location") val location: Location? = null,
    val error: ErrorResponse? = null
)

@Serializable
data class ErrorResponse(
    val code: Int,
    val type: String,
    val info: String
)

@Serializable
data class Request(
    val type: String,
    val query: String,
    val language: String,
    val unit: String
)