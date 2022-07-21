package com.example.wallpaper.shared

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager {
    private val APP_SETTINGS = "APP_SETTINGS"


    // properties
    private val SOME_STRING_VALUE = "SOME_STRING_VALUE"
    // other properties...

    private fun SharedPreferencesManager() {}

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
    }

    fun getAccessKey(context: Context): String? {
        return getSharedPreferences(context).getString(SOME_STRING_VALUE, "se7GoO6TlLv6K27P2h_0nEGn5OU7Q6HaeqY-Jkn_UfI")
    }

    fun setAccessKey(context: Context, newValue: String?) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(SOME_STRING_VALUE, newValue)
        editor.commit()
    }
    fun getLikes(context: Context): String? {
        return getSharedPreferences(context).getString("key", null)
    }

    fun setLikes(context: Context, newValue: String?) {
        val editor = getSharedPreferences(context).edit()
        editor.putString("key", newValue)
        editor.commit()
    }
    fun getHistory(context: Context): String? {
        return getSharedPreferences(context).getString("key1", null)
    }

    fun setHistory(context: Context, newValue: String?) {
        val editor = getSharedPreferences(context).edit()
        editor.putString("key1", newValue)
        editor.commit()
    }
}
