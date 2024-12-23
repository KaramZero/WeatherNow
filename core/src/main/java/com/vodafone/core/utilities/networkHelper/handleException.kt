package com.vodafone.core.utilities.networkHelper

import com.vodafone.core.domain.model.ErrorType
import com.vodafone.core.domain.model.ServerErrorType.*
import com.vodafone.core.domain.model.ViewState
import com.vodafone.core.utilities.networkHelper.RemoteDataException.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun handleException(ex: Exception, actionId: Int = -1): ViewState.Error {
    return when (ex) {
        is BadRequestException ->
            ViewState.Error(ErrorType.ServerError(BAD_REQUEST, ex.message.toString()), actionId)

        is UnauthorizedException ->
            ViewState.Error(ErrorType.ServerError(UNAUTHORIZED, ex.message.toString()), actionId)

        is ForbiddenException ->
            ViewState.Error(ErrorType.ServerError(FORBIDDEN, ex.message.toString()), actionId)

        is NotFoundException ->
            ViewState.Error(ErrorType.ServerError(NOT_FOUND, ex.message.toString()), actionId)

        is MethodNotAllowedException ->
            ViewState.Error(
                ErrorType.ServerError(METHOD_NOT_ALLOWED, ex.message.toString()),
                actionId
            )

        is RequestTimeoutException ->
            ViewState.Error(ErrorType.ServerError(REQUEST_TIMEOUT, ex.message.toString()), actionId)

        is InternalServerErrorException ->
            ViewState.Error(
                ErrorType.ServerError(INTERNAL_SERVER_ERROR, ex.message.toString()),
                actionId
            )

        is NotImplementedException ->
            ViewState.Error(ErrorType.ServerError(NOT_IMPLEMENTED, ex.message.toString()), actionId)

        is BadGatewayException ->
            ViewState.Error(ErrorType.ServerError(BAD_GATEWAY, ex.message.toString()), actionId)

        is ServiceUnavailableException ->
            ViewState.Error(
                ErrorType.ServerError(SERVICE_UNAVAILABLE, ex.message.toString()),
                actionId
            )

        is HTTPVersionNotSupportedException ->
            ViewState.Error(
                ErrorType.ServerError(
                    HTTP_VERSION_NOT_SUPPORTED,
                    ex.message.toString()
                ), actionId
            )

        is RequestNotCompletedException ->
            ViewState.Error(ErrorType.RequestNotCompletedError(ex.message), actionId)

        is ParsingException ->
            ViewState.Error(ErrorType.DataParsingError(ex.message.toString()), actionId)

        is UnknownHostException ->
            ViewState.Error(ErrorType.NetworkError(ex.message.toString()), actionId)

        is SocketTimeoutException -> ViewState.Error(ErrorType.RequestTimeoutError, actionId)
        else ->
            ViewState.Error(ErrorType.RequestNotCompletedError(ex.message), actionId)
    }
}
