package com.example.rek.minimalistfakeweatherapp.architecture

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData


class RepositoryFakeData {

    private val observableFakeDataEntities = MutableLiveData<ArrayList<EntityFakeData>>()
    private val localEntitiesArray = ArrayList<EntityFakeData>()

    companion object {
        val INSTANCE = RepositoryFakeData()
    }

    fun getObservableFakeData(): LiveData<ArrayList<EntityFakeData>> {
        return observableFakeDataEntities
    }

    fun addDataEntity(entity: EntityFakeData) {
        localEntitiesArray.add(entity)
        observableFakeDataEntities.value = localEntitiesArray
    }

    fun getSingleEntity(position: Int): EntityFakeData {
        return localEntitiesArray[position]
    }

    fun removeEntity(position: Int) {
        localEntitiesArray.removeAt(position)
        observableFakeDataEntities.value = localEntitiesArray
    }

    fun popEntity() {
        localEntitiesArray.removeAt(localEntitiesArray.size - 1)
        observableFakeDataEntities.value = localEntitiesArray
    }

}