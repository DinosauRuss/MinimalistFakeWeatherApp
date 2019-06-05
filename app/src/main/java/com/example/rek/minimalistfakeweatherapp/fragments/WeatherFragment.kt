package com.example.rek.minimalistfakeweatherapp.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rek.minimalistfakeweatherapp.architecture.WeatherEntityFake
import com.example.rek.minimalistfakeweatherapp.R
import com.example.rek.minimalistfakeweatherapp.architecture.WeatherEntity
import com.example.rek.minimalistfakeweatherapp.architecture.WeatherFutureDay
import com.example.rek.minimalistfakeweatherapp.utils.Utils
import com.example.rek.minimalistfakeweatherapp.views.ViewFutureWeather
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.fragment_weather_future_views.*
import kotlinx.android.synthetic.main.fragment_weather_primary_views.*
import kotlinx.android.synthetic.main.future_weather.view.*
import java.util.*


class WeatherFragment : Fragment() {

    private var created: Boolean = false
    private var visible: Boolean = false
    private var weatherIconIndex: Int = 0

    companion object {
        private const val JSON_ENTITY = "json_entity"

        // Method for creating new instances of the fragment
        fun newInstance(entity: WeatherEntity): WeatherFragment {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Retrieve and display the fake weather data from the Bundle
        val args = arguments
        if (args != null) {
            val weatherEntity = Gson().fromJson<WeatherEntity>(
                args.getString(JSON_ENTITY), WeatherEntityFake::class.java)
            setBgColor(weatherEntity)

            this.weatherIconIndex = weatherEntity.weatherIconIndex

            val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
            val defaultUnit = getString(R.string.FAHRENHEIT)
            val tempUnit = prefs.getString(getString(R.string.PREF_UNITS), defaultUnit) ?: defaultUnit

            // Primary day data
            bindPrimaryData(weatherEntity, tempUnit)

            // Future days data
            bindFutureData(futureViewPlusOne, weatherEntity.futureDayOne, tempUnit)
            bindFutureData(futureViewPlusTwo, weatherEntity.futureDayTwo, tempUnit)
            bindFutureData(futureViewPlusThree, weatherEntity.futureDayThree, tempUnit)
            bindFutureData(futureViewPlusFour, weatherEntity.futureDayFour, tempUnit)

            created = true
            checkSafeToAnimate()
        }
    }

    private fun setBgColor(entity: WeatherEntity) {
        val colors = resources.obtainTypedArray(R.array.weather_colors)
        fragContainerWeather.setBackgroundColor( colors.getColor(entity.weatherIconIndex, colors.length()-1) )
        colors.recycle()
    }

    private fun bindPrimaryData(entity: WeatherEntity, tempUnit: String) {
        val unitF = getString(R.string.FAHRENHEIT)

        tvCityName.text = entity.name
        tvCityTemp.text = if (tempUnit.equals(unitF)) {
            "${entity.temp}\u00B0"
        } else {
            "${Utils.convertFtoC(entity.temp)}\u00B0"
        }
        tvDegreeSymbol.text = tempUnit

        val imgs = resources.obtainTypedArray(R.array.avd_weather_icons)
        val descriptions = resources.obtainTypedArray(R.array.weather_icon_accessibility_desriptions)
        imgCityWeather.setImageResource(imgs.getResourceId(entity.weatherIconIndex, -1))
        imgCityWeather.contentDescription = descriptions.getString(weatherIconIndex)
        imgs.recycle()
        descriptions.recycle()
    }

    private fun bindFutureData(view: ViewFutureWeather, entity: WeatherFutureDay, tempUnit: String) {
        val todayInt = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val imgs = resources.obtainTypedArray(R.array.vd_weather_icons)
        val descriptions = resources.obtainTypedArray(R.array.weather_icon_accessibility_desriptions)

        view.tvFutureDay.text = Utils.dayTextFromInt( (todayInt + entity.daysAhead) )
        view.imgFutureWeatherIcon.setImageResource(imgs.getResourceId(entity.iconIndex, -1))
        view.imgFutureWeatherIcon.contentDescription = descriptions.getString(entity.iconIndex)
        imgs.recycle()
        descriptions.recycle()

        val unitF = getString(R.string.FAHRENHEIT)
        val futureTemps = if (tempUnit.equals(unitF)) {
            "${entity.tempHi}/${entity.tempLo}"
        } else {
            "${Utils.convertFtoC(entity.tempHi)}/${Utils.convertFtoC(entity.tempLo)}"
        }
        view.tvFutureTemps.text = futureTemps
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser) {
            visible = true
            checkSafeToAnimate()
        } else {
            visible = false
            if (created) {
                val d = getCorrectDrawableId()
                imgCityWeather.setImageResource(d)
            }
        }
    }

    override fun onDestroyView() {
        created = false
        super.onDestroyView()
    }

    private fun checkSafeToAnimate() {
        if (visible && created) {
            val drawable = imgCityWeather.drawable
            if (drawable is AnimatedVectorDrawableCompat) {
                drawable.start()

            }
        }
    }

    private fun getCorrectDrawableId(): Int {
        val avdArray = resources.obtainTypedArray(R.array.avd_weather_icons)
        val drawable = avdArray.getResourceId(weatherIconIndex, -1)
        avdArray.recycle()

        return drawable
    }

}
