package com.example.golden_years.weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// taken and adapted from Lab Week 4
object RetrofitObject {
    private val BASE_URL = "https://api.openweathermap.org/"

    val retrofitService: RetrofitInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(RetrofitInterface::class.java)
    }

}
