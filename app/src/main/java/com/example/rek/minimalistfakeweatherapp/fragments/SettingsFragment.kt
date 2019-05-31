package com.example.rek.minimalistfakeweatherapp.fragments

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.example.rek.minimalistfakeweatherapp.R


class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onCreatePreferences(p0: Bundle?, p1: String?) {}



}
