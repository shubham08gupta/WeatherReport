package com.weather.report.data.remote.error

import com.weather.report.domain.ErrorType
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toErrorType(): ErrorType = run {
    return when (this) {
        is UnknownHostException, is ConnectException -> {
            ErrorType.Network.ConnectionError.NoConnectionError
        }
        is SocketTimeoutException -> {
            ErrorType.Network.ConnectionError.TimeoutError
        }
        else -> {
            ErrorType.Network.ConnectionError.OtherError
        }
    }
}
