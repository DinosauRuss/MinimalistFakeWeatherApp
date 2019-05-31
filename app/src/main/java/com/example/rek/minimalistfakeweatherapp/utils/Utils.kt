package com.example.rek.minimalistfakeweatherapp.utils

import java.text.DateFormatSymbols
import java.util.*

class Utils {

    companion object {
        const val TAG = "something"
        const val numOfIcons = 4
        const val minTemp = 20
        const val maxTemp = 99
        const val maxCities = 9
        const val nightHour = 19
        const val morningHour = 5

        fun constrain(num:Int, min:Int=(minTemp-10), max:Int=(maxTemp+10)): Int {
            return Math.max( (Math.min(num, max)), min )
        }

        fun dayTextFromInt(day: Int): String {
            val newDay = ((day-1) % 7) + 1
            return DateFormatSymbols.getInstance().weekdays[newDay].substring(0, 3).toUpperCase()
        }

        fun convertFtoC(tempF: Int): Int {
            return Math.round( (tempF-32)/1.8f )
        }

        fun checkForNight(): Boolean {
            val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            return (hour !in (morningHour until nightHour))
        }
    }

}