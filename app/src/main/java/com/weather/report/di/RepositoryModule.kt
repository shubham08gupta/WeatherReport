package com.weather.report.di

import com.weather.report.data.repository.WeatherRepository
import com.weather.report.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/***
 * A Dagger Module used to provide configurations specific to Repository layer.
 * The repositories provided here are available application wide
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}
