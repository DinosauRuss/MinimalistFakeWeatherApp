package com.example.rek.minimalistfakeweatherapp.activities

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.architecture.WeatherViewModel
import com.example.rek.minimalistfakeweatherapp.utils.Utils
import kotlinx.android.synthetic.main.activity_cities_list.*

class CitiesListActivity : AppCompatActivity() {

    private lateinit var vModel: WeatherViewModel

    init {
//        vModel = ViewModelProviders.of(MainActivity.class).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities_list)

        setSupportActionBar(listToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)



        tvActivityTwo.setOnClickListener() {v -> }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuCities -> Log.d(Utils.TAG, "second cities")
            R.id.menuAdd -> Log.d(Utils.TAG, "second add")
            R.id.menuSettings -> Log.d(Utils.TAG, "second settings")
        }
        return true
    }
}
