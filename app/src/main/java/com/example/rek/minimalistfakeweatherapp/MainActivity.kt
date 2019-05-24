package com.example.rek.minimalistfakeweatherapp

import android.arch.lifecycle.Observer
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

        vpAdapter = MainViewPagerAdapter(supportFragmentManager)
        viewPagerMain.adapter = vpAdapter

        vModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        vModel.observableFakeDataEntities.observe(this, Observer {
            if (it != null) vpAdapter.dataSetChanged(it)
        })

        fab.setOnClickListener {
//            if (viewPagerMain.currentItem == vpAdapter.count-1) viewPagerMain.currentItem =0
            vModel.popEntity()
        }

        // Only add sample data on first run
        if (savedInstanceState == null) {
            addCity("St. Louis")
            addCity("Anaheim")
            addCity("Michigan")
            addCity("Seattle, WA")
            addCity("Miami, Fl")
            addCity("Denver, CO")
        }
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
        val newEntity = FakeDataEntity(name)
        vModel.addDataEntity(newEntity)
    }
}
