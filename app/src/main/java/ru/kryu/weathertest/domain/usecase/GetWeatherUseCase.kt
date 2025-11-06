package ru.kryu.weathertest.domain.usecase

import ru.kryu.weathertest.data.repository.WeatherRepository
import ru.kryu.weathertest.domain.mapper.WeatherMapper
import ru.kryu.weathertest.domain.model.WeatherData

class GetWeatherUseCase(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(): Result<WeatherData> {
        return repository.getWeather()
            .mapCatching { response ->
                WeatherMapper.mapToWeatherData(response)
            }
    }
}