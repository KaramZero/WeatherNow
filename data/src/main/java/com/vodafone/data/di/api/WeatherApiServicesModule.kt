package com.vodafone.data.di.api

import com.vodafone.data.datasource.remote.WeatherApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WeatherApiServicesModule {

    @Singleton
    @Provides
    fun provideWeatherApiServices(retrofit: Retrofit): WeatherApiServices =
        retrofit.create(WeatherApiServices::class.java)

}