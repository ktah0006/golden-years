package com.example.golden_years.weather.models

data class WeatherResponse(
    val name: String,
    val weather: List<Weather>,
    val main: Main
)