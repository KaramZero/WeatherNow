package com.vodafone.weatherutils

object WeatherFormatter {
    fun formatTemperature(temp: Double?): String {
        return "Temperature: ${temp?.toInt() ?: 0}°C"
    }

    fun formatHumidity(humidity: Int?): String {
        return "Humidity: $humidity%"
    }

    fun formatCondition(description: String?): String {
        return "Condition: ${description ?: "N/A"}"
    }

    fun formatWindSpeed(speed: Double?): String {
        return "Wind Speed: ${speed}km/h"
    }
}