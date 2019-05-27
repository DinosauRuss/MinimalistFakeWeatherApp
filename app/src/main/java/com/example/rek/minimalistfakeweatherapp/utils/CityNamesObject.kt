package com.example.rek.minimalistfakeweatherapp.utils

import android.content.Context
import com.example.rek.minimalistfakeweatherapp.R

class CityNamesObject(context: Context) {

    private val namesList: List<String> by lazy {
        context.resources.getStringArray(R.array.temp_cities_list).asList()
    }

        fun getNames(): List<String>  {
            return namesList
        }

        fun verifyName(name: String): Boolean {
            return name in namesList
        }

}



