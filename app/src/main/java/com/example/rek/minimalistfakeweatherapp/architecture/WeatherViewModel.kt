package com.example.rek.minimalistfakeweatherapp.architecture

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import android.support.v4.app.Fragment
import android.util.Log
import com.example.rek.minimalistfakeweatherapp.CityWeatherEntity

class WeatherViewModel(application: Application): AndroidViewModel(application) {

    val localEntitiesArray = ArrayList<CityWeatherEntity>()

    fun addCityEntity(entity: CityWeatherEntity) {
        localEntitiesArray.add(entity)
    }
}

