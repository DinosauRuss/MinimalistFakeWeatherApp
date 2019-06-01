package com.example.rek.minimalistfakeweatherapp.fragments

import android.os.Bundle
import android.support.v7.preference.ListPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceManager
import com.example.rek.minimalistfakeweatherapp.R


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.preferences)


        /**
         * A preference value change listener that updates the preference's summary
         * to reflect its new value.
         */
        val prefChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            val stringValue = newValue.toString()

            if (preference is ListPreference) {
                val index = preference.findIndexOfValue(stringValue)
                preference.summary = if (index >= 0) preference.entries[index] else ""
            }
            return@OnPreferenceChangeListener true
        }


        val tempPref = findPreference(getString(R.string.PREF_UNITS))
        tempPref.onPreferenceChangeListener = prefChangeListener

        // Immediately trigger the listener and set the summary
        val defaultUnit = getString(R.string.FAHRENHEIT)
        prefChangeListener.onPreferenceChange(
            tempPref,
            PreferenceManager.getDefaultSharedPreferences(activity)
                .getString( getString(R.string.PREF_UNITS), defaultUnit)
        )
    }

    override fun onCreatePreferences(p0: Bundle?, p1: String?) {}

}
