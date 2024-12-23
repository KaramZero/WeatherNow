package com.vodafone.currentweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vodafone.core.domain.model.ErrorType
import com.vodafone.core.domain.model.ViewState
import com.vodafone.core.domain.model.WeatherResponse
import com.vodafone.core.domain.usecases.GetWeatherUseCase
import com.vodafone.currentweather.presentation.CurrentWeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class CurrentWeatherViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var getWeatherUseCase: GetWeatherUseCase

    private lateinit var viewModel: CurrentWeatherViewModel

    @Before
    fun setUp() {
        // Initialize the viewModel
        viewModel = CurrentWeatherViewModel(getWeatherUseCase)

        // Set the test dispatcher to run the coroutines in a controlled manner
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test initial viewState is idle`() = runBlockingTest {
        val initialState = viewModel.uiState.viewState.first()
        assertTrue(initialState is ViewState.Idle)
    }

    @Test
    fun `test getCurrentWeather when called updates viewState to Loading and then to success`() =
        runTest {
            // Prepare the mock response
            val weatherResponse = WeatherResponse(/* provide a mock response */)
            `when`(getWeatherUseCase.invoke("")).thenReturn(ViewState.Success(weatherResponse))

            // Start the function
            viewModel.getCurrentWeather()

            // Verify the loading state
            val loadingState = viewModel.uiState.viewState.first()
            assertTrue(loadingState is ViewState.Loading)

            // Wait for the result state
            val successState = viewModel.uiState.viewState.first()
            assertTrue(successState is ViewState.Success)
            assertEquals((successState as ViewState.Success).data, weatherResponse)
        }

    @Test
    fun `test getCurrentWeather when use case fails updates viewState to Error`() = runTest {
        // Prepare the mock error response
        `when`(getWeatherUseCase.invoke("")).thenReturn(ViewState.Error(ErrorType.RequestTimeoutError))

        // Start the function
        viewModel.getCurrentWeather()

        // Verify the loading state
        val loadingState = viewModel.uiState.viewState.first()
        assertTrue(loadingState is ViewState.Loading)

        // Wait for the error state
        val errorState = viewModel.uiState.viewState.first()
        assertTrue(errorState is ViewState.Error)
    }

    @Test
    fun `test onRefresh triggers getCurrentWeather`() = runTest {
        // Prepare the mock response
        val weatherResponse = WeatherResponse(/* provide a mock response */)
        `when`(getWeatherUseCase.invoke("")).thenReturn(ViewState.Success(weatherResponse))

        // Call onRefresh
        viewModel.uiState.onRefresh.invoke()

        // Verify that getCurrentWeather is invoked
        verify(getWeatherUseCase).invoke("")

        // Verify the state after refresh
        val successState = viewModel.uiState.viewState.first()
        assertTrue(successState is ViewState.Success)
        assertEquals((successState as ViewState.Success).data, weatherResponse)
    }
}
