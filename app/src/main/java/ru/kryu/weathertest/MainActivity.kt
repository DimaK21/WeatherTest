package ru.kryu.weathertest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.kryu.weathertest.presentation.screen.weather.WeatherScreen
import ru.kryu.weathertest.ui.theme.WeatherTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherTestTheme {
                WeatherScreen()
            }
        }
    }
}