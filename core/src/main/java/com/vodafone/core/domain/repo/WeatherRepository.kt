package com.vodafone.core.domain.repo

import com.vodafone.core.domain.model.ForecastResponse
import com.vodafone.core.domain.model.ViewState
import com.vodafone.core.domain.model.WeatherResponse


interface WeatherRepository {

    suspend fun getWeather(cityName:String): ViewState<WeatherResponse>
    suspend fun getForecast(): ViewState<ForecastResponse>
}