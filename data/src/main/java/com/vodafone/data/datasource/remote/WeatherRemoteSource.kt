package com.vodafone.data.datasource.remote

import com.vodafone.core.domain.model.WeatherResponse
import com.vodafone.core.utilities.networkHelper.handleResponse
import javax.inject.Inject

class WeatherRemoteSource @Inject constructor(
    private val apiServices: WeatherApiServices
) {

    suspend fun getWeather(cityName: String): WeatherResponse =
        handleResponse(
            apiServices.getWeather(
                cityName = cityName,
                apiKey = NetworkParams.API_KEY
            )
        )
}