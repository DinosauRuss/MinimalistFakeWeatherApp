package com.example.rek.minimalistfakeweatherapp.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import java.util.*

class TypingDetector: TextWatcher {

    private val DELAY: Long = 750
    private var timer = Timer()

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
                    }
                }, DELAY
            )
        }
    }

}