package com.vodafone.currentweather.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vodafone.core.components.ErrorState
import com.vodafone.core.components.IdleState
import com.vodafone.core.components.LoadingState
import com.vodafone.core.components.WeatherContent
import com.vodafone.core.domain.model.Coord
import com.vodafone.core.domain.model.Main
import com.vodafone.core.domain.model.ViewState
import com.vodafone.core.domain.model.Weather
import com.vodafone.core.domain.model.WeatherResponse
import com.vodafone.core.domain.model.Wind
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun CurrentWeatherScreen(
    viewModel: CurrentWeatherViewModel = hiltViewModel(),
    onCityInputClick: () -> Unit,
    onForecastClick: () -> Unit
) {
    CurrentWeatherScreenUI(viewModel.uiState, onCityInputClick, onForecastClick)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun CurrentWeatherScreenUI(
    uiState: CurrentWeatherViewModel.UiState,
    onCityInputClick: () -> Unit,
    onForecastClick: () -> Unit
) {
    val viewState by uiState.viewState.collectAsState()
    LaunchedEffect(true) {
        uiState.onRefresh()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Current Weather",
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
                    val weatherData = (viewState as ViewState.Success<WeatherResponse>).data
                    WeatherSuccessUI(weatherData, onCityInputClick, onForecastClick)
                }

                is ViewState.Error -> {
                    val error = (viewState as ViewState.Error).error
                    ErrorState(error, onRetry = uiState.onRefresh)
                }
            }
        }
    }
}

@Composable
internal fun WeatherSuccessUI(
    weather: WeatherResponse,
    onCityInputClick: () -> Unit,
    onForecastClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
                .padding(12.dp)
                .fillMaxWidth()
                .clickable { onCityInputClick() },
            text = "Enter City",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
                .padding(12.dp)
                .fillMaxWidth()
                .clickable { onForecastClick() },
            text = "View 5 DaysForecast",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        WeatherContent(weather = weather)
    }
}

@Preview(showBackground = true)
@Composable
internal fun CurrentWeatherScreenPreview() {
    val uiState = remember {
        CurrentWeatherViewModel.UiState(
            viewState = MutableStateFlow(ViewState.Success(mockWeatherResponse())),
            onRefresh = {}
        )
    }
    CurrentWeatherScreenUI(uiState, {}, {})
}

internal fun mockWeatherResponse(): WeatherResponse {
    return WeatherResponse(
        coord = Coord(lon = -0.1257, lat = 51.5085),
        weather = arrayListOf(
            Weather(id = 803, main = "Clouds", description = "broken clouds", icon = "04n")
        ),
        main = Main(temp = 281.3, humidity = 78),
        wind = Wind(speed = 7.72),
        name = "London"
    )
}
