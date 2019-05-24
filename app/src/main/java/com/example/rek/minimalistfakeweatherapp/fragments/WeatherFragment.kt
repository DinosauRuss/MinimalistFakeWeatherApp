package com.example.rek.minimalistfakeweatherapp.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.architecture.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.future_weather.view.*

private const val CITY_POS = "city_pos"

class WeatherFragment : Fragment() {

    private var pos: Int = 888

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param pos Fragment position in the adapter data
         * @return A new instance of fragment WeatherFragment.
         */
        @JvmStatic
        fun newInstance(pos: Int) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putInt(CITY_POS, pos)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            pos = it.getInt(CITY_POS)
//        }
        if (savedInstanceState != null) {
            pos = savedInstanceState.getInt(CITY_POS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val vModel = ViewModelProviders.of(activity!!).get(WeatherViewModel::class.java)
        val entity = vModel.localEntitiesArray[pos]

        // Primary day data
        tvCityName.text = entity.name
        tvCityTemp.text = entity.temp.toString()
        val imgs = resources.obtainTypedArray(R.array.WeatherIcons)
        imgCityWeather.setImageResource( imgs.getResourceId(entity.weatherIconIndex, -1) )


        // TODO set symbol from sharedPref
        tvDegreeSymbol.text = getString(R.string.farenheight)


        // Future days data
        val futureDays = arrayOf(
            futureViewPlusOne,
            futureViewPlusTwo,
            futureViewPlusThree,
            futureViewPlusFour)
        val entityFutureDays = arrayOf(
            entity.futureDayOne,
            entity.futureDayTwo,
            entity.futureDayThree,
            entity.futureDayFour)

        futureDays.forEachIndexed { i, day ->
            val localEntity = entityFutureDays[i]

//        futureViewPlusOne.tvFutureDay.text =
            day.imgFutureWeatherIcon.setImageResource(
                imgs.getResourceId(localEntity.futureWeatherIcon, -1)
            )
            val hiLo = "${localEntity.tempHigh}/${localEntity.tempLow}"
            day.tvFutureTemps.text = hiLo
        }

        imgs.recycle()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CITY_POS, this.pos )
        super.onSaveInstanceState(outState)
    }

    fun setPos(position: Int) {
        this.pos = position
    }

}
