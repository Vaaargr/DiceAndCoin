package sapegin.anton.diceandcoin.controllers

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferencesController(application: Application) {

    private val preference: SharedPreferences

    init {
        preference = application.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    fun getInt(key: String): Int{
        return preference.getInt(key, 1)
    }

    fun getBoolean(key: String): Boolean{
        return preference.getBoolean(key, false)
    }

    fun getString(key: String): String?{
        return preference.getString(key, null)
    }

    fun saveInt(key: String, value: Int){
        preference.edit().putInt(key, value).apply()
    }

    fun saveBoolean(key: String, value: Boolean){
        preference.edit().putBoolean(key,value).apply()
    }

    fun saveString(key: String, value: String){
        preference.edit().putString(key, value).apply()
    }
}