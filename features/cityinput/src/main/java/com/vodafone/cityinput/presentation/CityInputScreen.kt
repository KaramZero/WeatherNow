package com.vodafone.cityinput.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import com.vodafone.core.components.SearchView
import com.vodafone.core.components.WeatherContent
import com.vodafone.core.domain.model.Coord
import com.vodafone.core.domain.model.Main
import com.vodafone.core.domain.model.ViewState
import com.vodafone.core.domain.model.Weather
import com.vodafone.core.domain.model.WeatherResponse
import com.vodafone.core.domain.model.Wind
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun CityInputScreen(
    viewModel: CityInputViewModel = hiltViewModel()
) {
    CityInputScreenUI(viewModel.uiState)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun CityInputScreenUI(
    uiState: CityInputViewModel.UiState
) {
    val viewState by uiState.viewState.collectAsState()
    val searchState = uiState.searchState
    val isSearching by searchState.isSearching.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Search City",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            SearchView(
                modifier = Modifier.padding(16.dp),
                searchState = searchState
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (!isSearching)
                when (viewState) {
                    is ViewState.Idle -> {
                        IdleState()
                    }

                    is ViewState.Loading -> {
                        LoadingState()
                    }

                    is ViewState.Success -> {
                        val weatherData = (viewState as ViewState.Success<WeatherResponse>).data
                        WeatherContent(
                            modifier = Modifier
                                .padding(16.dp),
                            weather = weatherData
                        )
                    }

                    is ViewState.Error -> {
                        val error = (viewState as ViewState.Error).error
                        ErrorState(error, onRetry = uiState.onRefresh)
                    }
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun CurrentWeatherScreenPreview() {
    val uiState = remember {
        CityInputViewModel.UiState(
            viewState = MutableStateFlow(ViewState.Success(mockWeatherResponse())),
            onRefresh = {}
        )
    }
    CityInputScreenUI(uiState)
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
