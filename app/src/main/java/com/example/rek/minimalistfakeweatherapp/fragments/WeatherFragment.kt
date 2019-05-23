package com.example.rek.minimalistfakeweatherapp.fragments


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rek.minimalistfakeweatherapp.CityWeatherEntity

import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.utils.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_weather.*

private const val CITY_POS = "city_pos"

class WeatherFragment : Fragment() {

    private var name: String? = "Random, CA"
    private var pos: Int = 0

    private lateinit var dataEntity: CityWeatherEntity

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param location Location to display fake weather data
         * @param unitF F or C
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
        arguments?.let {
            pos = it.getInt(CITY_POS)
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


        // TODO set symbol from sharedPref
        tvDegreeSymbol.text = getString(R.string.farenheight)

        tvCityName.text = vModel.citiesTempArray[pos]._name
    }

}
