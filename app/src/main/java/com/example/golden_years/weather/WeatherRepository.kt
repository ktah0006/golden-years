package com.example.golden_years.weather

import com.example.golden_years.BuildConfig
import com.example.golden_years.weather.models.WeatherResponse


class WeatherRepository {
    private val api = RetrofitObject.retrofitService

    suspend fun fetchWeather( latFromUser: Double, lonFromUser: Double):
            WeatherResponse {
            return api.getCurrentWeather(
                BuildConfig.OPEN_WEATHER_API_KEY,
                latFromUser,
                lonFromUser)
        }

}