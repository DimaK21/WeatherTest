package ru.kryu.weathertest.data.repository

import ru.kryu.weathertest.data.api.ApiConstants
import ru.kryu.weathertest.data.api.WeatherApiService
import ru.kryu.weathertest.data.model.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(): Result<WeatherResponse>
}

class WeatherRepositoryImpl(
    private val apiService: WeatherApiService
) : WeatherRepository {

    override suspend fun getWeather(): Result<WeatherResponse> {
        return try {
            val response = apiService.getForecast(
                key = ApiConstants.API_KEY,
                query = ApiConstants.MOSCOW_COORDINATES,
                days = ApiConstants.FORECAST_DAYS
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}