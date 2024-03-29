package com.cloudland.rek.minimalistfakeweatherapp.architecture

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData


class WeatherViewModel(application: Application): AndroidViewModel(application) {

    private val repo =
        com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherRepository.Companion.getInstance(application)

    fun getWeatherEntities(): LiveData<ArrayList<com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntity>> {
        return repo.getObservableWeatherEntities()
    }

    fun getSingleEntity(position: Int): com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntity {
        return repo.getSingleEntity(position)
    }

    fun addCity(name: String): Int{
        return repo.addCityWeather(name)
    }

    fun removeEntity(position: Int) {
        repo.removeEntity(position)
    }

    fun entityInModel(name: String): Boolean {
        return repo.entityInModel(name)
    }

    fun saveCitiesSharedPref() {
        repo.saveCitiesSharedPref()
    }

    fun getNumOfCities(): Int {
        return repo.getNumOfCities()
    }
}



