package com.example.rek.minimalistfakeweatherapp.architecture

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context.MODE_PRIVATE
import com.example.rek.minimalistfakeweatherapp.utils.Utils
import com.google.gson.Gson

class RepositoryFakeData private constructor(private val application: Application) {

    private val observableFakeDataEntities = MutableLiveData<ArrayList<EntityFakeData>>()
    private val localEntitiesArray = ArrayList<EntityFakeData>()

    companion object {

        private var INSTANCE: RepositoryFakeData? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RepositoryFakeData(application).also { INSTANCE = it }
        }
    }


    fun getObservableFakeData(): LiveData<ArrayList<EntityFakeData>> {
        return observableFakeDataEntities
    }

    fun addCity(name: String) {
        // Obtain data entity from anywhere
        val entity = generateFakeDataEntity(name)

        localEntitiesArray.add(entity)
        observableFakeDataEntities.value = localEntitiesArray

        saveCitiesSharedPref()
    }

    fun removeEntity(position: Int) {
        localEntitiesArray.removeAt(position)
        observableFakeDataEntities.value = localEntitiesArray

        saveCitiesSharedPref()
    }

    fun popEntity() {
        localEntitiesArray.removeAt(localEntitiesArray.size - 1)
        observableFakeDataEntities.value = localEntitiesArray

        saveCitiesSharedPref()
    }

    fun getSingleEntity(position: Int): EntityFakeData {
        return localEntitiesArray[position]
    }

    private fun saveCitiesSharedPref() {
        val sharedPref = application.getSharedPreferences(Utils.SHARED_PREFERENCES, MODE_PRIVATE)
        val edito = sharedPref.edit()

        val namesArray = ArrayList<String>()
        for (entity in localEntitiesArray) {
            namesArray.add(entity.name)
        }

        val nameJson = Gson().toJson(namesArray)
        edito.putString(Utils.PREF_NAMES, nameJson)
        edito.apply()
    }

    private fun generateFakeDataEntity(name: String): EntityFakeData {
        return EntityFakeData(name)
    }

}