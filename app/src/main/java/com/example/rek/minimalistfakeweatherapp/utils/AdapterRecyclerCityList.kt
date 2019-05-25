package com.example.rek.minimalistfakeweatherapp.utils

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.architecture.EntityFakeData

class AdapterRecyclerCityList(val context: Context, val listener: ItemPressListener): RecyclerView.Adapter<AdapterRecyclerCityList.ViewHolder>() {

    private var data = ArrayList<EntityFakeData>()

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


    fun setData(newData: ArrayList<EntityFakeData>) {
        data = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val v: View): RecyclerView.ViewHolder(v) {

        private val tvName: TextView = v.findViewById(R.id.tvItemName)
        private val tvTemp: TextView = v.findViewById(R.id.tvItemTemp)
        private val imgIcon: ImageView = v.findViewById(R.id.imgItemIcon)

        fun setViewData(entity: EntityFakeData, position: Int, listener: ItemPressListener) {
            tvName.text = entity.name

            val temp = "${entity.temp}\u00B0"
            tvTemp.text = temp

            val imgs = context.resources.obtainTypedArray(R.array.WeatherIcons)
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