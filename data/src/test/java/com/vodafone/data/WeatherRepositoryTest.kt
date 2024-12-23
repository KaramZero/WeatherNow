package com.vodafone.data

import com.vodafone.core.domain.model.ForecastResponse
import com.vodafone.core.domain.model.ViewState
import com.vodafone.core.domain.model.Weather
import com.vodafone.core.domain.model.WeatherForecast
import com.vodafone.core.domain.model.WeatherResponse
import com.vodafone.data.datasource.local.CityDao
import com.vodafone.data.datasource.remote.NetworkParams
import com.vodafone.data.datasource.remote.WeatherApiServices
import com.vodafone.data.datasource.remote.WeatherRemoteSource
import com.vodafone.data.model.City
import com.vodafone.data.repository.WeatherRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response


class WeatherRepositoryTest {

    @Mock
    private lateinit var mockLocalSource: CityDao

    @Mock
    private lateinit var apiServices: WeatherApiServices

    private lateinit var mockRemoteSource: WeatherRemoteSource

    private lateinit var weatherRepository: WeatherRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mockRemoteSource = WeatherRemoteSource(apiServices)
        weatherRepository = WeatherRepositoryImpl(mockRemoteSource, mockLocalSource)
    }

    @Test
    fun `test getWeather when API succeeds updates viewState to Success`() = runBlocking {
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

        // Mock successful API response
        `when`(apiServices.getWeather(cityName = cityName, apiKey = apiKey))
            .thenReturn(mockResponse)
        `when`(mockLocalSource.getCity()).thenReturn(City(name = cityName))

        // Call the method under test
        val actualResponse = weatherRepository.getWeather(cityName)

        // Verify the result
        assertTrue(actualResponse is ViewState.Success)
        assertEquals(mockWeatherResponse, (actualResponse as? ViewState.Success)?.data)
    }

    @Test
    fun `test getWeather when API fails updates viewState to Error`() = runBlocking {
        val cityName = "London"
        val apiKey = NetworkParams.API_KEY
        val mockErrorResponse = Response.error<WeatherResponse>(
            404,
            okhttp3.ResponseBody.create(null, "City not found")
        )

        // Mock API response
        `when`(apiServices.getWeather(cityName = cityName, apiKey = apiKey))
            .thenReturn(mockErrorResponse)
        `when`(mockLocalSource.getCity()).thenReturn(City(name = "London"))

        // Call the method under test
        val actualResponse = weatherRepository.getWeather(cityName)

        // Verify the result
        assertTrue(actualResponse is ViewState.Error)
    }

    @Test
    fun `test getForecast when API succeeds updates viewState to Success`() = runBlocking {
        val cityName = "Alexandria"
        val apiKey = NetworkParams.API_KEY
        val mockForecastResponse = ForecastResponse(
            forecasts = arrayListOf(WeatherForecast()),
            city = null
        ).apply {
            cod = 200
        }

        val mockResponse = Response.success(mockForecastResponse)

        // Mock successful API response
        `when`(apiServices.getForecast(cityName = cityName, apiKey = apiKey))
            .thenReturn(mockResponse)
        `when`(mockLocalSource.getCity()).thenReturn(City(name = "Alexandria"))

        // Call the method under test
        val actualResponse = weatherRepository.getForecast()

        // Verify the result
        assertTrue(actualResponse is ViewState.Success)
        val successResponse = actualResponse as ViewState.Success
        assertEquals(mockForecastResponse, successResponse.data)
    }

    @Test
    fun `test getForecast when API fails updates viewState to Error`() = runBlocking {
        val cityName = "London"
        val apiKey = NetworkParams.API_KEY
        val mockErrorResponse = Response.error<WeatherResponse>(
            404,
            okhttp3.ResponseBody.create(null, "City not found")
        )

        // Mock API response
        `when`(apiServices.getWeather(cityName = cityName, apiKey = apiKey))
            .thenReturn(mockErrorResponse)
        `when`(mockLocalSource.getCity()).thenReturn(City(name = "London"))

        // Call the method under test
        val actualResponse = weatherRepository.getForecast()

        // Verify the result
        assertTrue(actualResponse is ViewState.Error)
    }

    @Test
    fun `test getWeather when no city name is provided falls back to default city`() = runBlocking {

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

        // Mock successful API response
        `when`(apiServices.getWeather(cityName = "Cairo", apiKey = apiKey)) // Empty city name
            .thenReturn(mockResponse)
        `when`(mockLocalSource.getCity()).thenReturn(City(name = "Cairo"))

        // Call the method under test
        val actualResponse = weatherRepository.getWeather(" ")

        // Verify the result
        assertTrue(actualResponse is ViewState.Success)
        val successResponse = actualResponse as ViewState.Success
        assertEquals(mockWeatherResponse, successResponse.data)
    }
}
