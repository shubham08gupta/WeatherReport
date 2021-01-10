package com.weather.report.domain

sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure<out T>(val errorType: ErrorType) : Resource<T>()
}
