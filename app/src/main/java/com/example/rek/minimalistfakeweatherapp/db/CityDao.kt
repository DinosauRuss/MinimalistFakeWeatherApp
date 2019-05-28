package com.example.rek.minimalistfakeweatherapp.db

import android.arch.persistence.room.*

@Dao
interface CityDao {

    @Query("SELECT * FROM cities_db WHERE name==:name")
    fun getCitiesList(name: String): List<City>

    @Query("SELECT * FROM cities_db LIMIT 5")
    fun getAll(): List<City>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(city: City)

}

