package com.example.rek.minimalistfakeweatherapp.views

import android.content.Context
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.rek.minimalistfakeweatherapp.R


class ViewFutureWeather : LinearLayoutCompat {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    init {
        orientation = LinearLayout.VERTICAL

        val inflato: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflato.inflate(R.layout.future_weather, this, true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth = Math.round(context.resources.getDimension(R.dimen.future_view_width))
        val resolvedWidth = View.resolveSize(desiredWidth, widthMeasureSpec)

        setMeasuredDimension(resolvedWidth, measuredHeight)
    }

}