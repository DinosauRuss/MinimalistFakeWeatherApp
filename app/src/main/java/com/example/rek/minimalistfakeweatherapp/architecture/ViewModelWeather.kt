package com.example.rek.minimalistfakeweatherapp.architecture

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

class ViewModelWeather(application: Application): AndroidViewModel(application) {

//    private val app = application
    private val repo = RepositoryWeatherData.getInstance(application)

    fun getWeatherData(): LiveData<ArrayList<EntityWeather>> {
        return repo.getObservableWeatherData()
    }

    fun getSingleEntity(position: Int): EntityWeather {
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



