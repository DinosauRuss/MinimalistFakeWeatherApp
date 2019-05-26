package com.example.rek.minimalistfakeweatherapp.architecture

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context.MODE_PRIVATE
import com.example.rek.minimalistfakeweatherapp.utils.Utils
import com.google.gson.Gson

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

    fun removeEntity(position: Int) {
        localEntitiesArray.removeAt(position)
        observableFakeDataEntities.value = localEntitiesArray
    }

    fun popEntity() {
        localEntitiesArray.removeAt(localEntitiesArray.size - 1)
        observableFakeDataEntities.value = localEntitiesArray
    }

    fun getSingleEntity(position: Int): EntityFakeData {
        return localEntitiesArray[position]
    }

    fun saveCitiesSharedPref(application: Application) {
        val sharedPref = application.getSharedPreferences(Utils.SHARED_PREFERENCES, MODE_PRIVATE)
        val edito = sharedPref.edit()

        val namesArray = ArrayList<String>()
        for (e in localEntitiesArray) {
            namesArray.add(e.name)
        }

        val nameJson = Gson().toJson(namesArray)
        edito.putString(Utils.PREF_NAMES, nameJson)
        edito.apply()
    }

}