package com.example.rek.minimalistfakeweatherapp.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.utils.AdapterViewPagerMain
import com.example.rek.minimalistfakeweatherapp.architecture.WeatherViewModel
import com.example.rek.minimalistfakeweatherapp.utils.Utils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var vModelWeather: WeatherViewModel
    private lateinit var vpAdapter: AdapterViewPagerMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefNamesKey = getString(R.string.PREF_NAMES)

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPrefs.registerOnSharedPreferenceChangeListener(this)

        initLayout()

        vpAdapter = AdapterViewPagerMain(supportFragmentManager)
        viewPagerMain.adapter = vpAdapter

        vModelWeather = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        vModelWeather.getWeatherEntities().observe(this, Observer {
            if (it != null) {
                vpAdapter.dataSetChanged(it)
            }
            vModelWeather.saveCitiesSharedPref()
        })

        // Load data on first run
        if (savedInstanceState == null) {
            // Load cities from SharedPreferences
            val namesStr = sharedPrefs.getString(prefNamesKey, "")
            if (namesStr != "") {
                val namesArray = Gson().fromJson(namesStr, Array<String>::class.java).toSet()
                for (name in namesArray) {
                    Log.d(Utils.TAG, name)
                    addCity(name)
                }
            }
        }
    }

    private fun initLayout() {
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbarMain)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuCities -> {
                val intento = Intent(this, CitiesListActivity::class.java)
                startActivity(intento)
            }
            R.id.menuAdd -> {
                val intento = Intent(this, AddCityActivity::class.java)
                startActivity(intento)
            }
            R.id.menuSettings -> {
                val intento = Intent(this, SettingsActivity::class.java)
                startActivity(intento)
            }
        }
        return true
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key.equals(getString(R.string.PREF_UNITS))) {
            recreate()
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
        vModelWeather.addCity(name)
    }

}


