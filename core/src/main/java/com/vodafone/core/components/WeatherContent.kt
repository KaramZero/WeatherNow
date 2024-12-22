package com.vodafone.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.vodafone.core.domain.model.Coord
import com.vodafone.core.domain.model.Main
import com.vodafone.core.domain.model.Weather
import com.vodafone.core.domain.model.WeatherResponse
import com.vodafone.core.domain.model.Wind

@Composable
fun WeatherContent(
    modifier: Modifier = Modifier,
    weather: WeatherResponse
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "City: ${weather.name}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Text(
                    text = "Temperature: ${weather.main?.temp}°C",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Text(
                    text = weather.weather.firstOrNull()?.main ?: "",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Condition: ${weather.weather.firstOrNull()?.description ?: "N/A"}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )

                    val iconUrl =
                        "https://openweathermap.org/img/wn/${weather.weather.firstOrNull()?.icon}@2x.png"
                    Icon(
                        painter = rememberAsyncImagePainter(iconUrl),
                        contentDescription = "Weather Icon",
                        modifier = Modifier.size(40.dp)
                    )
                }
                Text(
                    text = "Humidity: ${weather.main?.humidity}%",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "Wind Speed: ${weather.wind?.speed} m/s",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
internal fun WeatherContentPreview() {
    WeatherContent(weather = mockWeatherResponse())
}

internal fun mockWeatherResponse(): WeatherResponse {
    return WeatherResponse(
        coord = Coord(lon = -0.1257, lat = 51.5085),
        weather = arrayListOf(
            Weather(id = 803, main = "Clouds", description = "broken clouds", icon = "04n")
        ),
        main = Main(temp = 281.3, humidity = 78),
        wind = Wind(speed = 7.72),
        name = "London"
    )
}