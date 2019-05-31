package com.example.rek.minimalistfakeweatherapp.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.architecture.WeatherViewModel
import com.example.rek.minimalistfakeweatherapp.utils.AdapterRecyclerCityList
import kotlinx.android.synthetic.main.activity_cities_list.*

class CitiesListActivity : AppCompatActivity(), AdapterRecyclerCityList.ItemPressListener {

    private lateinit var vModel: WeatherViewModel
    private lateinit var rvAdapter: AdapterRecyclerCityList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities_list)

        setSupportActionBar(toolbarList)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initRecyclerView()

        vModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        vModel.getWeatherEntities().observe(this, Observer {
            if (it != null ) rvAdapter.setData(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuAdd -> {
                val intento = Intent(this, AddCityActivity::class.java)
                startActivity(intento)
            }
            R.id.menuSettings -> {
                val intento = Intent(this, SettingsActivity::class.java)
                startActivity(intento)
            }
            android.R.id.home -> onBackPressed()    // Up/back button on toolbar
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
        val name = vModel.getSingleEntity(position).name

        val buildo = AlertDialog.Builder(this)
        buildo
            .setTitle(res.getString(R.string.alert_dialog_remove_title))
            .setMessage(res.getString(R.string.alert_dialog_remove_question).format(name))
            .setPositiveButton(res.getString(R.string.alert_dialog_confirm)) { dialog, which ->
                val toastMessage = resources.getString(R.string.toast_city_deleted).format(name)
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
                vModel.removeEntity(position)
            }
            .setNegativeButton(res.getString(R.string.alert_dialog_cancel)) { dialog, which ->
                dialog.cancel()
            }

        val alert = buildo.create()
        alert.show()
        }

}





