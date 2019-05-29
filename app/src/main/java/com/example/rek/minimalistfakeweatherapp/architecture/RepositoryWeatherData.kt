package com.example.rek.minimalistfakeweatherapp.architecture

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.rek.minimalistfakeweatherapp.db.CitiesDb
import com.example.rek.minimalistfakeweatherapp.db.CityDao
import com.example.rek.minimalistfakeweatherapp.utils.SharedPrefEditor

class RepositoryWeatherData private constructor(application: Application) {


    private val observableWeatherEntities = MutableLiveData<ArrayList<EntityWeather>>()
    private val localEntitiesArray = ArrayList<EntityWeather>()

    private val prefEditorObject = SharedPrefEditor(application)

    private val cityDao: CityDao = CitiesDb.getInstance(application).cityDao()

    companion object {
        private var INSTANCE: RepositoryWeatherData? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RepositoryWeatherData(application).also { INSTANCE = it }
            }
    }


    // ----- Methods for fragments/viewpager -----
    fun getObservableWeatherData(): LiveData<ArrayList<EntityWeather>> {
        return observableWeatherEntities
    }

    fun addCity(name: String) {
        // Obtain weather data from anywhere, real or fake
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

    // ----- Methods for cities database -----
    fun verifyCity(name: String): String {
        return cityDao.verifyCity(name)
    }

    fun getCitiesSimilar(name: String): List<String> {
        return cityDao.getCitiesSimilar(name)
    }

    fun getFirstFive(): List<String> {
       return cityDao.getFirstFive()
    }

    // ----- Other methods -----
    fun saveCitiesSharedPref() {
        prefEditorObject.saveCitiesSharedPref(localEntitiesArray)
    }

    private fun generateFakeWeatherEntity(name: String): EntityFakeWeather {
        return EntityFakeWeather(name)
    }

}