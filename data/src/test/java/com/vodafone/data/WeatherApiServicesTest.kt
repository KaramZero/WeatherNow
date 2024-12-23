package com.vodafone.data

import com.vodafone.core.domain.model.ForecastResponse
import com.vodafone.core.domain.model.WeatherResponse
import com.vodafone.data.datasource.remote.WeatherApiServices
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class WeatherApiServicesTest {

    @Mock
    private lateinit var apiService: WeatherApiServices

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `getWeather should return WeatherResponse`() = runBlocking {
        // Arrange
        val mockResponse = Response.success(WeatherResponse().apply {
            cod = 200
            message = "Success"
        })

        `when`(apiService.getWeather("Cairo", "metric", "testApiKey")).thenReturn(mockResponse)

        // Act
        val response = apiService.getWeather("Cairo", "metric", "testApiKey")

        // Assert
        assertNotNull(response.body())
        assertEquals(200, response.body()?.cod)
        assertEquals("Success", response.body()?.message)
    }

    @Test
    fun `getForecast should return ForecastResponse`() = runBlocking {
        // Arrange
        val mockResponse = Response.success(ForecastResponse().apply {
            cod = 200
            message = "0"
        })

        `when`(apiService.getForecast("Cairo", "metric", "testApiKey")).thenReturn(mockResponse)

        // Act
        val response = apiService.getForecast("Cairo", "metric", "testApiKey")

        // Assert
        assertNotNull(response.body())
        assertEquals(200, response.body()?.cod)
        assertEquals("0", response.body()?.message)
    }

    @Test
    fun `getWeather should handle API error response`() = runBlocking {
        // Arrange
        val mockResponse = Response.error<WeatherResponse>(
            404,
            okhttp3.ResponseBody.create(null, "City not found")
        )

        `when`(apiService.getWeather("UnknownCity", "metric", "testApiKey")).thenReturn(mockResponse)

        // Act
        val response = apiService.getWeather("UnknownCity", "metric", "testApiKey")

        // Assert
        assertNotNull(response.errorBody())
        assertEquals(404, response.code())
    }

    @Test
    fun `getForecast should handle API error response`() = runBlocking {
        // Arrange
        val mockResponse = Response.error<ForecastResponse>(
            401,
            okhttp3.ResponseBody.create(null, "Invalid API Key")
        )

        `when`(apiService.getForecast("Cairo", "metric", "invalidApiKey")).thenReturn(mockResponse)

        // Act
        val response = apiService.getForecast("Cairo", "metric", "invalidApiKey")

        // Assert
        assertNotNull(response.errorBody())
        assertEquals(401, response.code())
    }
}
