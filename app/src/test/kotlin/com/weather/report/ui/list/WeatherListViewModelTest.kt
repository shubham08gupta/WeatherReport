package com.weather.report.ui.list

import app.cash.turbine.test
import com.weather.report.domain.Location
import com.weather.report.ui.FakeWeatherRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDate

@RunWith(JUnit4::class)
class WeatherListViewModelTest {

    private lateinit var viewModel: WeatherListViewModel

    private lateinit var fakeWeatherRepo: FakeWeatherRepositoryImpl

    @Before
    fun setup() {
        fakeWeatherRepo = FakeWeatherRepositoryImpl()
        viewModel = WeatherListViewModel(fakeWeatherRepo)
    }

    @Test
    fun shouldReturnAllLocationsWithWeather() = runBlocking {
        // arrange
        fakeWeatherRepo.locationList = mutableListOf(
            Location("Tokyo", "Japan"),
            Location("Paris", "France", LocalDate.of(2020, 2, 25)),
            Location("Paris", "France", LocalDate.of(2019, 12, 2))
        )

        // act
        viewModel.allLocationsWithWeather.test {
            // assert
            val fetchedLocations = awaitItem() // returns all the locations
            assertThat(fetchedLocations.first().name, equalTo("Tokyo"))
            assertThat(fetchedLocations.last().name, equalTo("Paris"))
            awaitComplete()
        }
    }

}
