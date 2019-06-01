package com.example.rek.minimalistfakeweatherapp.architecture

import com.example.rek.minimalistfakeweatherapp.utils.Utils


class WeatherEntityFake(override val name: String): WeatherEntity() {

    // Primary day fake weather data
    override val temp = (Utils.minTemp..Utils.maxTemp).random()
    override val weatherIconIndex = (0 until Utils.numOfIcons).random()

    // Future days fake weather data
    override val futureDayOne = FutureDayWeatherFake(1, temp)
    override val futureDayTwo = FutureDayWeatherFake(2, temp)
    override val futureDayThree = FutureDayWeatherFake(3, temp)
    override val futureDayFour = FutureDayWeatherFake(4, temp)


    class FutureDayWeatherFake(override val daysAhead: Int, temp: Int): WeatherFutureDay() {
        override val iconIndex = (0 until Utils.numOfIcons).random()
        override val tempHi: Int = Utils.constrain( ((temp-10)..(temp+10)).random() )
        override val tempLo: Int = Utils.constrain( ((tempHi-20)..(tempHi-10)).random() )
    }

}

