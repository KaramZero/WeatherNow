package com.vodafone.core.domain.usecases

import com.vodafone.core.domain.repo.WeatherRepository
import javax.inject.Inject


class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(cityName:String) = repository.getWeather(cityName)
}