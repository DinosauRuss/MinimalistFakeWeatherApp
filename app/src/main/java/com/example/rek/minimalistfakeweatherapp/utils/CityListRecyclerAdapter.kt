package com.example.rek.minimalistfakeweatherapp.utils

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.architecture.FakeDataEntity

class CityListRecyclerAdapter(val context: Context): RecyclerView.Adapter<CityListRecyclerAdapter.ViewHolder>() {

    private var data = ArrayList<FakeDataEntity>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val inflato = LayoutInflater.from(context)
        val view = inflato.inflate(R.layout.list_item_city, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setViewData(data[position])
    }


    fun setData(newData: ArrayList<FakeDataEntity>) {
        data = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v) {

        private val tvName: TextView = v.findViewById(R.id.tvItemName)
        private val tvTemp: TextView = v.findViewById(R.id.tvItemTemp)
        private val imgIcon: ImageView = v.findViewById(R.id.imgItemIcon)

        fun setViewData(entity: FakeDataEntity) {
            tvName.text = entity.name

            val temp = "${entity.temp}\u00B0"
            tvTemp.text = temp

            val imgs = context.resources.obtainTypedArray(R.array.WeatherIcons)
            imgIcon.setImageResource( imgs.getResourceId(entity.weatherIconIndex, 0) )
            imgs.recycle()
        }
    }
}