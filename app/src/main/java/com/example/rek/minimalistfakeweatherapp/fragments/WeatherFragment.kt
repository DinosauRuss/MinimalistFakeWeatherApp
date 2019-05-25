package com.example.rek.minimalistfakeweatherapp.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rek.minimalistfakeweatherapp.architecture.EntityFakeData

import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.views.ViewFutureWeather
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.future_weather.view.*

class WeatherFragment : Fragment() {

    companion object {
        private const val JSON_ENTITY = "json_entity"

        // Method for creating new instances of the fragment
        fun newInstance(entity: EntityFakeData): WeatherFragment {
            // Store the fake weather data in a Bundle object

            val args = Bundle()
            val jsonEntity = Gson().toJson(entity)
            args.putString(JSON_ENTITY, jsonEntity)

            // Create a new WeatherFragment and set the Bundle as the arguments
            // to be retrieved and displayed when the view is created
            val fragment = WeatherFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            pos = it.getInt(CITY_POS)
//        }

//        pos = arguments?.getInt(CITY_POS, DEFAULT_POS) ?: DEFAULT_POS

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // TODO set symbol from sharedPref
        tvDegreeSymbol.text = getString(R.string.farenheight)

        // Retrieve and display the fake weather data from the Bundle
        val args = arguments
        if (args != null) {
            val jsonEntity = Gson().fromJson<EntityFakeData>(args.getString(JSON_ENTITY), EntityFakeData::class.java)
            val imgs = resources.obtainTypedArray(R.array.WeatherIcons)

            // Primary day data
            tvCityName.text = jsonEntity.name
            val temp = "${jsonEntity.temp}\u00B0"
            tvCityTemp.text = temp
            imgCityWeather.setImageResource(imgs.getResourceId(jsonEntity.weatherIconIndex, -1))
            imgs.recycle()

            // Future Days
            bindFutureData(futureViewPlusOne, jsonEntity.futureDayOne)
            bindFutureData(futureViewPlusTwo, jsonEntity.futureDayTwo)
            bindFutureData(futureViewPlusThree, jsonEntity.futureDayThree)
            bindFutureData(futureViewPlusFour, jsonEntity.futureDayFour)
        }
    }

    private fun bindFutureData(view: ViewFutureWeather, day: EntityFakeData.FutureDayWeather) {
        val imgs = resources.obtainTypedArray(R.array.WeatherIcons)

        view.tvFutureDay.text = "NULL"
        view.imgFutureWeatherIcon.setImageResource( imgs.getResourceId(day.iconIndex, -1) )
        val futureTemps = "${day.tempHi}/${day.tempLo}"
        view.tvFutureTemps.text = futureTemps

        imgs.recycle()
    }

}
