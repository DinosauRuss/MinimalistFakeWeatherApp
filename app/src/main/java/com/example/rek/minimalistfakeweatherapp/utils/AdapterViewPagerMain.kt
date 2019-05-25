package com.example.rek.minimalistfakeweatherapp.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.example.rek.minimalistfakeweatherapp.architecture.EntityFakeData
import com.example.rek.minimalistfakeweatherapp.fragments.WeatherFragment

class AdapterViewPagerMain(manager: FragmentManager): FragmentStatePagerAdapter(manager) {

    private var adapterFakeDataEntities = ArrayList<EntityFakeData>()

    override fun getItem(position: Int): Fragment {
        return WeatherFragment.newInstance(adapterFakeDataEntities[position])
    }

    override fun getCount(): Int {
        return adapterFakeDataEntities.size
    }

    /*
    Adapter needs this to organize fragments after the data changes
     */
    override fun getItemPosition(`object`: Any): Int {
        if (adapterFakeDataEntities.contains(`object`)) {
            return adapterFakeDataEntities.indexOf(`object`)
        } else {
            return PagerAdapter.POSITION_NONE
        }
    }

    fun dataSetChanged(newDataList: ArrayList<EntityFakeData>) {
        adapterFakeDataEntities = newDataList
        notifyDataSetChanged()
    }



}




