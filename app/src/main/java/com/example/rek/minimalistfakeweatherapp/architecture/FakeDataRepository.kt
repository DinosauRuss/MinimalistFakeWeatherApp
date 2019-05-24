package com.example.rek.minimalistfakeweatherapp.architecture

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData


class FakeDataRepository {

    private val observableFakeDataEntities = MutableLiveData<ArrayList<FakeDataEntity>>()
    private val localEntitiesArray = ArrayList<FakeDataEntity>()

    companion object {
        val INSTANCE = FakeDataRepository()
    }

    fun getObservableFakeData(): LiveData<ArrayList<FakeDataEntity>> {
        return observableFakeDataEntities
    }

    fun addDataEntity(entity: FakeDataEntity) {
        localEntitiesArray.add(entity)
        observableFakeDataEntities.value = localEntitiesArray
    }

    fun getSingleEntity(position: Int): FakeDataEntity {
        return localEntitiesArray[position]
    }

    fun popEntity() {
        localEntitiesArray.removeAt(localEntitiesArray.size - 1)
        observableFakeDataEntities.value = localEntitiesArray
    }

}