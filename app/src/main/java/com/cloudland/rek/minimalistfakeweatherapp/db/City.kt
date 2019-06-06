package com.cloudland.rek.minimalistfakeweatherapp.db

import android.arch.persistence.room.Entity
import android.support.annotation.NonNull

@Entity(primaryKeys = ["name", "region"], tableName = "world_cities")
data class City (

    @NonNull
    val name: String,

    @NonNull
    val region: String )

