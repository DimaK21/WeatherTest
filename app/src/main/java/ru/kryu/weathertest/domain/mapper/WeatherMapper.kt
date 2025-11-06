package ru.kryu.weathertest.domain.mapper

import ru.kryu.weathertest.data.model.WeatherResponse
import ru.kryu.weathertest.domain.model.CurrentWeatherInfo
import ru.kryu.weathertest.domain.model.DailyWeatherInfo
import ru.kryu.weathertest.domain.model.HourlyWeatherInfo
import ru.kryu.weathertest.domain.model.LocationInfo
import ru.kryu.weathertest.domain.model.WeatherData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object WeatherMapper {

    fun mapToWeatherData(response: WeatherResponse): WeatherData {
        return WeatherData(
            location = mapLocation(response),
            current = mapCurrentWeather(response),
            hourly = mapHourlyWeather(response),
            daily = mapDailyWeather(response)
        )
    }

    private fun mapLocation(response: WeatherResponse): LocationInfo {
        return LocationInfo(
            name = response.location.name,
            localtime = response.location.localtime
        )
    }

    private fun mapCurrentWeather(response: WeatherResponse): CurrentWeatherInfo {
        return CurrentWeatherInfo(
            tempC = response.current.tempC,
            condition = response.current.condition.text,
            conditionIcon = normalizeIconUrl(response.current.condition.icon),
            feelsLikeC = response.current.feelslikeC,
            humidity = response.current.humidity,
            windKph = response.current.windKph,
            pressureMb = response.current.pressureMb,
            uvIndex = response.current.uv,
            isDay = response.current.isDay == 1
        )
    }

    private fun mapHourlyWeather(response: WeatherResponse): List<HourlyWeatherInfo> {
        val today = response.forecast.forecastday.firstOrNull() ?: return emptyList()
        val currentTimeEpoch = response.location.localtimeEpoch
        return today.hour
            .filter { it.timeEpoch >= currentTimeEpoch }
            .map { hour ->
                HourlyWeatherInfo(
                    time = formatTime(hour.time),
                    timeEpoch = hour.timeEpoch,
                    tempC = hour.tempC,
                    condition = hour.condition.text,
                    conditionIcon = normalizeIconUrl(hour.condition.icon),
                    chanceOfRain = hour.chanceOfRain,
                    isDay = hour.isDay == 1
                )
            }
    }

    private fun mapDailyWeather(response: WeatherResponse): List<DailyWeatherInfo> {
        return response.forecast.forecastday.map { day ->
            DailyWeatherInfo(
                date = day.date,
                dateEpoch = day.dateEpoch,
                maxTempC = day.day.maxtempC,
                minTempC = day.day.mintempC,
                condition = day.day.condition.text,
                conditionIcon = normalizeIconUrl(day.day.condition.icon),
                chanceOfRain = day.day.dailyChanceOfRain,
                dayOfWeek = getDayOfWeek(day.dateEpoch)
            )
        }
    }

    private fun normalizeIconUrl(icon: String): String {
        return if (icon.startsWith("//")) {
            "https:$icon"
        } else {
            icon
        }
    }

    private fun formatTime(time: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = inputFormat.parse(time)
            date?.let { outputFormat.format(it) } ?: time
        } catch (e: Exception) {
            time
        }
    }

    private fun getDayOfWeek(epochSeconds: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = epochSeconds * 1000

        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        return dayFormat.format(Date(calendar.timeInMillis))
    }
}