package com.cloudland.rek.minimalistfakeweatherapp.architecture

import com.cloudland.rek.minimalistfakeweatherapp.utils.Utils


class WeatherEntityFake(override val name: String): com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntity() {

    // Primary day fake weather data
    override val temp = (Utils.minTemp..Utils.maxTemp).random()
    override val weatherIconIndex = (0 until Utils.numOfIcons).random()

    // Future days fake weather data
    override val futureDayOne =
        com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntityFake.FutureDayWeatherFake(1, temp)
    override val futureDayTwo =
        com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntityFake.FutureDayWeatherFake(2, temp)
    override val futureDayThree =
        com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntityFake.FutureDayWeatherFake(3, temp)
    override val futureDayFour =
        com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntityFake.FutureDayWeatherFake(4, temp)


    class FutureDayWeatherFake(override val daysAhead: Int, temp: Int): com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherFutureDay() {
        override val iconIndex = (0 until Utils.numOfIcons).random()
        override val tempHi: Int = Utils.constrain( ((temp-10)..(temp+10)).random() )
        override val tempLo: Int = Utils.constrain( ((tempHi-20)..(tempHi-10)).random() )
    }

}

