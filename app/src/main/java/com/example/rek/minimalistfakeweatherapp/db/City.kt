package com.example.rek.minimalistfakeweatherapp.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "cities_db")
class City (
    @PrimaryKey
//    @ColumnInfo(name = "name")
    var name: String,

//    @ColumnInfo(name = "region")
    var region: String ) {

    fun getProperName(): String {
        return "$name, $region"
    }

}
