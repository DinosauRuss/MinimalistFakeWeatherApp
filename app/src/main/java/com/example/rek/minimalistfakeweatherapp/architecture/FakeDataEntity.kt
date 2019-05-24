package com.example.rek.minimalistfakeweatherapp.architecture

import com.example.rek.minimalistfakeweatherapp.utils.Utils
import java.io.Serializable


class FakeDataEntity(_name: String): Serializable {

    // Primary day fake weather data
    val name = _name
    val temp = (Utils.minTemp..Utils.maxTemp).random()
    val weatherIconIndex = (0 until Utils.numOfIcons).random()

    // Future days fake weather data
    val futureDayOne: ArrayList<Int> = generatFutureData()
    val futureDayTwo: ArrayList<Int> = generatFutureData()
    val futureDayThree: ArrayList<Int> = generatFutureData()
    val futureDayFour: ArrayList<Int> = generatFutureData()

    /*
    Generate array list of future day fake weather data
    in form (icon_index, temp_high, temp_low)
     */
    private fun generatFutureData(): ArrayList<Int> {
        val icon: Int = (0..3).random()
        val high: Int = ((temp-20)..(temp+20)).random()
        val low: Int = ((high-30)..(high-10)).random()

        val tempList = ArrayList<Int>()
        tempList.add(icon)
        tempList.add(high)
        tempList.add(low)

        return tempList
    }

}