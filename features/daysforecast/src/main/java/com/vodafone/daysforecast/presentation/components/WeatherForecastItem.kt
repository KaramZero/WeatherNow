package com.vodafone.daysforecast.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodafone.core.domain.model.Main
import com.vodafone.core.domain.model.Weather
import com.vodafone.core.domain.model.WeatherForecast
import com.vodafone.core.domain.model.Wind

@Composable
fun WeatherForecastItem(forecast: WeatherForecast) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = forecast.dtTxt ?: "N/A",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Temperature: ${forecast.main?.temp}°C",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Feels Like: ${forecast.main?.feelsLike}°C",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Description: ${forecast.weather.firstOrNull()?.description.orEmpty()}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Wind Speed: ${forecast.wind?.speed} m/s",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherForecastItemPreview() {
    WeatherForecastItem(
        WeatherForecast(
            dt = 1620000000,
            main = Main(25.0, 26.0),
            weather = arrayListOf(
                Weather(
                    id = 802,
                    main = "Clouds",
                    description = "scattered clouds",
                    icon = "03n"
                )
            ),
            wind = Wind(5.0)
        )
    )
}