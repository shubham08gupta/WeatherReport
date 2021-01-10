package com.test.weather.report

import com.weather.report.domain.Location
import java.time.LocalDate

object TestUtil {

    fun createTestLocations(): List<Location> {
        return mutableListOf(
            Location("Tokyo", "Japan", LocalDate.of(2020, 12, 25)),
            Location("Tokyo", "Japan", LocalDate.of(2020, 12, 27)),
            Location("Tokyo", "Japan", LocalDate.of(2020, 12, 26)),
            Location("Berlin", "Germany", LocalDate.of(2020, 12, 26)),
            Location("Berlin", "Germany", LocalDate.of(2020, 12, 25)),
        )
    }
}