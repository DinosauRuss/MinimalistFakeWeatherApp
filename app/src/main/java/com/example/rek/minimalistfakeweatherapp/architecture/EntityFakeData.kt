package com.example.rek.minimalistfakeweatherapp.architecture

import com.example.rek.minimalistfakeweatherapp.utils.Utils


data class EntityFakeData(val name: String) {

    // Primary day fake weather data
//    val name = _name
    val temp = (Utils.minTemp..Utils.maxTemp).random()
    val weatherIconIndex = (0 until Utils.numOfIcons).random()

    // Future days fake weather data
    val futureDayOne = FutureDayWeather(1, temp)
    val futureDayTwo = FutureDayWeather(2, temp)
    val futureDayThree = FutureDayWeather(3, temp)
    val futureDayFour = FutureDayWeather(4, temp)


    class FutureDayWeather(val daysAhead:Int, temp: Int) {
        val iconIndex: Int = (0..3).random()
        val tempHi: Int = Utils.constrain( ((temp-10)..(temp+10)).random() )
        val tempLo: Int = Utils.constrain( ((tempHi-20)..(tempHi-10)).random() )
    }

}

