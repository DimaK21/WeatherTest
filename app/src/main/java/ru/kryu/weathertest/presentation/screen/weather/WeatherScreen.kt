package ru.kryu.weathertest.presentation.screen.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.kryu.weathertest.presentation.components.CurrentWeatherCard
import ru.kryu.weathertest.presentation.components.DailyForecastCard
import ru.kryu.weathertest.presentation.components.ErrorDialog
import ru.kryu.weathertest.presentation.components.HourlyForecastRow
import ru.kryu.weathertest.presentation.components.LoadingIndicator

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        when (val state = uiState) {
            is WeatherUiState.Loading -> {
                LoadingIndicator(
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is WeatherUiState.Success -> {
                WeatherContent(
                    state = state,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            is WeatherUiState.Error -> {
                showErrorDialog = true
            }
        }

        if (showErrorDialog && uiState is WeatherUiState.Error) {
            ErrorDialog(
                message = (uiState as WeatherUiState.Error).message,
                onDismiss = {
                    showErrorDialog = false
                },
                onRetry = {
                    showErrorDialog = false
                    viewModel.retry()
                }
            )
        }
    }
}

@Composable
private fun WeatherContent(
    state: WeatherUiState.Success,
    modifier: Modifier = Modifier
) {
    val weatherData = state.data
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        CurrentWeatherCard(
            current = weatherData.current,
            locationName = weatherData.location.name,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        if (weatherData.hourly.isNotEmpty()) {
            HourlyForecastRow(
                hourlyForecast = weatherData.hourly
            )
        }

        if (weatherData.daily.isNotEmpty()) {
            DailyForecastCard(
                dailyForecast = weatherData.daily
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}