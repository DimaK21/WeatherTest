package ru.kryu.weathertest.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.kryu.weathertest.presentation.screen.weather.WeatherViewModel

val presentationModule = module {

    viewModel {
        WeatherViewModel(
            getWeatherUseCase = get()
        )
    }
}