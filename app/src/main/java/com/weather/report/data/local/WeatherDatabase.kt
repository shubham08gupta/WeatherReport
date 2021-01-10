package com.weather.report.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.weather.report.domain.Location

@Database(
    entities = [
        Location::class
    ],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao
}