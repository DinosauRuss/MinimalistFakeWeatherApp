package com.example.rek.minimalistfakeweatherapp.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.utils.AdapterViewPagerMain
import com.example.rek.minimalistfakeweatherapp.architecture.WeatherViewModel
import com.example.rek.minimalistfakeweatherapp.utils.SharedPrefObject
import com.example.rek.minimalistfakeweatherapp.utils.Utils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var vModelWeather: WeatherViewModel
    private lateinit var sharedPrefObject: SharedPrefObject
    private val vpAdapter = AdapterViewPagerMain(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()

        sharedPrefObject = SharedPrefObject(application)
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
            val namesStr = sharedPrefObject.getCitiesSharedPref()
            if (namesStr != "") {
                val namesArray = Gson().fromJson(namesStr, Array<String>::class.java).toSet()
                for (name in namesArray) {
                    Log.d(Utils.TAG, name)
                    vModelWeather.addCity(name)
                }
            }
            // Reset ViewPager to previous page
            val prevPos = sharedPrefObject.getPosition()
            // Needs to be in Handler to allow ViewPager time to fully populate
            Handler().post { viewPagerMain.currentItem = prevPos }
        }
    }

    private fun initLayout() {

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbarMain)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewPagerMain.adapter = vpAdapter
        tabLayout.setupWithViewPager(viewPagerMain)

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
                if (vModelWeather.getNumOfCities() >= Utils.maxCities) {
                    Toast.makeText(this, getString(R.string.toast_too_many_cities), Toast.LENGTH_SHORT).show()
                    return true
                }
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

    override fun onStop() {
        super.onStop()
        sharedPrefObject.savePosition(viewPagerMain.currentItem)
    }

    override fun onResume() {
        super.onResume()

        if (vpAdapter.count < 1) {
            tvNoCities.visibility = View.VISIBLE
            if (Utils.checkForNight()) {
                bgMainActivity.background = ContextCompat.getDrawable(this, R.drawable.bg_night)
                tvNoCities.setTextColor(ContextCompat.getColor(this, R.color.warm_grey))
            } else {
                bgMainActivity.background = null
                tvNoCities.setTextColor(ContextCompat.getColor(this, android.R.color.black))
            }
        } else {
            tvNoCities.visibility = View.GONE
        }
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

}


