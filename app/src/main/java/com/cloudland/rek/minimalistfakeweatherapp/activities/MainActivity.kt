package com.cloudland.rek.minimalistfakeweatherapp.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.cloudland.rek.minimalistfakeweatherapp.R
import com.cloudland.rek.minimalistfakeweatherapp.utils.AdapterViewPagerMain
import com.cloudland.rek.minimalistfakeweatherapp.utils.SharedPrefObject
import com.cloudland.rek.minimalistfakeweatherapp.utils.Utils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_views.*


class MainActivity : AppCompatActivity() {

    companion object {
        private var currentTempUnit: String = ""
        private var alreadyExists: Boolean = false
    }

    private lateinit var vModelWeather: com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherViewModel
    private lateinit var sharedPrefObject: SharedPrefObject
    private val vpAdapter = AdapterViewPagerMain(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()

        sharedPrefObject = SharedPrefObject(application)
        vModelWeather = ViewModelProviders.of(this).get(com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherViewModel::class.java)
        vModelWeather.getWeatherEntities().observe(this, Observer {
            if (it != null) {
                vpAdapter.dataSetChanged(it)
            }
            vModelWeather.saveCitiesSharedPref()
        })

        // Load data on first run
        if (!com.cloudland.rek.minimalistfakeweatherapp.activities.MainActivity.Companion.alreadyExists) {
            // Load cities from SharedPreferences
            val namesStr = sharedPrefObject.getCitiesSharedPref()
            if (namesStr != "") {
                val namesArray = Gson().fromJson(namesStr, Array<String>::class.java).toSet()
                for (name in namesArray) {
                    vModelWeather.addCity(name)
                }
            }
            com.cloudland.rek.minimalistfakeweatherapp.activities.MainActivity.Companion.alreadyExists = true
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
                val intento = Intent(this, com.cloudland.rek.minimalistfakeweatherapp.activities.CitiesListActivity::class.java)
                startActivity(intento)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.menuAdd -> {
                if (vModelWeather.getNumOfCities() >= Utils.maxCities) {
                    Toast.makeText(this, getString(R.string.toast_too_many_cities), Toast.LENGTH_SHORT).show()
                    return true
                }
                val intento = Intent(this, com.cloudland.rek.minimalistfakeweatherapp.activities.AddCityActivity::class.java)
                startActivity(intento)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.menuSettings -> {
                val intento = Intent(this, com.cloudland.rek.minimalistfakeweatherapp.activities.SettingsActivity::class.java)
                startActivity(intento)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()

        // Change background as needed
        if (vpAdapter.count < 1) {
            tvNoCities.visibility = View.VISIBLE
            if (Utils.checkForNight()) {
                bgMainActivity.background = ContextCompat.getDrawable(this, R.drawable.bg_night)
                tvNoCities.setTextColor(ContextCompat.getColor(this, R.color.warm_grey))
            } else {
                bgMainActivity.background = null
                bgMainActivity.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBgDefault))
                tvNoCities.setTextColor(ContextCompat.getColor(this, android.R.color.black))
            }
        } else {
            tvNoCities.visibility = View.GONE
        }

        // Reset ViewPager to previous page
        val prevPos = sharedPrefObject.getPosition()
        // Needs to be in Handler to allow ViewPager time to fully populate
        Handler().post {
//            sleep(100)
            viewPagerMain.currentItem = prevPos }

        // Change temperature units as needed
        val defaultUnit = getString(R.string.FAHRENHEIT)
        val resumeTempUnit = PreferenceManager.getDefaultSharedPreferences(this)
            .getString( getString(R.string.PREF_UNITS), defaultUnit )
        if (!resumeTempUnit!!.equals(com.cloudland.rek.minimalistfakeweatherapp.activities.MainActivity.Companion.currentTempUnit)) {
            com.cloudland.rek.minimalistfakeweatherapp.activities.MainActivity.Companion.currentTempUnit = resumeTempUnit
            recreate()
        }

    }

    override fun onStop() {
        sharedPrefObject.savePosition(viewPagerMain.currentItem)
        super.onStop()
    }


//    override fun onBackPressed() {
//        if (viewPagerMain.currentItem == 0) {
//            // Minimize app but do not destroy activity, retain created data
//            moveTaskToBack(true)
//        } else {
//            viewPagerMain.currentItem -= 1
//        }
//    }

}


