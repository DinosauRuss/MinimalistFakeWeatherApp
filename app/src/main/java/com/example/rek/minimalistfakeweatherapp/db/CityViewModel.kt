package com.example.rek.minimalistfakeweatherapp.db

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import com.example.rek.minimalistfakeweatherapp.architecture.RepositoryWeatherData

class CityViewModel(application: Application): AndroidViewModel(application) {

    private val repo = RepositoryWeatherData.getInstance(application)

    private var typingFlag = MutableLiveData<Boolean>()

    fun verifyCity(name: String): String {
        return repo.verifyCity(name)
    }

    fun getCitiesSimilar(name: String): List<String> {
        return repo.getCitiesSimilar(name)
    }

    fun getFirstFive(): List<String> {
        return repo.getFirstFive()
    }

    fun typingStarted() {
        typingFlag.value = true
    }

    fun typingStopped() {
        typingFlag.value = false
    }

    fun getTypingFlag(): LiveData<Boolean> {
        return typingFlag
    }

}