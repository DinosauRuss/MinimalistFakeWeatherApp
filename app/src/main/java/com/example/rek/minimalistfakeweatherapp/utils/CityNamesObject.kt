package com.example.rek.minimalistfakeweatherapp.utils

import android.content.Context
import com.example.rek.minimalistfakeweatherapp.R

class CityNamesObject(context: Context) {

    private val namesList: Array<String> by lazy {
        context.resources.getStringArray(R.array.temp_cities_list)
    }

        fun getNames(): Array<String>  {
            return namesList
        }

        fun verifyName(name: String): Boolean {
            return name in namesList
        }

}



