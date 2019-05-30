package com.example.rek.minimalistfakeweatherapp.db

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.rek.minimalistfakeweatherapp.architecture.RepositoryWeatherData

class CityViewModel(application: Application): AndroidViewModel(application) {

    private val repo = RepositoryWeatherData.getInstance(application)

    private var allowNewData = MutableLiveData<Boolean>()

    init {
        allowNewData.postValue(false)
    }

    fun verifyCity(name: String): Boolean {
        return repo.verifyCity(name) != null
    }

    fun getCitiesSimilar(name: String): List<String> {
        return repo.getCitiesSimilar(name)
    }

    fun getFirstFive(): List<String> {
        return repo.getFirstFive()
    }

    fun doNotAllowNewData() {
        allowNewData.postValue(false)
    }

    fun allowNewData() {
        allowNewData.postValue(true)
    }

    fun getTypingFlag(): LiveData<Boolean> {
        return allowNewData
    }

}