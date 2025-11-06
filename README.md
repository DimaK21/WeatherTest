# Weather Test

Простое приложение для отображения погоды в Москве.

## Описание

Приложение показывает текущую погоду, почасовой прогноз на сегодня и прогноз на 3 дня. Данные получаются с API WeatherAPI.

## Стек технологий

- Kotlin
- Jetpack Compose
- Clean Architecture (Data/Domain/Presentation)
- MVVM
- Koin (Dependency Injection)
- Retrofit + OkHttp (Networking)
- Kotlinx Serialization
- Coil (Image Loading)
- Material Design 3

## Функционал

- Отображение текущей погоды с основными параметрами (температура, влажность, ветер, давление)
- Почасовой прогноз на сегодня
- Прогноз на 3 дня
- Поддержка светлой и темной темы
- Pull-to-refresh
- Локализация (русский и английский)
- Обработка ошибок с возможностью повторить запрос

## API

Используется WeatherAPI (https://www.weatherapi.com/)
