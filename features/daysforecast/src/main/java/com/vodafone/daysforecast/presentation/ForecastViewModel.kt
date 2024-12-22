package com.vodafone.daysforecast.presentation

import androidx.lifecycle.ViewModel
import com.vodafone.core.domain.model.ForecastResponse
import com.vodafone.core.domain.model.ViewState
import com.vodafone.core.domain.usecases.GetForecastUseCase
import com.vodafone.core.utilities.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {

    private val _viewState =
        MutableStateFlow<ViewState<ForecastResponse>>(ViewState.Idle())

    val uiState = UiState(
        viewState = _viewState.asStateFlow(),
        handleIntent = ::handleIntent,
    )


    private fun handleIntent(intent: ForecastIntent) {
        when (intent) {
            is ForecastIntent.GetForecast -> getForecast()
        }
    }

    private fun getForecast() {
        launchIO {
            _viewState.value = ViewState.Loading()
            getForecastUseCase.invoke().let {
                _viewState.value = it
            }
        }
    }

    data class UiState(
        val viewState: StateFlow<ViewState<ForecastResponse>> = MutableStateFlow(ViewState.Idle()),
        val handleIntent: (ForecastIntent) -> Unit = {},
    )
}