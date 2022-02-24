package com.weather.report.ui.detail

import app.cash.turbine.test
import com.weather.report.domain.Location
import com.weather.report.ui.FakeWeatherRepositoryImpl
import com.weather.report.util.MainCoroutineRule
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDate

@RunWith(JUnit4::class)
class WeatherDetailViewModelTest {

    // replaces Dispatcher.Main with a test coroutine dispatcher
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: WeatherDetailViewModel

    private lateinit var fakeWeatherRepo: FakeWeatherRepositoryImpl

    @Before
    fun setup() {
        fakeWeatherRepo = FakeWeatherRepositoryImpl()
        viewModel = WeatherDetailViewModel(fakeWeatherRepo, "Paris")
    }

    @Test
    fun whenDetailScreenOpenedShouldReturnCorrectUiStates() = runTest {
        // arrange
        fakeWeatherRepo.locationList = mutableListOf(
            Location("Paris", "France", LocalDate.of(2020, 12, 26)),
            Location("Paris", "France", LocalDate.of(2020, 2, 25)),
            Location("Delhi", "Tokyo", LocalDate.of(2021, 3, 25)),
        )
        //act
        viewModel.uiState.test {
            // assert
            assertTrue(awaitItem() is WeatherDetailUiState.Loading) // loading state
            val successState = awaitItem() // success state
            assertTrue(successState is WeatherDetailUiState.Success)
            // should give exactly 2 Paris locations as the city is Paris
            assertThat(2, equalTo((successState as WeatherDetailUiState.Success).data.size))
            cancelAndConsumeRemainingEvents()
        }
    }
}
