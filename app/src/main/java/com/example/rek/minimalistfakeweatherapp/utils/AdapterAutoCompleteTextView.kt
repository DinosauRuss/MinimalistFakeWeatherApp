package com.example.rek.minimalistfakeweatherapp.utils

import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Filter


class AdapterAutoCompleteTextView(context: Context, layout: Int) : ArrayAdapter<String>(context, layout) {

    private val MAX_RESULTS = 3
//    private val mData = ArrayList<String>()

    override fun getCount(): Int {
        return Math.min(MAX_RESULTS, super.getCount())
    }

//    override fun getItem(position: Int): String? {
//        return mData[position]
//    }


//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val filterResults = FilterResults()
//                if (constraint != null) {
//                    // A class that queries a web API, parses the data and returns an ArrayList<Style>
//                    val fetcher = StyleFetcher()
//                    var resultData = ArrayList<String>()
//                    try {
//                        resultData = fetcher.retrieveResults(constraint.toString())
//                    } catch (e: Exception) {
//                        Log.e("myException", e.message)
//                    }
//
//                    // Now assign the values and count to the FilterResults object
//                    filterResults.values = resultData
//                    filterResults.count = resultData.size
//                }
//                return filterResults
//            }
//
//            override fun publishResults(contraint: CharSequence, results: FilterResults?) {
//                if (results != null && results.count > 0) {
//                    notifyDataSetChanged()
//                } else {
//                    notifyDataSetInvalidated()
//                }
//            }
//        }
//    }


}