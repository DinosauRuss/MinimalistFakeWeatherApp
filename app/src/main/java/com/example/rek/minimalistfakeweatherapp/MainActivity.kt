package com.example.rek.minimalistfakeweatherapp

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.rek.minimalistfakeweatherapp.fragments.WeatherFragment
import com.example.rek.minimalistfakeweatherapp.utils.MainViewPagerAdapter
import com.example.rek.minimalistfakeweatherapp.utils.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var vpAdapter: MainViewPagerAdapter
    private lateinit var vModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vpAdapter = MainViewPagerAdapter(supportFragmentManager)
        vModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        viewPagerMain.adapter = vpAdapter

        addCity("St. Louis")
        addCity("Anaheim")
    }

    fun addCity(name: String) {
        val newPos = vpAdapter.count
        vpAdapter.addFragment(WeatherFragment.newInstance(newPos))
        val entity = CityWeatherEntity(name)
        vModel.addCity(entity)
    }
}
