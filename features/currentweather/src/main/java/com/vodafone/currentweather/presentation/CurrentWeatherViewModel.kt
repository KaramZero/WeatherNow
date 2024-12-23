package com.vodafone.currentweather.presentation

import androidx.lifecycle.ViewModel
import com.vodafone.core.domain.model.ViewState
import com.vodafone.core.domain.model.WeatherResponse
import com.vodafone.core.domain.usecases.GetWeatherUseCase
import com.vodafone.core.utilities.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _viewState =
        MutableStateFlow<ViewState<WeatherResponse>>(ViewState.Idle())

    val uiState = UiState(
        viewState = _viewState.asStateFlow(),
        onRefresh = ::getCurrentWeather,
    )

    init {
        getCurrentWeather()
    }

    fun getCurrentWeather() {
        launchIO {
            _viewState.value = ViewState.Loading()
            getCurrentWeatherUseCase.invoke("").let {
                _viewState.value = it
            }
        }
    }

    data class UiState(
        val viewState: StateFlow<ViewState<WeatherResponse>> = MutableStateFlow(ViewState.Idle()),
        val onRefresh: () -> Unit = {},
    )
}