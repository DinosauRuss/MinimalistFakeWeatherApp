package com.example.rek.minimalistfakeweatherapp.utils

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.architecture.WeatherEntity
import com.google.gson.Gson


class SharedPrefObject(private val application: Application) {

    private val prefsNameKey = application.getString(R.string.PREF_NAMES)
    private val prefPosKey = application.getString(R.string.PREF_POSITION)
    private val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(application)
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

    fun getCitiesSharedPref(): String {
        return sharedPrefs.getString(prefsNameKey, "") ?: ""
    }

    fun savePosition(position: Int) {
        val posKey = application.getString(R.string.PREF_POSITION)
        edito.putInt(posKey, position)
        edito.apply()
    }

    fun getPosition(): Int {
        return sharedPrefs.getInt(prefPosKey, 0)
    }
}