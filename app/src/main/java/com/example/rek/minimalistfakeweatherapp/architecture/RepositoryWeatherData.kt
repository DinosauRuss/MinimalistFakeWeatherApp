package com.example.rek.minimalistfakeweatherapp.architecture

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.rek.minimalistfakeweatherapp.utils.SharedPrefEditor

class RepositoryWeatherData private constructor(private val application: Application) {


    private val observableWeatherEntities = MutableLiveData<ArrayList<EntityWeather>>()
    private val localEntitiesArray = ArrayList<EntityWeather>()

    private val prefEditorObject = SharedPrefEditor(application)

    companion object {
        private var INSTANCE: RepositoryWeatherData? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RepositoryWeatherData(application).also { INSTANCE = it }
            }
    }

    fun getObservableWeatherData(): LiveData<ArrayList<EntityWeather>> {
        return observableWeatherEntities
    }

    fun addCity(name: String) {
        // Obtain weather data from anywhere
        val entity = generateFakeWeatherEntity(name)

        localEntitiesArray.add(entity)
        observableWeatherEntities.value = localEntitiesArray
    }

    fun removeEntity(position: Int) {
        localEntitiesArray.removeAt(position)
        observableWeatherEntities.value = localEntitiesArray
    }

    fun getSingleEntity(position: Int): EntityWeather {
        return localEntitiesArray[position]
    }

    fun entityInModel(name: String): Boolean {
        for (entity in localEntitiesArray) {
            if (entity.name == name) return true
        }
        return false
    }

    private fun generateFakeWeatherEntity(name: String): EntityFakeWeather {
        return EntityFakeWeather(name)
    }



    fun saveCitiesSharedPref() {
        prefEditorObject.saveCitiesSharedPref(localEntitiesArray)
    }



}