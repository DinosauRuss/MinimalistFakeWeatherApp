package com.example.rek.minimalistfakeweatherapp.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.architecture.ViewModelWeather
import com.example.rek.minimalistfakeweatherapp.db.CityViewModel
import com.example.rek.minimalistfakeweatherapp.utils.AdapterAutoCompleteTextView
import com.example.rek.minimalistfakeweatherapp.utils.TypingDetector
import kotlinx.android.synthetic.main.activity_add_city.*

class AddCityActivity : AppCompatActivity() {

//    private val namesObject: CityNamesObject = CityNamesObject(this)
    private lateinit var vModelWeather: ViewModelWeather
    private lateinit var vModelCity: CityViewModel


//    private val vModelCities: CityViewModel =
//        ViewModelProviders.of(this).get(CityViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)

        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapto = AdapterAutoCompleteTextView(this, android.R.layout.select_dialog_item)
//        adapto.addAll(namesObject.getNames())
        actvCities.setAdapter(adapto)

        actvCities.addTextChangedListener(TypingDetector(this))

        btnAddCity.setOnClickListener { btnAddCallback() }

        vModelWeather = ViewModelProviders.of(this).get(ViewModelWeather::class.java)
        vModelCity = ViewModelProviders.of(this).get(CityViewModel::class.java)
        vModelCity.getTypingFlag().observe(this, Observer { flag ->
            if (flag == false ) {
                val text = actvCities.text.toString().trim()
                val namesList = vModelCity.getCitiesSimilar(text)
                adapto.clear()
                adapto.addAll(namesList)
                adapto.notifyDataSetChanged()
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun btnAddCallback() {
        val name = actvCities.text.toString().trim()

        if (vModelWeather.entityInModel(name)) {
            val msg = resources.getString(R.string.toast_already_registered).format(name)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//        } else if (name.isNotEmpty() && namesObject.verifyName(name) && !vModelWeather.entityInModel(name)) {
//            vModelWeather.addCity(name)
//            val msg = resources.getString(R.string.toast_added).format(name)
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//            finish()
        } else if (name.isNotEmpty() && !vModelWeather.entityInModel(name)) {
            vModelWeather.addCity(name)
            val msg = resources.getString(R.string.toast_added).format(name)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, getString(R.string.toast_unknown_city), Toast.LENGTH_SHORT).show()
        }
    }
}
