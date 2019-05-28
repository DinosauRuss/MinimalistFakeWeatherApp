package com.example.rek.minimalistfakeweatherapp.db

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.util.Log
import com.example.rek.minimalistfakeweatherapp.architecture.RepositoryWeatherData
import com.example.rek.minimalistfakeweatherapp.utils.Utils

class CityViewModel(application: Application): AndroidViewModel(application) {

    private val repo = RepositoryWeatherData.getInstance(application)

//    fun getCitiesList(name: String): ArrayList<String> {
//        return repo.getCities(name)
//    }

    fun getAll(): List<String> {
        val cities = repo.getAll()
//        Log.d(Utils.TAG, "vm: $cities")

        val strList = ArrayList<String>()
        for (city in cities) {
            strList.add(city.getProperName())
        }

        return strList
    }

    fun insert(city: City) {
        repo.insert(city)
    }
}