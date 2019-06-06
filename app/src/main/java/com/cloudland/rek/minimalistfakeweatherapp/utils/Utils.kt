package com.cloudland.rek.minimalistfakeweatherapp.utils

import java.text.DateFormatSymbols
import java.util.*

class Utils {

    companion object {
        const val TAG = "something"
        const val numOfIcons = 4
        const val minTemp = 20
        const val maxTemp = 99
        const val maxCities = 9

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
            val nightHour: Int
            val morningHour: Int

            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val month = calendar.get(Calendar.MONTH)

            if (month in (4..9)) {
                nightHour = 20
                morningHour = 5
            } else {
                nightHour = 18
                morningHour = 6
            }

            return (hour !in (morningHour until nightHour))
        }
    }

}