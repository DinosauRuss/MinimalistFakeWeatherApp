package com.example.rek.minimalistfakeweatherapp.architecture

abstract class FutureDayWeather {

    abstract val daysAhead: Int
    abstract val iconIndex: Int
    abstract val tempHi: Int
    abstract val tempLo: Int
}