package com.example.rek.minimalistfakeweatherapp

import com.example.rek.minimalistfakeweatherapp.utils.Utils


class CityWeatherEntity(_name: String) {

    val name = _name
    val temp = (Utils.minTemp..Utils.maxTemp).random()
    val weatherIconIndex = (0 until Utils.numOfIcons).random()

    val futureDayOne: FutureDay = FutureDay()
    val futureDayTwo: FutureDay = FutureDay()
    val futureDayThree: FutureDay = FutureDay()
    val futureDayFour: FutureDay = FutureDay()


    inner class FutureDay {
        val futureWeatherIcon: Int = (0..3).random()
        val tempHigh: Int = ((temp-20)..(temp+20)).random()
        val tempLow: Int = ((tempHigh-30)..(tempHigh-10)).random()
    }

}