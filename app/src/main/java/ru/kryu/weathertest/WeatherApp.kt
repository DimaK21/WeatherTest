package ru.kryu.weathertest

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.kryu.weathertest.di.dataModule
import ru.kryu.weathertest.di.domainModule
import ru.kryu.weathertest.di.networkModule
import ru.kryu.weathertest.di.presentationModule

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@WeatherApp)
            modules(
                networkModule,
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }
}