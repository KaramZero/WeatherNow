package com.vodafone.daysforecast.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vodafone.core.components.ErrorState
import com.vodafone.core.components.IdleState
import com.vodafone.core.components.LoadingState
import com.vodafone.core.domain.model.City
import com.vodafone.core.domain.model.ForecastResponse
import com.vodafone.core.domain.model.Main
import com.vodafone.core.domain.model.ViewState
import com.vodafone.core.domain.model.Weather
import com.vodafone.core.domain.model.WeatherForecast
import com.vodafone.core.domain.model.Wind
import com.vodafone.daysforecast.presentation.components.WeatherForecastItem
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ForecastScreen(
    viewModel: ForecastViewModel = hiltViewModel()
) {
    ForecastScreenUI(viewModel.uiState)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun ForecastScreenUI(
    uiState: ForecastViewModel.UiState
) {
    val viewState by uiState.viewState.collectAsState()
    LaunchedEffect(true) {
        uiState.handleIntent(ForecastIntent.GetForecast)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "5 Days Forecast",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (viewState) {
                is ViewState.Idle -> {
                    IdleState()
                }

                is ViewState.Loading -> {
                    LoadingState()
                }

                is ViewState.Success -> {
                    val weatherData = (viewState as ViewState.Success<ForecastResponse>).data
                    ForecastSuccessUI(weatherData)
                }

                is ViewState.Error -> {
                    val error = (viewState as ViewState.Error).error
                    ErrorState(
                        error,
                        onRetry = { uiState.handleIntent(ForecastIntent.GetForecast) })
                }
            }
        }
    }
}

@Composable
internal fun ForecastSuccessUI(
    forecast: ForecastResponse
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = "City: ${forecast.city?.name ?: "N/A"}",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
        items(forecast.forecasts) { weatherForecast ->
            if (weatherForecast.dtTxt?.contains("12:00:00") == true) {
                Spacer(modifier = Modifier.height(12.dp))
                WeatherForecastItem(weatherForecast)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun CurrentWeatherScreenPreview() {
    val uiState = remember {
        ForecastViewModel.UiState(
            viewState = MutableStateFlow(ViewState.Success(mockWeatherResponse())),
            handleIntent = {}
        )
    }
    ForecastScreenUI(uiState)
}

internal fun mockWeatherResponse(): ForecastResponse {
    val list = List(5) {
        WeatherForecast(
            dt = 1620000000,
            main = Main(25.0, 26.0),
            weather = arrayListOf(
                Weather(
                    id = 802,
                    main = "Clouds",
                    description = "scattered clouds",
                    icon = "03n"
                )
            ),
            wind = Wind(5.0)
        )
    } as ArrayList<WeatherForecast>
    return ForecastResponse(
        city = City(name = "London"),
        forecasts = list
    )
}
