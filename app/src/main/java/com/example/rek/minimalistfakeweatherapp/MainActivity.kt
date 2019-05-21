package com.example.rek.minimalistfakeweatherapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.rek.minimalistfakeweatherapp.fragments.WeatherFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {

        val vpAdapter = MainViewPagerAdapter(supportFragmentManager)
        for (i in 1..4) {
            vpAdapter.addFragment(WeatherFragment())
        }

        viewPagerMain.adapter = vpAdapter
    }
}
