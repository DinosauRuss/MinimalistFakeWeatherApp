package com.example.rek.minimalistfakeweatherapp.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.rek.minimalistfakeweatherapp.architecture.EntityFakeData
import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.utils.AdapterViewPagerMain
import com.example.rek.minimalistfakeweatherapp.architecture.ViewModelWeather
import com.example.rek.minimalistfakeweatherapp.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var vpAdapter: AdapterViewPagerMain
    private lateinit var vModel: ViewModelWeather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()

        vpAdapter = AdapterViewPagerMain(supportFragmentManager)
        viewPagerMain.adapter = vpAdapter

        vModel = ViewModelProviders.of(this).get(ViewModelWeather::class.java)
        vModel.getFakeData().observe(this, Observer {
            if (it != null) vpAdapter.dataSetChanged(it)
            Log.d(Utils.TAG, it?.size.toString())
        })

        // Load sample data on first run
        if (savedInstanceState == null) {
            addCity("St. Louis")
            addCity("Anaheim")
            addCity("Michigan")
            addCity("Seattle, WA")
            addCity("Miami, Fl")
//            addCity("Denver, CO")
//            addCity("Houston, TX")
//            addCity("Washington, DC")
//            addCity("Sioux Falls, ND")
//            addCity("Birmingham, AL")
//            addCity("Boston, MA")
//            addCity("Wilmington, DE")
//            addCity("Chicago, IL")
//            addCity("Wyoming")
//            addCity("Idaho")
//            addCity("Australia")
        }
    }

    private fun initLayout() {
        setContentView(R.layout.activity_main)

        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
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
            R.id.menuAdd -> Log.d(Utils.TAG, "add")
            R.id.menuSettings -> Log.d(Utils.TAG, "settings")
            else -> {
                Toast.makeText(this, resources.getString(R.string.oops), Toast.LENGTH_SHORT).show()
            }
        }
        return true
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
        val newEntity = EntityFakeData(name)
        vModel.addDataEntity(newEntity)
    }
}
