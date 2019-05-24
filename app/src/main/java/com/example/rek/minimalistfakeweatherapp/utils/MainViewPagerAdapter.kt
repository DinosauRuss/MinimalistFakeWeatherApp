package com.example.rek.minimalistfakeweatherapp.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import com.example.rek.minimalistfakeweatherapp.fragments.WeatherFragment

class MainViewPagerAdapter(manager: FragmentManager): FragmentStatePagerAdapter(manager) {

    private val fragmentList = ArrayList<WeatherFragment>()

    override fun getItem(position: Int): Fragment {
        val frag = fragmentList[position]
        frag.setPos(position)

        return frag
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment() {
        fragmentList.add( WeatherFragment() )
        notifyDataSetChanged()
    }



}




