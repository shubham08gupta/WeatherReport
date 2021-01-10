package com.test.weather.report

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.weather.report.data.local.LocationDao
import com.weather.report.data.local.WeatherDatabase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class LocationDaoTest {

    private lateinit var locationDao: LocationDao
    private lateinit var db: WeatherDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room
            .inMemoryDatabaseBuilder(
                context, WeatherDatabase::class.java
            )
            .allowMainThreadQueries()
            .build()
        locationDao = db.locationDao()
    }

    @Test
    fun whenInsertRandomLocationsReturnSortedWeather() = runBlocking {
        val locationList = TestUtil.createTestLocations()
        locationList.forEach {
            locationDao.upsert(it)
        }
        val byCity = locationDao.getLocationByCity("Tokyo")
        val weatherToday = byCity[0]
        // check sorted by decreasing order of last saved date
        assertThat(weatherToday.lastSavedOn, equalTo(LocalDate.of(2020, 12, 27)))
    }

    @Test
    fun whenFetchUnknownCityReturnEmptyResults() = runBlocking {
        val locationList = TestUtil.createTestLocations()
        locationList.forEach {
            locationDao.upsert(it)
        }
        val byCity = locationDao.getLocationByCity("abc")
        assertThat(byCity.isEmpty(), equalTo(true))
    }

    @Test
    fun whenSearchedCityRunCaseInsensitiveSearch() = runBlocking {
        val locationList = TestUtil.createTestLocations()
        locationList.forEach {
            locationDao.upsert(it)
        }
        // test case insensitive search
        val byCityUppercase = locationDao.getLocationByCity("TOKYO")
        val byCityLowercase = locationDao.getLocationByCity("tokyo")
        val byCityMixCase = locationDao.getLocationByCity("Tokyo")
        assertThat(byCityUppercase.isNotEmpty(), equalTo(true))
        assertThat(byCityLowercase.isNotEmpty(), equalTo(true))
        assertThat(byCityMixCase.isNotEmpty(), equalTo(true))
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}