package ru.kryu.weathertest.di

import org.koin.dsl.module
import ru.kryu.weathertest.domain.usecase.GetWeatherUseCase

val domainModule = module {

    factory {
        GetWeatherUseCase(
            repository = get()
        )
    }
}
