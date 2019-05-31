package com.example.rek.minimalistfakeweatherapp.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.widget.Toast
import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.architecture.WeatherViewModel
import com.example.rek.minimalistfakeweatherapp.db.CityAccessObject
import com.example.rek.minimalistfakeweatherapp.db.CityViewModel
import com.example.rek.minimalistfakeweatherapp.utils.AdapterAutoCompleteTextView
import com.example.rek.minimalistfakeweatherapp.utils.Utils
import kotlinx.android.synthetic.main.activity_add_city.*

class AddCityActivity : AppCompatActivity() {

    private lateinit var vModelWeather: WeatherViewModel
    private lateinit var vModelCity: CityViewModel

    private val cao = CityAccessObject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)

        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapto = AdapterAutoCompleteTextView(this, android.R.layout.select_dialog_item)
        actvCities.setAdapter(adapto)

        btnAddCity.setOnClickListener { btnAddCallback() }

        vModelWeather = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        vModelCity = ViewModelProviders.of(this).get(CityViewModel::class.java)
        vModelCity.getTypingFlag().observe(this, Observer { flag ->
            if (flag == true) {
                val text = actvCities.text.toString().trim()

                val results = cao.getCitiesSimilar(text)
                adapto.clear()
                adapto.addAll(results)
                adapto.notifyDataSetChanged()
                actvCities.showDropDown()
            }
        })

    }

    override fun onResume() {
        super.onResume()

        if (Utils.checkForNight()) {
            bgAddActivity.background = ContextCompat.getDrawable(this, R.drawable.bg_night)
        } else {
            bgAddActivity.background = null
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun btnAddCallback() {
        val name = actvCities.text.toString().trim()

        if (name.isNotEmpty() && vModelWeather.entityInModel(name)) {
            val msg = resources.getString(R.string.toast_already_registered).format(name)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        } else if (name.isNotEmpty() && cao.verifyCity(name)) {
            val addReturnVal = vModelWeather.addCity(name)
            if (addReturnVal == -1) {
                Toast.makeText(this, getString(R.string.toast_city_not_added).format(name), Toast.LENGTH_SHORT).show()
            } else {
                val msg = resources.getString(R.string.toast_added).format(name)
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, getString(R.string.toast_unknown_city), Toast.LENGTH_SHORT).show()
        }
    }

}
