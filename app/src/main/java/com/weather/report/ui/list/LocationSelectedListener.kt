package com.weather.report.ui.list

import com.weather.report.domain.Location

fun interface LocationSelectedListener {
    fun onLocationSelected(location: Location)
}