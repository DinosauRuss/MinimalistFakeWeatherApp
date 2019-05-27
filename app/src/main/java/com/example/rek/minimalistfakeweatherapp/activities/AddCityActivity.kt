package com.example.rek.minimalistfakeweatherapp.activities

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.architecture.ViewModelWeather
import com.example.rek.minimalistfakeweatherapp.utils.AdapterAutoCompleteTextView
import com.example.rek.minimalistfakeweatherapp.utils.CityNamesObject
import com.example.rek.minimalistfakeweatherapp.utils.TypingDetector
import kotlinx.android.synthetic.main.activity_add_city.*

class AddCityActivity : AppCompatActivity() {

    private val namesObject: CityNamesObject = CityNamesObject(this)
    private val vModel: ViewModelWeather by lazy {
        ViewModelProviders.of(this).get(ViewModelWeather::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)

        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapto = AdapterAutoCompleteTextView(this, android.R.layout.select_dialog_item)
        adapto.addAll(namesObject.getNames())
        actvCities.setAdapter(adapto)

        actvCities.addTextChangedListener( TypingDetector() )

        btnAddCity.setOnClickListener { btnAddCallback() }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun btnAddCallback() {
        val name = actvCities.text.toString().trim()

        if (vModel.entityInModel(name)) {
            val msg = resources.getString(R.string.toast_already_registered).format(name)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        } else if (name.isNotEmpty() && namesObject.verifyName(name) && !vModel.entityInModel(name)) {
            vModel.addCity(name)
            val msg = resources.getString(R.string.toast_added).format(name)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, getString(R.string.toast_unknown_city), Toast.LENGTH_SHORT).show()
        }
    }
}
