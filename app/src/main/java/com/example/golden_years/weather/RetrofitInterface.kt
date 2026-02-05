package com.example.golden_years.weather

import com.example.golden_years.weather.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("appid") apiKey: String,
//        @Query("q") city: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}