package com.vodafone.core.utilities.networkHelper

import java.io.IOException

/**
 * Base class for all exceptions related to remote data operations.
 *
 * @param message The error message.
 * @param cause The underlying cause of the exception.
 */
sealed class RemoteDataException(message: String?, cause: Throwable? = null) :
    IOException(message, cause) {
    class EmptyResponseException(message: String?) : RemoteDataException(message)
    class BadRequestException(message: String?) : RemoteDataException(message)
    class UnauthorizedException(message: String?) : RemoteDataException(message)
    class ForbiddenException(message: String?) : RemoteDataException(message)
    class NotFoundException(message: String?) : RemoteDataException(message)
    class MethodNotAllowedException(message: String?) : RemoteDataException(message)
    class RequestTimeoutException(message: String?) : RemoteDataException(message)
    class InternalServerErrorException(message: String?) : RemoteDataException(message)
    class NotImplementedException(message: String?) : RemoteDataException(message)
    class BadGatewayException(message: String?) : RemoteDataException(message)
    class ServiceUnavailableException(message: String?) : RemoteDataException(message)
    class GatewayTimeoutException(message: String?) : RemoteDataException(message)
    class HTTPVersionNotSupportedException(message: String?) : RemoteDataException(message)
    class RequestNotCompletedException(message: String?) : RemoteDataException(message)
    class ParsingException(message: String?) : RemoteDataException(message)
}