package com.example.golden_years.weather

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golden_years.weather.models.WeatherResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    var weather by mutableStateOf<WeatherResponse?>(null)
    private val wRepository = WeatherRepository()
    fun getWeather(context: Context) {
        lateinit var fusedLocationClient: FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

//        Log.d("WEATHER", "outside listener")

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
//                Log.d("WEATHER", "inside listener")
                if (location != null) {
//                    Log.d("WEATHER", "location isnt null")
                    viewModelScope.launch {
                        try {
//                            Log.d("WEATHER", "before Fetching")
                            weather = wRepository.fetchWeather(
                                location.latitude,
                                location.longitude
                            )
                            Log.d("WEATHER", "Weather Fetched: ${location.latitude} ${location.longitude}")
                        } catch (e: Exception) {
                            Log.e("WEATHER", "error: ${e.message}")
                        }
                    }
                } else {
                    Log.e("WEATHER", "Location is null")
                }
            }
    }
}