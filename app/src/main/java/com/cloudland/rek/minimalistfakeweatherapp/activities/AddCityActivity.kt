package com.cloudland.rek.minimalistfakeweatherapp.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.cloudland.rek.minimalistfakeweatherapp.R
import com.cloudland.rek.minimalistfakeweatherapp.db.CityAccessObject
import com.cloudland.rek.minimalistfakeweatherapp.db.CityViewModel
import com.cloudland.rek.minimalistfakeweatherapp.utils.AdapterAutoCompleteTextView
import com.cloudland.rek.minimalistfakeweatherapp.utils.Utils
import kotlinx.android.synthetic.main.activity_add_city.*
import kotlinx.android.synthetic.main.activity_add_views.*

class AddCityActivity : AppCompatActivity() {

    private lateinit var vModelWeather: com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherViewModel
    private lateinit var vModelCity: CityViewModel

    private val cao = CityAccessObject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)

        val toolbarAdd = findViewById<Toolbar>(R.id.toolbarAdd)
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapto = AdapterAutoCompleteTextView(this, android.R.layout.select_dialog_item)
//        val actvCities = findViewById<AutoCompleteCustom>(R.id.actvCities)
        actvCities.setAdapter(adapto)

//        val btnAddCity = findViewById<Button>(R.id.btnAddCity)
        btnAddCity.setOnClickListener { btnAddCallback() }

        vModelWeather = ViewModelProviders.of(this).get(com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherViewModel::class.java)
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
            bgAddActivity.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBgDefault))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
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
