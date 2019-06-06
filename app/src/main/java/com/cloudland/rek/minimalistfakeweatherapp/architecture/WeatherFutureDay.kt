package com.cloudland.rek.minimalistfakeweatherapp.architecture

abstract class WeatherFutureDay {

    abstract val daysAhead: Int
    abstract val iconIndex: Int
    abstract val tempHi: Int
    abstract val tempLo: Int
}