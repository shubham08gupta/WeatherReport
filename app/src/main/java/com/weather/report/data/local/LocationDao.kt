package com.weather.report.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.weather.report.domain.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao : BaseDao<Location> {

    @Query("SELECT * FROM LOCATION WHERE name LIKE (:cityName) ORDER BY lastSavedOn DESC")
    suspend fun getLocationByCity(cityName: String): List<Location>

    @Query("SELECT * FROM LOCATION")
    fun getAllSavedLocations(): Flow<List<Location>>

    /***
     * Inserts or updates the data. If a conflict occurs when an insert operation is performed,
     * it updates the data instead of replacing the old data
     */
    @Transaction
    suspend fun upsert(location: Location) {
        if (insertOrIgnore(location) == -1L)
            update(location)
    }
}