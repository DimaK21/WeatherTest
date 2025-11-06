package ru.kryu.weathertest.presentation.screen.weather

import ru.kryu.weathertest.domain.model.WeatherData

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Success(val data: WeatherData) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}