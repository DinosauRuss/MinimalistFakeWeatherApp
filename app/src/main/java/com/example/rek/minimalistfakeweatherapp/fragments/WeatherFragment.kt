package com.example.rek.minimalistfakeweatherapp.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rek.minimalistfakeweatherapp.FakeDataEntity

import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.views.FutureWeatherView
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.future_weather.view.*

private const val NAME = "name"
private const val TEMP = "temp"
private const val ICON_INDEX = "icon_index"
private const val FUTURE_ONE = "future_one"
private const val FUTURE_TWO = "future_two"
private const val FUTURE_THREE = "future_three"
private const val FUTURE_FOUR = "future_four"


class WeatherFragment : Fragment() {

    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param pos Fragment position in the adapter data
//         * @return A new instance of fragment WeatherFragment.
//         */
//        @JvmStatic
//        fun newInstance(position: Int) =
//            WeatherFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(CITY_POS, position)
//                }
//            }

        // Method for creating new instances of the fragment
        fun newInstance(entity: FakeDataEntity): WeatherFragment {

            // Store the movie data in a Bundle object
            val args = Bundle()
            args.putString(NAME, entity.name)
            args.putInt(TEMP, entity.temp)
            args.putInt(ICON_INDEX, entity.weatherIconIndex)
            args.putIntegerArrayList(FUTURE_ONE, entity.futureDayOne)
            args.putIntegerArrayList(FUTURE_TWO, entity.futureDayTwo)
            args.putIntegerArrayList(FUTURE_THREE, entity.futureDayThree)
            args.putIntegerArrayList(FUTURE_FOUR, entity.futureDayFour)

            // Create a new MovieFragment and set the Bundle as the arguments
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
            // Primary day data
            tvCityName.text = args.getString(NAME)
            val temp = "${args.getInt(TEMP, -1)}\u00B0"
            tvCityTemp.text = temp
            val iconIndex = args.getInt(ICON_INDEX, -1)
            val imgs = resources.obtainTypedArray(R.array.WeatherIcons)
            imgCityWeather.setImageResource(imgs.getResourceId(iconIndex, -1))
            imgs.recycle()

            // Future Days data
            val futureOne = args.getIntegerArrayList(FUTURE_ONE) ?: arrayListOf(-1, -1, -1)
            setFutureData(futureOne, futureViewPlusOne)
            val futureTwo = args.getIntegerArrayList(FUTURE_TWO) ?: arrayListOf(-1, -1, -1)
            setFutureData(futureTwo, futureViewPlusTwo)
            val futureThree = args.getIntegerArrayList(FUTURE_THREE) ?: arrayListOf(-1, -1, -1)
            setFutureData(futureThree, futureViewPlusThree)
            val futureFour = args.getIntegerArrayList(FUTURE_FOUR) ?: arrayListOf(-1, -1, -1)
            setFutureData(futureFour, futureViewPlusFour)
        }
    }

    private fun setFutureData(list: ArrayList<Int>, futureView: FutureWeatherView) {
        val imgs = resources.obtainTypedArray(R.array.WeatherIcons)

        val iconOne = list[0]
        val tempHi = list[1]
        val tempLo = list[2]
        val hiLo = "$tempHi/$tempLo"

        futureView.tvFutureDay.text = "NULL"
        futureView.imgFutureWeatherIcon.setImageResource(imgs.getResourceId(iconOne, -1))
        futureView.tvFutureTemps.text = hiLo

        imgs.recycle()
    }


}
