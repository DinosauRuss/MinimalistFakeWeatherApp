package com.example.rek.minimalistfakeweatherapp.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.architecture.ViewModelWeather
import com.example.rek.minimalistfakeweatherapp.db.CityViewModel
import com.example.rek.minimalistfakeweatherapp.utils.AdapterAutoCompleteTextView
import com.example.rek.minimalistfakeweatherapp.utils.TypingDetector
import com.example.rek.minimalistfakeweatherapp.utils.Utils
import kotlinx.android.synthetic.main.activity_add_city.*
import kotlinx.android.synthetic.main.activity_add_city.view.*
import java.lang.ref.WeakReference
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddCityActivity : AppCompatActivity() {

    //    private val namesObject: CityNamesObject = CityNamesObject(this)
    private lateinit var vModelWeather: ViewModelWeather
    private lateinit var vModelCity: CityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)

        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapto = AdapterAutoCompleteTextView(this, android.R.layout.select_dialog_item)
        actvCities.setAdapter(adapto)

        btnAddCity.setOnClickListener { btnAddCallback() }

        vModelWeather = ViewModelProviders.of(this).get(ViewModelWeather::class.java)
        vModelCity = ViewModelProviders.of(this).get(CityViewModel::class.java)
        vModelCity.getTypingFlag().observe(this, Observer { flag ->
            if (flag == true) {
                val text = actvCities.text.toString().trim()
                CitiesAsync(vModelCity, adapto, this).execute(text)
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


    private class CitiesAsync(
        private val vm: CityViewModel,
        private val adapto: AdapterAutoCompleteTextView,
        activity: AddCityActivity):
            AsyncTask<String, Void, List<String>>() {

        private val weakRef = WeakReference<AddCityActivity>(activity)

        override fun doInBackground(vararg params: String?): List<String> {
            return vm.getCitiesSimilar(params[0] ?: "")
        }

        override fun onPostExecute(result: List<String>?) {
            super.onPostExecute(result)

            if (result != null) {
                adapto.clear()
                adapto.addAll(result)
                adapto.notifyDataSetChanged()

                val activity = weakRef.get()
                activity?.actvCities?.showDropDown()
            }
        }
    }

}
