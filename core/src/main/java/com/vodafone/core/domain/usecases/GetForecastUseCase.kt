package com.vodafone.core.domain.usecases

import com.vodafone.core.domain.repo.WeatherRepository
import javax.inject.Inject


class GetForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke() = repository.getForecast()
}