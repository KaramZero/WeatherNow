package com.vodafone.core

import com.vodafone.core.domain.model.ViewState
import com.vodafone.core.utilities.networkHelper.safeApiCall
import org.junit.Assert.assertEquals
import org.junit.Test

class SafeApiCallTest {

    private val defaultActionId = 100

    @Test
    fun `safeApiCall should return ViewState Success when block executes successfully`() {
        // Arrange
        val result = "Success"
        val apiCall: () -> String = { result }

        // Act
        val response = safeApiCall(actionId = defaultActionId, block = apiCall)

        // Assert
        assertEquals(ViewState.Success(result, defaultActionId), response)
    }

    @Test
    fun `safeApiCall should return ViewState Error when an exception occurs`() {
        // Arrange
        val exception = RuntimeException("Network Error")
        val apiCall: () -> String = { throw exception }

        // Act
        val response = safeApiCall(actionId = defaultActionId, block = apiCall)

        // Assert
        assert(response is ViewState.Error) { "Expected ViewState.Error, but got ${response::class}" }
        val errorResponse = response as ViewState.Error
        assertEquals(defaultActionId, errorResponse.actionId)
    }

    @Test
    fun `safeApiCall should call handleException on failure`() {
        // Arrange
        val exception = RuntimeException("Test Exception")
        val apiCall: () -> String = { throw exception }

        // Act
        val response = safeApiCall(actionId = defaultActionId, block = apiCall)

        // Assert
        assert(response is ViewState.Error) { "Expected ViewState.Error, but got ${response::class}" }
        val errorResponse = response as ViewState.Error
        assertEquals(defaultActionId, errorResponse.actionId)
    }

    @Test
    fun `safeApiCall should return ViewState Error with correct actionId when exception occurs without actionId`() {
        // Arrange
        val exception = RuntimeException("Test Exception without actionId")
        val apiCall: () -> String = { throw exception }

        // Act
        val response = safeApiCall(block = apiCall)

        // Assert
        assert(response is ViewState.Error) { "Expected ViewState.Error, but got ${response::class}" }
        val errorResponse = response as ViewState.Error
        assertEquals(-1, errorResponse.actionId)
    }
}
