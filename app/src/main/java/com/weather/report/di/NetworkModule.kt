package com.weather.report.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.weather.report.BuildConfig
import com.weather.report.data.remote.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        defaultQueryParamsInterceptor: DefaultQueryParamsInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(defaultQueryParamsInterceptor)
        }.build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(BuildConfig.BASE_URL)
            client(okHttpClient)
            addConverterFactory(
                Json {
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                }.asConverterFactory("application/json".toMediaType())
            )
        }.build()

    @Singleton
    @Provides
    fun provideDefaultQueryParamsInterceptor(): DefaultQueryParamsInterceptor {
        return DefaultQueryParamsInterceptor()
    }

    /***
     * insert default query parameters in every request
     */
    @Singleton
    class DefaultQueryParamsInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val url = request.url.newBuilder()
                .addQueryParameter("access_key", BuildConfig.API_KEY)
                .build()
            val defaultQuery = request.newBuilder().url(url).build()

            return chain.proceed(defaultQuery)
        }
    }

    @Singleton
    @Provides
    fun provideWeatherService(retrofit: Retrofit): WeatherService =
        retrofit.create(WeatherService::class.java)
}