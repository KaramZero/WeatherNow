package com.vodafone.data.di.api


import com.vodafone.core.domain.repo.WeatherRepository
import com.vodafone.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindWeatherRepository(repository: WeatherRepositoryImpl): WeatherRepository

}