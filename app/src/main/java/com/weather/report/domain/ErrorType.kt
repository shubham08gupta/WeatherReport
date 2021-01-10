package com.weather.report.domain

sealed class ErrorType {

    sealed class Network : ErrorType() {

        object ClientError : Network() {
            object Unauthorized : Network()
            object IllegalStateError : Network()
            object ValidationError : Network()
            object RoutingError : Network()
            object NotFoundError : Network()
            object MethodNotAllowed : Network()
            object Forbidden : Network()
        }

        // An errorType that could not be communicated with the network for some reason.
        sealed class ConnectionError {
            object NoConnectionError : Network()
            object TimeoutError : Network()
            object OtherError : Network()
        }

        sealed class ServerError {
            object InternalServerError : Network()
            object ServiceUnavailable : Network()
        }

        // Communication with the network is successful and a response is returned, but an unexpected response status error.
        object OtherNetworkError : Network()
    }

    object IO : ErrorType()

    object Unknown : ErrorType()

}