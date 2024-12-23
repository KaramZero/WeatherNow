package com.vodafone.core

import com.vodafone.core.domain.model.ErrorType
import com.vodafone.core.domain.model.ServerErrorType
import com.vodafone.core.domain.model.ViewState
import com.vodafone.core.utilities.networkHelper.RemoteDataException.*
import com.vodafone.core.utilities.networkHelper.handleException
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class HandleExceptionTest {

    private val defaultActionId = 100

    @Test
    fun `handleException should return ViewState_Error for BadRequestException`() {
        val exception = BadRequestException("Bad Request")
        val result = handleException(exception, defaultActionId)
        assertEquals(
            ViewState.Error(ErrorType.ServerError(ServerErrorType.BAD_REQUEST, "Bad Request"), defaultActionId),
            result
        )
    }

    @Test
    fun `handleException should return ViewState_Error for UnauthorizedException`() {
        val exception = UnauthorizedException("Unauthorized")
        val result = handleException(exception, defaultActionId)
        assertEquals(
            ViewState.Error(ErrorType.ServerError(ServerErrorType.UNAUTHORIZED, "Unauthorized"), defaultActionId),
            result
        )
    }

    @Test
    fun `handleException should return ViewState_Error for ForbiddenException`() {
        val exception = ForbiddenException("Forbidden")
        val result = handleException(exception, defaultActionId)
        assertEquals(
            ViewState.Error(ErrorType.ServerError(ServerErrorType.FORBIDDEN, "Forbidden"), defaultActionId),
            result
        )
    }

    @Test
    fun `handleException should return ViewState_Error for NotFoundException`() {
        val exception = NotFoundException("Not Found")
        val result = handleException(exception, defaultActionId)
        assertEquals(
            ViewState.Error(ErrorType.ServerError(ServerErrorType.NOT_FOUND, "Not Found"), defaultActionId),
            result
        )
    }

    @Test
    fun `handleException should return ViewState_Error for MethodNotAllowedException`() {
        val exception = MethodNotAllowedException("Method Not Allowed")
        val result = handleException(exception, defaultActionId)
        assertEquals(
            ViewState.Error(ErrorType.ServerError(ServerErrorType.METHOD_NOT_ALLOWED, "Method Not Allowed"), defaultActionId),
            result
        )
    }

    @Test
    fun `handleException should return ViewState_Error for RequestTimeoutException`() {
        val exception = RequestTimeoutException("Request Timeout")
        val result = handleException(exception, defaultActionId)
        assertEquals(
            ViewState.Error(ErrorType.ServerError(ServerErrorType.REQUEST_TIMEOUT, "Request Timeout"), defaultActionId),
            result
        )
    }

    @Test
    fun `handleException should return ViewState_Error for InternalServerErrorException`() {
        val exception = InternalServerErrorException("Internal Server Error")
        val result = handleException(exception, defaultActionId)
        assertEquals(
            ViewState.Error(ErrorType.ServerError(ServerErrorType.INTERNAL_SERVER_ERROR, "Internal Server Error"), defaultActionId),
            result
        )
    }

    @Test
    fun `handleException should return ViewState_Error for NotImplementedException`() {
        val exception = NotImplementedException("Not Implemented")
        val result = handleException(exception, defaultActionId)
        assertEquals(
            ViewState.Error(ErrorType.ServerError(ServerErrorType.NOT_IMPLEMENTED, "Not Implemented"), defaultActionId),
            result
        )
    }

    @Test
    fun `handleException should return ViewState_Error for BadGatewayException`() {
        val exception = BadGatewayException("Bad Gateway")
        val result = handleException(exception, defaultActionId)
        assertEquals(
            ViewState.Error(ErrorType.ServerError(ServerErrorType.BAD_GATEWAY, "Bad Gateway"), defaultActionId),
            result
        )
    }

    @Test
    fun `handleException should return ViewState_Error for ServiceUnavailableException`() {
        val exception = ServiceUnavailableException("Service Unavailable")
        val result = handleException(exception, defaultActionId)
        assertEquals(
            ViewState.Error(ErrorType.ServerError(ServerErrorType.SERVICE_UNAVAILABLE, "Service Unavailable"), defaultActionId),
            result
        )
    }

    @Test
    fun `handleException should return ViewState_Error for HTTPVersionNotSupportedException`() {
        val exception = HTTPVersionNotSupportedException("HTTP Version Not Supported")
        val result = handleException(exception, defaultActionId)
        assertEquals(
            ViewState.Error(ErrorType.ServerError(ServerErrorType.HTTP_VERSION_NOT_SUPPORTED, "HTTP Version Not Supported"), defaultActionId),
            result
        )
    }

    @Test
    fun `handleException should return ViewState_Error for UnknownHostException`() {
        val exception = UnknownHostException("No Internet Connection")
        val result = handleException(exception, defaultActionId)
        assertEquals(
            ViewState.Error(ErrorType.NetworkError("No Internet Connection"), defaultActionId),
            result
        )
    }

    @Test
    fun `handleException should return ViewState_Error for SocketTimeoutException`() {
        val exception = SocketTimeoutException()
        val result = handleException(exception, defaultActionId)
        assertEquals(
            ViewState.Error(ErrorType.RequestTimeoutError, defaultActionId),
            result
        )
    }

    @Test
    fun `handleException should return ViewState_Error for ParsingException`() {
        val exception = ParsingException("Parsing Error")
        val result = handleException(exception, defaultActionId)
        assertEquals(
            ViewState.Error(ErrorType.DataParsingError("Parsing Error"), defaultActionId),
            result
        )
    }

    @Test
    fun `handleException should return ViewState_Error for unknown exceptions`() {
        val exception = Exception("Unknown Error")
        val result = handleException(exception, defaultActionId)
        assertEquals(
            ViewState.Error(ErrorType.RequestNotCompletedError("Unknown Error"), defaultActionId),
            result
        )
    }
}
