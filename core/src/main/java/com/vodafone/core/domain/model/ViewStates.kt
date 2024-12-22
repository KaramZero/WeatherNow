package com.vodafone.core.domain.model

sealed class ViewState<out T> {
    data class Idle(val actionId: Int = -1) : ViewState<Nothing>()
    data class Loading(val actionId: Int = -1) : ViewState<Nothing>()
    data class Success<T>(val data: T,val actionId: Int = -1) : ViewState<T>()
    data class Error(val error: ErrorType?, val actionId: Int = -1) : ViewState<Nothing>()
}

sealed class ErrorType {
    data class ServerError(val error: ServerErrorType?, val message: String) : ErrorType()
    data class NetworkError(val message: String) : ErrorType()
    data class DataParsingError(val message: String) : ErrorType()
    data class RequestNotCompletedError(val message: String?) : ErrorType()
    data object RequestTimeoutError : ErrorType()
}

enum class ServerErrorType {
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND,
    METHOD_NOT_ALLOWED,
    REQUEST_TIMEOUT,
    INTERNAL_SERVER_ERROR,
    NOT_IMPLEMENTED,
    SERVICE_UNAVAILABLE,
    BAD_GATEWAY,
    HTTP_VERSION_NOT_SUPPORTED,
}