package com.vodafone.core.utilities.networkHelper

import com.vodafone.core.domain.model.BaseApiResponse
import com.vodafone.core.utilities.networkHelper.RemoteDataException.*
import com.vodafone.core.utilities.networkHelper.RemoteDataException.EmptyResponseException
import retrofit2.Response


/**
 * Handles the response of a network request.
 *
 * @param response The response object.
 *
 * @return The response body as the specified type [T].
 *
 * @throws RemoteDataException If the request fails or the response is not successful.
 * @throws TypeCastException If the response body is not of the specified type [T].
 */
@Throws(RemoteDataException::class)
inline fun <reified T> handleResponse(response: Response<T>?): T {
    return if (response?.isSuccessful == true) {
        try {
            val res: T = response.body()
                ?: throw EmptyResponseException("No data received from server : response.body() is null")


            val baseResponse: BaseApiResponse = res as? BaseApiResponse
                ?: throw TypeCastException("Response body (${T::class.java}) is not of type BaseApiResponse (${BaseApiResponse::class.java})")

            if (baseResponse.cod == 200) res
            else throw RequestNotCompletedException(baseResponse.message)
        } catch (e: Exception) {
            if (e is RemoteDataException || e is TypeCastException)
                throw e
            throw ParsingException("Error parsing response body : ${e.message}")
        }

    } else {
        val errorMessage = response?.message()

        throw when (response?.code()) {
            400 -> BadRequestException(errorMessage)
            401 -> UnauthorizedException(errorMessage)
            403 -> ForbiddenException(errorMessage)
            404 -> NotFoundException(errorMessage)
            405 -> MethodNotAllowedException(errorMessage)
            408 -> RequestTimeoutException(errorMessage)
            500 -> InternalServerErrorException(errorMessage)
            501 -> NotImplementedException(errorMessage)
            502 -> BadGatewayException(errorMessage)
            503 -> ServiceUnavailableException(errorMessage)
            504 -> GatewayTimeoutException(errorMessage)
            505 -> HTTPVersionNotSupportedException(errorMessage)
            else -> RequestNotCompletedException(errorMessage)
        }
    }
}