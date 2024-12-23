package com.vodafone.data

import com.vodafone.core.domain.model.ForecastResponse
import com.vodafone.core.domain.model.Weather
import com.vodafone.core.domain.model.WeatherForecast
import com.vodafone.core.domain.model.WeatherResponse
import com.vodafone.core.utilities.networkHelper.RemoteDataException
import com.vodafone.data.datasource.remote.NetworkParams
import com.vodafone.data.datasource.remote.WeatherApiServices
import com.vodafone.data.datasource.remote.WeatherRemoteSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class WeatherRemoteSourceTest {

    @Mock
    private lateinit var apiServices: WeatherApiServices
    private lateinit var weatherRemoteSource: WeatherRemoteSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        weatherRemoteSource = WeatherRemoteSource(apiServices)
    }

    @Test
    fun `getWeather returns successful response`() = runBlocking {
        // Arrange
        val cityName = "Cairo"
        val apiKey = NetworkParams.API_KEY

        val mockWeatherResponse = WeatherResponse(
            id = 1,
            name = "Cairo",
            weather = arrayListOf(Weather(description = "Clear sky", icon = "01d")),
            main = null
        ).apply {
            cod = 200
        }
        val mockResponse = Response.success(mockWeatherResponse)

        `when`(apiServices.getWeather(cityName = cityName, apiKey =  apiKey))
            .thenReturn(mockResponse)

        // Act
        val result = weatherRemoteSource.getWeather(cityName)

        // Assert
        assertNotNull(result)
        assertEquals("Cairo", result.name)
        assertTrue(result.weather.isNotEmpty())
    }

    @Test
    fun `getWeather throws NetworkException for error response`() = runBlocking {
        // Arrange
        val cityName = "UnknownCity"
        val apiKey = NetworkParams.API_KEY
        val mockErrorResponse = Response.error<WeatherResponse>(
            404,
            okhttp3.ResponseBody.create(null, "City not found")
        )

        `when`(apiServices.getWeather(cityName, apiKey =  apiKey))
            .thenReturn(mockErrorResponse)

        // Act & Assert
        val exception = assertThrows(RemoteDataException.NotFoundException::class.java) {
            runBlocking {
                weatherRemoteSource.getWeather(cityName)
            }
        }
        assertTrue(exception.message?.isNotEmpty() == true)
    }

    @Test
    fun `getForecast returns successful response`() = runBlocking {
        // Arrange
        val cityName = "Alexandria"
        val apiKey = NetworkParams.API_KEY
        val mockForecastResponse = ForecastResponse(
            forecasts = arrayListOf(WeatherForecast()),
            city = null
        ).apply {
            cod = 200
        }

        val mockResponse = Response.success(mockForecastResponse)

        `when`(apiServices.getForecast(cityName =cityName, apiKey =  apiKey))
            .thenReturn(mockResponse)

        // Act
        val result = weatherRemoteSource.getForecast(cityName)

        // Assert
        assertNotNull(result)
        assertTrue(result.forecasts.isNotEmpty())
    }

    @Test
    fun `getForecast throws NetworkException for error response`() = runBlocking {
        // Arrange
        val cityName = "UnknownCity"
        val apiKey = NetworkParams.API_KEY
        val mockErrorResponse = Response.error<ForecastResponse>(
            401,
            okhttp3.ResponseBody.create(null, "City not found")
        )

        `when`(apiServices.getForecast(cityName= cityName, apiKey =  apiKey))
            .thenReturn(mockErrorResponse)

        // Act & Assert
        val exception = assertThrows(RemoteDataException.UnauthorizedException::class.java) {
            runBlocking {
                weatherRemoteSource.getForecast(cityName)
            }
        }
        assertTrue(exception.message?.isNotEmpty() == true)

    }
}
