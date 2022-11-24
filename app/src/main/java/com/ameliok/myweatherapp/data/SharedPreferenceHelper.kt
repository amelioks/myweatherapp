package com.ameliok.myweatherapp.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


class SharedPreferenceHelper(private val context: Context) {
    val SHARED_PREFS = "sharedPrefs"

    val sharedPreferences : SharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)

    var query : String
        get() = sharedPreferences.getString(KEY_QUERY, DEFAULT_CITY_QUERY).toString()
        set(value) {
            sharedPreferences.edit().putString(KEY_QUERY,value).apply()
        }

    companion object {
        const val KEY_QUERY = "pref_key_last_query"
        const val DEFAULT_CITY_QUERY: String = "Berlin"
    }

}