package ru.kryu.weathertest.presentation.screen.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kryu.weathertest.domain.usecase.GetWeatherUseCase

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    init {
        loadWeather()
    }

    fun loadWeather() {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading

            getWeatherUseCase()
                .onSuccess { weatherData ->
                    _uiState.value = WeatherUiState.Success(weatherData)
                }
                .onFailure { throwable ->
                    _uiState.value = WeatherUiState.Error(
                        message = throwable.message ?: "Неизвестная ошибка"
                    )
                }
        }
    }

    fun retry() {
        loadWeather()
    }
}