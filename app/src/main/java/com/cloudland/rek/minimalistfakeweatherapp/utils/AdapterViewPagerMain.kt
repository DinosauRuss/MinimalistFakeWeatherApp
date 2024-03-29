package com.cloudland.rek.minimalistfakeweatherapp.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.cloudland.rek.minimalistfakeweatherapp.fragments.WeatherFragment

class AdapterViewPagerMain(manager: FragmentManager): FragmentStatePagerAdapter(manager) {

    private var localWeatherEntities = ArrayList<com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntity>()

    override fun getItem(position: Int): Fragment {
        return WeatherFragment.newInstance(localWeatherEntities[position])
    }

    override fun getCount(): Int {
        return localWeatherEntities.size
    }

    /*
    Adapter uses this method to organize fragments after the ArrayList changes
     */
    override fun getItemPosition(`object`: Any): Int {
        if (localWeatherEntities.contains(`object`)) {
            return localWeatherEntities.indexOf(`object`)
        } else {
            return PagerAdapter.POSITION_NONE
        }
    }

    fun dataSetChanged(newDataList: ArrayList<com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntity>) {
        localWeatherEntities = newDataList
        notifyDataSetChanged()
    }



}




