package com.example.rek.minimalistfakeweatherapp.architecture

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.example.rek.minimalistfakeweatherapp.FakeDataEntity

class WeatherViewModel(application: Application): AndroidViewModel(application) {

    val observableFakeDataEntities = MutableLiveData<ArrayList<FakeDataEntity>>()
    private val localEntitiesArray = ArrayList<FakeDataEntity>()

    fun addDataEntity(entity: FakeDataEntity) {
        localEntitiesArray.add(entity)
        observableFakeDataEntities.value = localEntitiesArray
    }

    fun popEntity() {
        localEntitiesArray.removeAt(localEntitiesArray.size - 1)
        observableFakeDataEntities.value = localEntitiesArray
    }

}

