package com.vodafone.cityinput.presentation

import androidx.lifecycle.ViewModel
import com.vodafone.core.components.SearchState
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
class CityInputViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _isSearching = MutableStateFlow(false)
    private val _searchText = MutableStateFlow("")

    private val _viewState =
        MutableStateFlow<ViewState<WeatherResponse>>(ViewState.Idle())

    private val searchState = SearchState(
        isSearching = _isSearching.asStateFlow(),
        searchText = _searchText.asStateFlow(),
        onSearchTextChange = ::onSearchTextChange,
        onToggleSearch = ::onToggleSearch,
        onSearch = ::onSearch
    )

    val uiState = UiState(
        viewState = _viewState.asStateFlow(),
        searchState = searchState,
        onRefresh = ::getCurrentWeather,
    )

    private fun getCurrentWeather(cityName:String = _searchText.value) {
        launchIO {
            _viewState.value = ViewState.Loading()
            getCurrentWeatherUseCase.invoke(cityName).let {
                _viewState.value = it
            }
        }
    }

    private fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    private fun onToggleSearch() {
        _isSearching.value = !_isSearching.value
    }
    private fun onSearch(searchText: String) {
        if (searchText.isBlank()) return
        getCurrentWeather()
        onToggleSearch()
    }

    data class UiState(
        val viewState: StateFlow<ViewState<WeatherResponse>> = MutableStateFlow(ViewState.Idle()),
        val searchState: SearchState = SearchState(),
        val onRefresh: () -> Unit = {},
    )
}