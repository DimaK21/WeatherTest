package ru.kryu.weathertest.presentation.screen.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.kryu.weathertest.R
import org.koin.androidx.compose.koinViewModel
import ru.kryu.weathertest.presentation.components.CurrentWeatherCard
import ru.kryu.weathertest.presentation.components.DailyForecastCard
import ru.kryu.weathertest.presentation.components.ErrorDialog
import ru.kryu.weathertest.presentation.components.HourlyForecastRow
import ru.kryu.weathertest.presentation.components.LoadingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var dialogDismissed by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        when (val state = uiState) {
            is WeatherUiState.Loading -> {
                LoadingIndicator(
                    modifier = Modifier.padding(paddingValues)
                )
                dialogDismissed = false
            }

            is WeatherUiState.Success -> {
                PullToRefreshBox(
                    isRefreshing = false,
                    onRefresh = { viewModel.loadWeather() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    WeatherContent(
                        state = state,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                dialogDismissed = false
            }

            is WeatherUiState.Error -> {
                ErrorContent(
                    onRetry = { viewModel.retry() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )

                if (!dialogDismissed) {
                    ErrorDialog(
                        message = state.message,
                        onDismiss = {
                            dialogDismissed = true
                        },
                        onRetry = {
                            viewModel.retry()
                        }
                    )
                }
            }
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

@Composable
private fun ErrorContent(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = stringResource(R.string.error_dialog_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onRetry) {
                Text(text = stringResource(R.string.error_dialog_retry))
            }
        }
    }
}