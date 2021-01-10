package com.weather.report.ui.detail

import androidx.annotation.DrawableRes

/***
 * A data class that represents information about the current weather
 */
data class WeatherParameter(
    @DrawableRes val icon: Int,
    val paramName: String,
    val paramValue: String
)