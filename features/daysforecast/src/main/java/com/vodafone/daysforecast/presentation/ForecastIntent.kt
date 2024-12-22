package com.vodafone.daysforecast.presentation

sealed class ForecastIntent{
    data object GetForecast : ForecastIntent()
}