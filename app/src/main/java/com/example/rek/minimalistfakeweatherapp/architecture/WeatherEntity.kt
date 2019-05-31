package com.example.rek.minimalistfakeweatherapp.architecture

abstract class WeatherEntity {

    abstract val name: String
    abstract val temp: Int
    abstract val weatherIconIndex: Int

    abstract val futureDayOne: WeatherFutureDay
    abstract val futureDayTwo: WeatherFutureDay
    abstract val futureDayThree: WeatherFutureDay
    abstract val futureDayFour: WeatherFutureDay

}