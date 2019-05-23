package com.example.rek.minimalistfakeweatherapp.utils

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.rek.minimalistfakeweatherapp.CityWeatherEntity

class WeatherViewModel: ViewModel() {

    var citiesTempArray = ArrayList<CityWeatherEntity>()
//    var citiesArray = MutableLiveData<ArrayList<CityWeatherEntity>>()

    fun addCity(city: CityWeatherEntity) {
        citiesTempArray.add(city)
//        citiesArray.value = citiesTempArray
    }

}

