package com.example.rek.minimalistfakeweatherapp.utils

import android.app.Application
import android.content.Context
import com.example.rek.minimalistfakeweatherapp.architecture.EntityWeather
import com.google.gson.Gson

class SharedPrefEditor(private val application: Application) {

    fun saveCitiesSharedPref(array: ArrayList<EntityWeather> ) {
        val sharedPref = application.getSharedPreferences(Utils.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val edito = sharedPref.edit()

        val namesArray = ArrayList<String>()
        for (entity in array) {
            namesArray.add(entity.name)
        }

        val nameJson = Gson().toJson(namesArray)
        edito.putString(Utils.PREF_NAMES, nameJson)
        edito.apply()
    }
}