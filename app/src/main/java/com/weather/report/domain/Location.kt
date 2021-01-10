package com.weather.report.domain

import androidx.room.Embedded
import androidx.room.Entity
import com.weather.report.data.remote.LocalDateSerializer
import com.weather.report.data.remote.ZonedDateTimeSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.LocalDate
import java.time.ZonedDateTime

/***
 * Represents a location containing the weather of that location
 */
@Entity(
    // use a composite primary key to save weather of a location for any day.
    // This only saves the latest weather for any particular day.
    // For eg: Tokyo can have only 1 weather information for today. Only the latest weather will
    // be saved. Similarly, Tokyo will have only 1 weather information for yesterday.
    primaryKeys = ["name", "lastSavedOn"]
)
@Serializable
data class Location(
    val name: String,
    val country: String,
    // represents the date on which the location was last saved
    @Serializable(with = LocalDateSerializer::class)
    val lastSavedOn: LocalDate = LocalDate.now(),
    // represents the time at which the location was last fetched
    @Serializable(with = ZonedDateTimeSerializer::class)
    val lastUpdatedAt: ZonedDateTime = ZonedDateTime.now(),
    @Transient @Embedded val weather: Weather? = null
)