package com.weather.report.data.local

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.ZonedDateTime

class Converters {

    @TypeConverter
    fun fromZonedDateTime(value: ZonedDateTime): String = value.toString()

    @TypeConverter
    fun toZonedDateTime(value: String): ZonedDateTime = ZonedDateTime.parse(value)

    @TypeConverter
    fun fromLocalDate(value: LocalDate): String = value.toString()

    @TypeConverter
    fun toLocalDate(value: String): LocalDate = LocalDate.parse(value)

    /***
     * a better idea to implement this is to use a new entity and refer using a foreign key
     */
    @TypeConverter
    fun fromStringList(value: List<String>): String = value.firstOrNull() ?: ""

    @TypeConverter
    fun toStringList(value: String): List<String> = listOf(value)
}