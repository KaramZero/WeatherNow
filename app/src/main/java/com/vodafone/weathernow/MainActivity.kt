package com.vodafone.weathernow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vodafone.cityinput.presentation.CityInputScreen
import com.vodafone.core.components.composableWithAnimation
import com.vodafone.currentweather.presentation.CurrentWeatherScreen
import com.vodafone.daysforecast.presentation.ForecastScreen
import com.vodafone.weathernow.ui.theme.WeatherNowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherNowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    AppNavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = rememberNavController()
                    )
                }
            }
        }
    }

    @Composable
    fun AppNavHost(
        modifier: Modifier = Modifier,
        navController: NavHostController,
        startDestination: String = NavigationItem.CurrentWeather.route
    ) {
        NavHost(
            modifier = modifier, navController = navController, startDestination = startDestination
        ) {

            composableWithAnimation(route = NavigationItem.CurrentWeather.route) {
                CurrentWeatherScreen(
                    onCityInputClick = { navController.navigate(NavigationItem.CityInput.route) },
                    onForecastClick = { navController.navigate(NavigationItem.Forecast.route) }
                )
            }

            composableWithAnimation(route = NavigationItem.CityInput.route) {
                CityInputScreen()
            }

            composableWithAnimation(route = NavigationItem.Forecast.route) {
                ForecastScreen()
            }
        }
    }

}