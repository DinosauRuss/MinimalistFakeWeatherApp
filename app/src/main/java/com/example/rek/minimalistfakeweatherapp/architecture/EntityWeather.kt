package com.example.rek.minimalistfakeweatherapp.architecture

abstract class EntityWeather {

    abstract val name: String
    abstract val temp: Int
    abstract val weatherIconIndex: Int

    abstract val futureDayOne: FutureDayWeather
    abstract val futureDayTwo: FutureDayWeather
    abstract val futureDayThree: FutureDayWeather
    abstract val futureDayFour: FutureDayWeather

}