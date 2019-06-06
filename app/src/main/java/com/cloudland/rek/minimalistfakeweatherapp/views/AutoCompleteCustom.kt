package com.cloudland.rek.minimalistfakeweatherapp.views

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import com.cloudland.rek.minimalistfakeweatherapp.R
import com.cloudland.rek.minimalistfakeweatherapp.db.CityViewModel
import com.cloudland.rek.minimalistfakeweatherapp.utils.TypingDetector

class AutoCompleteCustom: AutoCompleteTextView {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    private val typo = TypingDetector(context)
    private val vModel = ViewModelProviders.of(context as FragmentActivity).get(CityViewModel::class.java)

    init {
        val itemListener = AdapterView.OnItemClickListener { parent, view, position, id -> vModel.doNotAllowNewData() }
        this.onItemClickListener = itemListener

        this.addTextChangedListener(typo)
        this.typeface = ResourcesCompat.getFont(context, R.font.comfortaa_regular)
    }



}