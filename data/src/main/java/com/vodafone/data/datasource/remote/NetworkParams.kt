package com.vodafone.data.datasource.remote

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkParams @Inject constructor() {
    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val API_KEY = "6f56af69aa243b830ba54b2ac9f5195d"
    }

}