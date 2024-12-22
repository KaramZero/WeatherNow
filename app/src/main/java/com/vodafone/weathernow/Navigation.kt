package com.vodafone.weathernow

internal object Screens {

    const val CURRENT_WEATHER = "CurrentWeather"
    const val CITY_INPUT = "CityInput"
    const val FORECAST = "Forecast"

}

internal sealed class NavigationItem(val route: String) {

    data object CurrentWeather : NavigationItem(Screens.CURRENT_WEATHER)
    data object CityInput : NavigationItem(Screens.CITY_INPUT)
    data object Forecast : NavigationItem(Screens.FORECAST)

}