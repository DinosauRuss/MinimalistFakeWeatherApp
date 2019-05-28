package com.example.rek.minimalistfakeweatherapp.utils

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.rek.minimalistfakeweatherapp.db.City
import com.example.rek.minimalistfakeweatherapp.db.CityViewModel
import java.util.*

class TypingDetector(context: Context, private val adapter: AdapterAutoCompleteTextView): TextWatcher {

    private val DELAY: Long = 750
    private var timer = Timer()

    private val vModel =
        ViewModelProviders.of(context as FragmentActivity).get(CityViewModel::class.java)

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        timer.cancel()
    }

    override fun afterTextChanged(s: Editable?) {
        val len = s?.length ?: return
        timer = Timer()
        if (len >= 3) {
            timer.schedule(
                object : TimerTask() {
                    override fun run() {
                        Log.d(Utils.TAG, "typing stopped")

                        val text = s.toString()
                        vModel.insert( City("Russell", "CA") )

                        vModel.insert( City("$s add","CA") )

                        val namesList = vModel.getAll()
                        Log.d(Utils.TAG, "detector: $namesList")

//                        adapter.clear()
//                        adapter.addAll(namesList)
//                        adapter.notifyDataSetChanged()
                    }
                }, DELAY
            )
        }
    }

}