package com.cloudland.rek.minimalistfakeweatherapp.db

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class CityAccessObject(activity: FragmentActivity) {

    private val threado = Executors.newSingleThreadExecutor()

    private val vModelCity: CityViewModel by lazy {
        ViewModelProviders.of(activity).get(CityViewModel::class.java)
    }

    fun getCitiesSimilar(name: String): List<String> {
        var cities = listOf<String>()
        val latch = CountDownLatch(1)
        threado.execute {
            cities = vModelCity.getCitiesSimilar(name)
            latch.countDown()
        }
        latch.await()
        return cities
    }

    fun verifyCity(name: String): Boolean {
        var verify: Boolean? = null
        val latch = CountDownLatch(1)
        threado.execute {
            verify =vModelCity.verifyCity(name)
            latch.countDown()
        }
        latch.await()
        return verify ?: false
    }
}