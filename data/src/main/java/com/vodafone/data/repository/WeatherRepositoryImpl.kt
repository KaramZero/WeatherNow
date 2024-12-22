package com.vodafone.data.repository

import com.vodafone.core.domain.model.ForecastResponse
import com.vodafone.core.domain.model.ViewState
import com.vodafone.core.domain.model.WeatherResponse
import com.vodafone.core.domain.repo.WeatherRepository
import com.vodafone.core.utilities.networkHelper.safeApiCall
import com.vodafone.data.datasource.local.CityDao
import com.vodafone.data.datasource.remote.WeatherRemoteSource
import com.vodafone.data.model.City
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val remoteSource: WeatherRemoteSource,
    private val localSource: CityDao
) : WeatherRepository {

    override suspend fun getWeather(cityName: String): ViewState<WeatherResponse> = safeApiCall {
        val city = cityName.ifBlank { getCityName() }
        val res = remoteSource.getWeather(city)
        if (res.cod == 200) insertCityName(city)
        res
    }

    override suspend fun getForecast(): ViewState<ForecastResponse> = safeApiCall {
        val city = getCityName()
        val res = remoteSource.getForecast(city)
        if (res.cod == 200) insertCityName(city)
        res
    }

    private suspend fun getCityName(): String {
        return try {
            localSource.getCity().name ?: "London"
        } catch (e: Exception) {
            "London"
        }
    }

    private suspend fun insertCityName(cityName: String) {
        try {
            localSource.insertCity(City(name = cityName))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}