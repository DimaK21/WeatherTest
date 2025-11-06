package ru.kryu.weathertest.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.kryu.weathertest.data.model.WeatherResponse

interface WeatherApiService {
    @GET("v1/forecast.json")
    suspend fun getForecast(
        @Query("key") key: String,
        @Query("q") query: String,
        @Query("days") days: Int
    ): WeatherResponse
}