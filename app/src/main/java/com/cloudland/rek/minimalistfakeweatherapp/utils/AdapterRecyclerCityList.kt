package com.cloudland.rek.minimalistfakeweatherapp.utils

import android.content.Context
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.cloudland.rek.minimalistfakeweatherapp.R
import kotlin.collections.ArrayList

class AdapterRecyclerCityList(private val context:Context, private val listener:ItemPressListener):
    RecyclerView.Adapter<AdapterRecyclerCityList.ViewHolder>() {

    private var data = ArrayList<com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntity>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val inflato = LayoutInflater.from(context)
        val view = inflato.inflate(R.layout.list_item_city, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setViewData(data[position], position, listener)
    }

    fun setData(newData: ArrayList<com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntity>) {
        data = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val v: View): RecyclerView.ViewHolder(v) {

        private val tvName: TextView = v.findViewById(R.id.tvItemName)
        private val tvRegion: TextView = v.findViewById(R.id.tvItemRegion)
        private val tvTemp: TextView = v.findViewById(R.id.tvItemTemp)
        private val imgIcon: ImageView = v.findViewById(R.id.imgItemIcon)

        fun setViewData(entity: com.cloudland.rek.minimalistfakeweatherapp.architecture.WeatherEntity, position: Int, listener: ItemPressListener) {

            val nameRegion = entity.name.split(",")
            tvName.text = nameRegion[0]
            tvRegion.text = nameRegion[1].trim()

            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val tempKey = context.getString(R.string.PREF_UNITS)
            val defaultUnit = context.getString(R.string.FAHRENHEIT)
            if (prefs.getString(tempKey, defaultUnit) == defaultUnit) {
                val text = "${entity.temp}\u00B0"
                tvTemp.text = text
            } else {
                val text = "${Utils.convertFtoC(entity.temp)}\u00B0"
                tvTemp.text = text
            }


            if (Utils.checkForNight()) {
                tvName.setTextColor(ContextCompat.getColor(context, R.color.warm_grey) )
                tvRegion.setTextColor(ContextCompat.getColor(context, R.color.warm_grey) )
                tvTemp.setTextColor(ContextCompat.getColor(context, R.color.warm_grey) )
            }

            val imgs = context.resources.obtainTypedArray(R.array.vd_weather_icons)
            imgIcon.setImageResource( imgs.getResourceId(entity.weatherIconIndex, 0) )
            imgs.recycle()

            v.setOnLongClickListener {
                listener.onItemLongPress(position)
                return@setOnLongClickListener true
            }
        }



    }

    /*
    Provides a handle to the activity for modifying data
     */
    interface ItemPressListener {
        fun onItemLongPress(position: Int)
    }
}