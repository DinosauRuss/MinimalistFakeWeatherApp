package com.example.rek.minimalistfakeweatherapp.architecture

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

class ViewModelWeather(application: Application): AndroidViewModel(application) {

    private val repo = RepositoryFakeData.INSTANCE

    fun addDataEntity(entity: EntityFakeData) {
        repo.addDataEntity(entity)
    }

    fun getSingleEntity(position: Int): EntityFakeData {
        return repo.getSingleEntity(position)
    }

    fun removeEntity(position: Int) {
        repo.removeEntity(position)
    }

    fun popEntity() {
        repo.popEntity()
    }

    fun getFakeData(): LiveData<ArrayList<EntityFakeData>> {
        return repo.getObservableFakeData()
    }

}



