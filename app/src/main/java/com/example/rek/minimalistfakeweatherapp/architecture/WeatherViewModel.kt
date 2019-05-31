package com.example.rek.minimalistfakeweatherapp.architecture

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData


class WeatherViewModel(application: Application): AndroidViewModel(application) {

    private val repo = WeatherRepository.getInstance(application)

    fun getWeatherEntities(): LiveData<ArrayList<WeatherEntity>> {
        return repo.getObservableWeatherEntities()
    }

    fun getSingleEntity(position: Int): WeatherEntity {
        return repo.getSingleEntity(position)
    }

    fun addCity(name: String) {
        repo.addCity(name)
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

}



