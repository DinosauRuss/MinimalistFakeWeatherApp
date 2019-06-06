package com.cloudland.rek.minimalistfakeweatherapp.utils

import android.content.Context
import android.widget.ArrayAdapter


class AdapterAutoCompleteTextView(context: Context, layout: Int) : ArrayAdapter<String>(context, layout) {

    private val MAX_RESULTS = 3

    override fun getCount(): Int {
        return Math.min(MAX_RESULTS, super.getCount())
    }




}