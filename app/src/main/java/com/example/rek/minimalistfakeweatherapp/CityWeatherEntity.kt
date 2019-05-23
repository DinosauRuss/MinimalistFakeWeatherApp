package com.example.rek.minimalistfakeweatherapp

import com.example.rek.minimalistfakeweatherapp.utils.Utils


class CityWeatherEntity(name: String) {

    val _name: String = name
    val temp = (Utils.minTemp..Utils.maxTemp).random()

//    val weatherIconId =

//    val futureDayOne: FutureDay = FutureDay()
//    val futureDayTwo: FutureDay = FutureDay()
//    val futureDayThree: FutureDay = FutureDay()
//    val futureDayFour: FutureDay = FutureDay()


//    inner class FutureDay {

//        val futureWeatherIcon: Int = (0..3).random()
//        val tempHigh: Int = ((temp-20)..(temp+20)).random()
//        val tempLow: Int = ((tempHigh-30)..(tempHigh-10)).random()
//    }

}