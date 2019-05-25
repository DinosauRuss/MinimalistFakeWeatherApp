package com.example.rek.minimalistfakeweatherapp.utils

import android.content.res.Resources

class Utils {

    companion object {
        const val TAG = "something"

        const val numOfIcons = 4
        const val minTemp = 20
        const val maxTemp = 100

        fun constrain(num:Int, min:Int=0, max:Int=110): Int {
            return Math.max( (Math.min(num, max)), min )
        }

        fun dpToPx(dp: Int): Int {
            return (dp * Resources.getSystem().displayMetrics.density).toInt()
        }
    }

}