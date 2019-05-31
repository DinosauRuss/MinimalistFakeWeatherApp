package com.example.rek.minimalistfakeweatherapp.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.architecture.WeatherEntity
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList

class AdapterRecyclerCityList(private val context:Context, private val listener:ItemPressListener):
    RecyclerView.Adapter<AdapterRecyclerCityList.ViewHolder>() {

    private var data = ArrayList<WeatherEntity>()

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

    fun setData(newData: ArrayList<WeatherEntity>) {
        data = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val v: View): RecyclerView.ViewHolder(v) {

        private val tvName: TextView = v.findViewById(R.id.tvItemName)
        private val tvRegion: TextView = v.findViewById(R.id.tvItemRegion)
        private val tvTemp: TextView = v.findViewById(R.id.tvItemTemp)
        private val imgIcon: ImageView = v.findViewById(R.id.imgItemIcon)

        fun setViewData(entity: WeatherEntity, position: Int, listener: ItemPressListener) {

            val nameRegion = entity.name.split(",")
            tvName.text = nameRegion[0]
            tvRegion.text = nameRegion[1].trim()

            val temp = "${entity.temp}\u00B0"
            tvTemp.text = temp

            if (Utils.checkForNight()) {
                tvName.setTextColor(ContextCompat.getColor(context, R.color.warm_grey) )
                tvRegion.setTextColor(ContextCompat.getColor(context, R.color.warm_grey) )
                tvTemp.setTextColor(ContextCompat.getColor(context, R.color.warm_grey) )
            }

            val imgs = context.resources.obtainTypedArray(R.array.WeatherIcons)
            imgIcon.setImageResource( imgs.getResourceId(entity.weatherIconIndex, 0) )
            imgs.recycle()

            v.setOnLongClickListener {v ->
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