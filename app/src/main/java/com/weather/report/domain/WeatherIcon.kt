package com.weather.report.domain

import kotlinx.serialization.Serializable

@Serializable
data class WeatherIcon(
    val url: String
)