package com.weather.report.ui

import android.content.Context
import com.weather.report.R
import com.weather.report.domain.ErrorType

/***
 * Shows the appropriate error message based on the error type
 */
fun ErrorType.getDisplayMessage(context: Context): String = run {
    when (this) {
        is ErrorType.Network.ServerError.InternalServerError, is ErrorType.Network.ServerError.ServiceUnavailable ->
            context.getString(R.string.message_server_error)
        is ErrorType.Network.ConnectionError.TimeoutError ->
            context.getString(R.string.message_network_timeout)
        is ErrorType.Network.ClientError.ValidationError ->
            context.getString(R.string.message_wrong_input)
        is ErrorType.Network.ConnectionError.NoConnectionError ->
            context.getString(R.string.message_offline)
        else -> context.getString(R.string.message_unknown_error)
    }
}