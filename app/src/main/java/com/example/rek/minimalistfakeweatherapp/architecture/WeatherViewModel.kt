package com.example.rek.minimalistfakeweatherapp.architecture

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.rek.minimalistfakeweatherapp.utils.Utils

class WeatherViewModel(application: Application): AndroidViewModel(application) {

    private val repo = FakeDataRepository.INSTANCE

//    val observableFakeDataEntities = MutableLiveData<ArrayList<FakeDataEntity>>()

    fun addDataEntity(entity: FakeDataEntity) {
        repo.addDataEntity(entity)
    }

    fun popEntity() {
        repo.popEntity()
    }

    fun getFakeData(): LiveData<ArrayList<FakeDataEntity>> {
        return repo.getObservableFakeData()
    }

}

