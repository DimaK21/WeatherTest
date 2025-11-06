package ru.kryu.weathertest.domain.model

data class WeatherData(
    val location: LocationInfo,
    val current: CurrentWeatherInfo,
    val hourly: List<HourlyWeatherInfo>,
    val daily: List<DailyWeatherInfo>
)

data class LocationInfo(
    val name: String,
    val localtime: String
)

data class CurrentWeatherInfo(
    val tempC: Double,
    val condition: String,
    val conditionIcon: String,
    val feelsLikeC: Double,
    val humidity: Int,
    val windKph: Double,
    val pressureMb: Double,
    val uvIndex: Double,
    val isDay: Boolean
)

data class HourlyWeatherInfo(
    val time: String,
    val timeEpoch: Long,
    val tempC: Double,
    val condition: String,
    val conditionIcon: String,
    val chanceOfRain: Int,
    val isDay: Boolean
)

data class DailyWeatherInfo(
    val date: String,
    val dateEpoch: Long,
    val maxTempC: Double,
    val minTempC: Double,
    val condition: String,
    val conditionIcon: String,
    val chanceOfRain: Int,
    val dayOfWeek: String
)
