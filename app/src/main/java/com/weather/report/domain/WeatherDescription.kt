package com.weather.report.domain

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDescription(
    val desc: String
)