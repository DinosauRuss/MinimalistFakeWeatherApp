package com.example.rek.minimalistfakeweatherapp.db

import android.arch.persistence.room.*

@Dao
interface CityDao {

    @Query("SELECT name || ', ' || region FROM world_cities WHERE name || ', ' || region LIKE :name")
    fun verifyCity(name: String): String

    @Query("SELECT name || ', ' || region FROM world_cities WHERE " +
            "name || ', ' || region LIKE  :name || '%'")
        fun getCitiesSimilar(name: String): List<String>

    @Query("SELECT name || ', ' || region FROM world_cities LIMIT 5")
    fun getFirstFive(): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(city: City)
}

