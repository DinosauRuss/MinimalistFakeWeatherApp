package com.cloudland.rek.minimalistfakeweatherapp.architecture

abstract class WeatherEntity {

    abstract val name: String
    abstract val temp: Int
    abstract val weatherIconIndex: Int

    abstract val futureDayOne: com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherFutureDay
    abstract val futureDayTwo: com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherFutureDay
    abstract val futureDayThree: com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherFutureDay
    abstract val futureDayFour: com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherFutureDay

}