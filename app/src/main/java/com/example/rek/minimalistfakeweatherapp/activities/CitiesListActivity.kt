package com.example.rek.minimalistfakeweatherapp.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.architecture.WeatherViewModel
import com.example.rek.minimalistfakeweatherapp.utils.AdapterRecyclerCityList
import com.example.rek.minimalistfakeweatherapp.utils.Utils
import kotlinx.android.synthetic.main.activity_cities_list.*
import java.util.*

class CitiesListActivity : AppCompatActivity(), AdapterRecyclerCityList.ItemPressListener {

    private lateinit var vModelWeather: WeatherViewModel
    private lateinit var rvAdapter: AdapterRecyclerCityList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities_list)

        initLayout()
        initRecyclerView()

        vModelWeather = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        vModelWeather.getWeatherEntities().observe(this, Observer {
            if (it != null) rvAdapter.setData(it)
        })
    }

    private fun initLayout() {
        setSupportActionBar(toolbarList)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()

        // Set background as needed
        if (vModelWeather.getNumOfCities() < 1) {
            tvNoCitiesList.visibility = View.VISIBLE
        }
        if (Utils.checkForNight()) {
            citiesListContainer.background = ContextCompat.getDrawable(this, R.drawable.bg_night)
            tvNoCitiesList.setTextColor(ContextCompat.getColor(this, R.color.warm_grey))
        } else {
            citiesListContainer.background = null
            citiesListContainer.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBgDefault))
            tvNoCitiesList.setTextColor(ContextCompat.getColor(this, android.R.color.black))
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuAdd -> {
                if (vModelWeather.getNumOfCities() >= Utils.maxCities) {
                    Toast.makeText(this, getString(R.string.toast_too_many_cities), Toast.LENGTH_SHORT).show()
                    return true
                }
                val intento = Intent(this, AddCityActivity::class.java)
                startActivity(intento)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.menuSettings -> {
                val intento = Intent(this, SettingsActivity::class.java)
                startActivity(intento)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            android.R.id.home -> {  // Up/back button on toolbar
                onBackPressed()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
        return true
    }

    private fun initRecyclerView() {
        rvCityList.setHasFixedSize(true)
        rvCityList.layoutManager = LinearLayoutManager(this)
        rvAdapter = AdapterRecyclerCityList(this, this)
        rvCityList.adapter = rvAdapter
    }

    override fun onItemLongPress(position: Int) {
        val res = resources
        val name = vModelWeather.getSingleEntity(position).name

        val buildo = AlertDialog.Builder(this)
        buildo
            .setTitle(res.getString(R.string.alert_dialog_remove_title))
            .setMessage(res.getString(R.string.alert_dialog_remove_question).format(name))
            .setPositiveButton(res.getString(R.string.alert_dialog_confirm)) { dialog, which ->
                val toastMessage = resources.getString(R.string.toast_city_deleted).format(name)
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
                vModelWeather.removeEntity(position)
                if (vModelWeather.getNumOfCities() < 1) {
                    tvNoCitiesList.visibility = View.VISIBLE
                }
            }
            .setNegativeButton(res.getString(R.string.alert_dialog_cancel)) { dialog, which ->
                dialog.cancel()
            }

        val alert = buildo.create()
        alert.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}





