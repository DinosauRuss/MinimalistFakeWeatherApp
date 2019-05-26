package com.example.rek.minimalistfakeweatherapp.architecture

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

class ViewModelWeather(application: Application): AndroidViewModel(application) {

//    private val app = application
    private val repo = RepositoryFakeData.getInstance(application)

    fun getFakeData(): LiveData<ArrayList<EntityFakeData>> {
        return repo.getObservableFakeData()
    }

    fun getSingleEntity(position: Int): EntityFakeData {
        return repo.getSingleEntity(position)
    }

    fun addCity(name: String) {
        repo.addCity(name)
//        saveCitiesSharedPref()
    }

    fun removeEntity(position: Int) {
        repo.removeEntity(position)
//        saveCitiesSharedPref()
    }

    fun popEntity() {
        repo.popEntity()
//        saveCitiesSharedPref()
    }

//    private fun saveCitiesSharedPref() {
//        repo.saveCitiesSharedPref(app)
//    }

}



