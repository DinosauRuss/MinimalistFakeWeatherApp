package com.example.rek.minimalistfakeweatherapp.utils

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.TextWatcher
import com.example.rek.minimalistfakeweatherapp.db.CityViewModel

class TypingDetector(context: Context): TextWatcher {

    private val letterThreshold: Int = 3

    private val vModelCity =
        ViewModelProviders.of(context as FragmentActivity).get(CityViewModel::class.java)

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        val len = s?.length ?: return
        if (len >= letterThreshold) {
            vModelCity.allowNewData()
        } else {
            vModelCity.doNotAllowNewData()
        }
    }

}


