package com.example.rek.minimalistfakeweatherapp.utils

import android.app.Application
import android.content.Context
import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.architecture.WeatherEntity
import com.google.gson.Gson

class SharedPrefEditor(application: Application) {

    private val prefsName = application.resources.getString(R.string.SHARED_PREFERENCES)
    private val prefsNameKey = application.resources.getString(R.string.PREF_NAMES)
    private val prefsUnitsKey = application.resources.getString(R.string.PREF_UNITS)

    private val sharedPrefs = application.getSharedPreferences(
        prefsName, Context.MODE_PRIVATE)
    private val edito = sharedPrefs.edit()


    fun saveCitiesSharedPref(array: ArrayList<WeatherEntity> ) {
        val namesArray = ArrayList<String>()
        for (entity in array) {
            namesArray.add(entity.name)
        }

        val nameJson = Gson().toJson(namesArray)
        edito.putString(prefsNameKey, nameJson)
        edito.apply()
    }

//    fun saveDegreeUnits(farenheight: Boolean) {
//        edito.putBoolean(prefsUnitsKey, farenheight)
//        edito.apply()
//    }

}