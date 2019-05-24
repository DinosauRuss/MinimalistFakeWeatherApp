package com.example.rek.minimalistfakeweatherapp

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.rek.minimalistfakeweatherapp.utils.MainViewPagerAdapter
import com.example.rek.minimalistfakeweatherapp.architecture.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var vpAdapter: MainViewPagerAdapter
    private lateinit var vModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        vpAdapter = MainViewPagerAdapter(supportFragmentManager)
        viewPagerMain.adapter = vpAdapter

        addCity("St. Louis")
        addCity("Anaheim")
        addCity("Michigan")
        addCity("Seattle, WA")
        addCity("Miami, Fl")
        addCity("Denver, CO")
    }

    override fun onBackPressed() {
        if (viewPagerMain.currentItem == 0) {
            // Minimize app but do not destroy activity, retain created data
            moveTaskToBack(true)
        } else {
            viewPagerMain.currentItem -= 1
        }
    }

    private fun addCity(name: String) {
        vpAdapter.addFragment()

        val entity = CityWeatherEntity(name)
        vModel.addCityEntity(entity)
    }
}
