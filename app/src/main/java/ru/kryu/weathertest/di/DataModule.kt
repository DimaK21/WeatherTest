package ru.kryu.weathertest.di

import org.koin.dsl.module
import ru.kryu.weathertest.data.repository.WeatherRepository
import ru.kryu.weathertest.data.repository.WeatherRepositoryImpl

val dataModule = module {
    single<WeatherRepository> {
        WeatherRepositoryImpl(
            apiService = get()
        )
    }
}