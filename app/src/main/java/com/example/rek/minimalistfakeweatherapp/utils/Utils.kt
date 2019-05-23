package com.example.rek.minimalistfakeweatherapp.utils

import android.content.res.Resources

class Utils {

    companion object {
        const val TAG = "something"
        const val minTemp = 20
        const val maxTemp = 100

        fun dpToPx(dp: Int): Int {
            return (dp * Resources.getSystem().displayMetrics.density).toInt()
        }
    }

}