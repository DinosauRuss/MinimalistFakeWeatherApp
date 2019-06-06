package com.cloudland.rek.minimalistfakeweatherapp.architecture

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.cloudland.rek.minimalistfakeweatherapp.db.CitiesDb
import com.cloudland.rek.minimalistfakeweatherapp.db.CityDao
import com.cloudland.rek.minimalistfakeweatherapp.utils.SharedPrefObject
import com.cloudland.rek.minimalistfakeweatherapp.utils.Utils


class WeatherRepository private constructor(application: Application) {

    private val observableWeatherEntities = MutableLiveData<ArrayList<com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntity>>()
    private val localEntitiesArray = ArrayList<com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntity>()

    private val sharedPrefObject = SharedPrefObject(application)
    private val cityDao: CityDao = CitiesDb.getInstance(application).cityDao()

    companion object {
        private var INSTANCE: com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherRepository? = null

        fun getInstance(application: Application) =
            com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherRepository.Companion.INSTANCE ?: synchronized(this) {
                com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherRepository.Companion.INSTANCE
                    ?: com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherRepository(application).also { com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherRepository.Companion.INSTANCE = it }
            }
    }


    // ----- Methods for fragments/viewpager -----
    fun getObservableWeatherEntities(): LiveData<ArrayList<com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntity>> {
        return observableWeatherEntities
    }

    fun addCityWeather(name: String): Int {
        if (localEntitiesArray.size < Utils.maxCities) {
            // Obtain weather data from anywhere, real or fake
            val entity = generateFakeWeatherEntity(name)
            localEntitiesArray.add(entity)
            observableWeatherEntities.value = localEntitiesArray
            return 1
        } else {
            return -1
        }
    }

    fun removeEntity(position: Int) {
        localEntitiesArray.removeAt(position)
        observableWeatherEntities.value = localEntitiesArray
    }

    fun getSingleEntity(position: Int): com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntity {
        return localEntitiesArray[position]
    }

    fun entityInModel(name: String): Boolean {
        for (entity in localEntitiesArray) {
            if (entity.name == name) return true
        }
        return false
    }

    fun getNumOfCities(): Int {
        return localEntitiesArray.size
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
        sharedPrefObject.saveCitiesSharedPref(localEntitiesArray)
    }

    private fun generateFakeWeatherEntity(name: String): com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntityFake {
        return com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntityFake(name)
    }

}