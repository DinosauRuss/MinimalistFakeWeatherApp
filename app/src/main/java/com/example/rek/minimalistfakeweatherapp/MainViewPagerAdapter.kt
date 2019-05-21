package com.example.rek.minimalistfakeweatherapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class MainViewPagerAdapter(manager: FragmentManager): FragmentStatePagerAdapter(manager) {

    private val fragmentList: MutableList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(frag: Fragment) {
        fragmentList.add(frag)
        notifyDataSetChanged()
    }
}