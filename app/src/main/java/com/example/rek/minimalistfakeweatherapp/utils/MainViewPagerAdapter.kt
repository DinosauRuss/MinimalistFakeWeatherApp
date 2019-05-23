package com.example.rek.minimalistfakeweatherapp.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log

class MainViewPagerAdapter(manager: FragmentManager): FragmentStatePagerAdapter(manager) {

    private val fragmentList: MutableList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(frag: Fragment) {
        fragmentList.add(frag)
        notifyDataSetChanged()
    }

}


