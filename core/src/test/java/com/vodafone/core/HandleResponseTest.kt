package com.vodafone.core

import com.vodafone.core.domain.model.BaseApiResponse
import com.vodafone.core.utilities.networkHelper.RemoteDataException.*
import com.vodafone.core.utilities.networkHelper.handleResponse
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

class HandleResponseTest {

    data class MockApiResponse(
        val result:String
    ) : BaseApiResponse()

    data class NotApiResponse(
        val result:String
    )

    @Test
    fun `handleResponse should return response body on success`() {
        // Arrange
        val mockResponse = MockApiResponse("Result").apply {
            cod = 200
            message = "Success"
        }
        val response = Response.success(mockResponse)

        // Act
        val result = handleResponse(response)

        // Assert
        assertNotNull(result)
        assertEquals(200, result.cod)
        assertEquals("Success", result.message)
    }

    @Test
    fun `handleResponse should throw EmptyResponseException if response body is null`() {
        // Arrange
        val response: Response<MockApiResponse> = Response.success(null)

        // Act & Assert
        val exception = assertThrows(EmptyResponseException::class.java) {
            handleResponse(response)
        }
        assertEquals("No data received from server : response.body() is null", exception.message)
    }

    @Test
    fun `handleResponse should throw TypeCastException if response body is not BaseApiResponse`() {
        // Arrange
        val mockInvalidResponse = NotApiResponse("Invalid Response Type")
        val response = Response.success(mockInvalidResponse)

        // Act & Assert
        val exception = assertThrows(TypeCastException::class.java) {
            handleResponse(response)
        }
        assertTrue(exception.message!!.contains("Response body"))
    }

    @Test
    fun `handleResponse should throw RequestNotCompletedException if cod is not 200`() {
        // Arrange
        val mockResponse = MockApiResponse( "No Result").apply {
            cod = 404
            message = "Not Found"
        }
        val response = Response.success(mockResponse)

        // Act & Assert
        val exception = assertThrows(RequestNotCompletedException::class.java) {
            handleResponse(response)
        }
        assertEquals("Not Found", exception.message)
    }

    @Test
    fun `handleResponse should throw specific RemoteDataException for various HTTP error codes`() {
        val testCases = listOf(
            400 to BadRequestException::class.java,
            401 to UnauthorizedException::class.java,
            403 to ForbiddenException::class.java,
            404 to NotFoundException::class.java,
            500 to InternalServerErrorException::class.java,
            503 to ServiceUnavailableException::class.java
        )

        for ((code, expectedException) in testCases) {
            // Arrange
            val response: Response<MockApiResponse> = Response.error(code, okhttp3.ResponseBody.create(null, "Error"))

            // Act & Assert
            val exception = assertThrows(expectedException) {
                handleResponse(response)
            }
            assertNotNull(exception.message)
        }
    }

    @Test
    fun `handleResponse should throw RequestNotCompletedException for unknown HTTP error codes`() {
        // Arrange
        val response: Response<MockApiResponse> = Response.error(999, okhttp3.ResponseBody.create(null, "Unknown Error"))

        // Act & Assert
        val exception = assertThrows(RequestNotCompletedException::class.java) {
            handleResponse(response)
        }
        assertNotNull(exception.message)
    }
}
