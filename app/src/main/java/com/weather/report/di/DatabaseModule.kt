package com.weather.report.di

import android.content.Context
import androidx.room.Room
import com.weather.report.data.local.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/***
 * A Dagger Module to provide configurations specific to Application database(a.k.a. [Room])
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            WeatherDatabase::class.java,
            "WeatherDatabase.db"
        ).build()

    @Singleton
    @Provides
    fun provideLocationDao(db: WeatherDatabase) = db.locationDao()
}
