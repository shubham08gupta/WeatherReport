package com.weather.report.util

import com.weather.report.domain.Location
import com.weather.report.domain.Weather
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

object DummyData {

    fun createDummyLocations(): List<Location> {
        val weather1 = Weather(
            "12:50 PM",
            11.0,
            116.0,
            listOf("https://assets.weatherstack.com/images/wsymbols01_png_64/wsymbol_0004_black_low_cloud.png"),
            listOf("Cloudy"),
            12.0,
            2.0,
            "Left",
            1000.0,
            0.0,
            12.0,
            1.0,
            9.0,
            1.9,
            10.0,
            "no"
        )
        val weather2 = Weather(
            "11:50 PM",
            28.0,
            126.0,
            listOf("https://assets.weatherstack.com/images/wsymbols01_png_64/wsymbol_0002_sunny_intervals.png"),
            listOf("Sunny Interval"),
            14.0,
            4.0,
            "Right",
            1000.0,
            0.0,
            17.0,
            1.0,
            10.0,
            1.9,
            10.0,
            "no"
        )
        val weather3 = Weather(
            "11:50 PM",
            19.0,
            126.0,
            listOf("https://assets.weatherstack.com/images/wsymbols01_png_64/wsymbol_0001_sunny.png"),
            listOf("Partly Cloudy"),
            14.0,
            4.0,
            "Right",
            1000.0,
            0.0,
            17.0,
            1.0,
            14.0,
            1.9,
            10.0,
            "no"
        )
        val weather4 = Weather(
            "11:50 PM",
            6.0,
            126.0,
            listOf("https://assets.weatherstack.com/images/wsymbols01_png_64/wsymbol_0004_black_low_cloud.png"),
            listOf("Partly Cloudy"),
            14.0,
            4.0,
            "Right",
            1000.0,
            0.0,
            17.0,
            1.0,
            6.0,
            1.9,
            10.0,
            "no"
        )
        val weather5 = Weather(
            "1:50 PM",
            22.0,
            126.0,
            listOf("https://assets.weatherstack.com/images/wsymbols01_png_64/wsymbol_0001_sunny.png"),
            listOf("Partly Cloudy"),
            14.0,
            4.0,
            "Right",
            2000.0,
            0.0,
            17.0,
            1.0,
            0.0,
            1.9,
            10.0,
            "no"
        )
        val weather6 = Weather(
            "1:50 PM",
            16.0,
            126.0,
            listOf("https://assets.weatherstack.com/images/wsymbols01_png_64/wsymbol_0033_cloudy_with_light_rain_night.png"),
            listOf("Light Rain"),
            14.0,
            4.0,
            "Right",
            2000.0,
            0.0,
            17.0,
            1.0,
            15.0,
            1.9,
            10.0,
            "no"
        )
        return mutableListOf(
            Location(
                "Tokyo",
                "Japan",
                LocalDate.of(2020, 12, 27),
                ZonedDateTime.of(
                    LocalDate.of(2020, 12, 27),
                    LocalTime.MIDNIGHT,
                    ZoneId.systemDefault()
                ),
                weather1
            ),
            Location(
                "Tokyo",
                "Japan",
                LocalDate.of(2020, 12, 26),
                ZonedDateTime.of(
                    LocalDate.of(2020, 12, 26),
                    LocalTime.NOON,
                    ZoneId.systemDefault()
                ),
                weather3
            ),
            Location(
                "Tokyo",
                "Japan",
                LocalDate.of(2020, 12, 25),
                ZonedDateTime.of(LocalDate.of(2020, 12, 25), LocalTime.MIN, ZoneId.systemDefault()),
                weather2
            ),
            Location(
                "Berlin", "Germany",
                LocalDate.of(2020, 12, 24),
                ZonedDateTime.of(LocalDate.of(2020, 12, 24), LocalTime.MIN, ZoneId.systemDefault()),
                weather4
            ),
            Location(
                "Berlin", "Germany",
                LocalDate.of(2020, 12, 23),
                ZonedDateTime.of(LocalDate.of(2020, 12, 23), LocalTime.MAX, ZoneId.systemDefault()),
                weather6
            ),
            Location(
                "Vancouver", "Canada",
                LocalDate.of(2020, 12, 22),
                ZonedDateTime.of(
                    LocalDate.of(2020, 12, 22),
                    LocalTime.MIDNIGHT,
                    ZoneId.systemDefault()
                ),
                weather5
            ),
            Location(
                "New York", "USA",
                LocalDate.of(2020, 12, 21),
                ZonedDateTime.of(
                    LocalDate.of(2020, 12, 21),
                    LocalTime.MAX,
                    ZoneId.systemDefault()
                ),
                weather = weather5
            )
        )
    }
}