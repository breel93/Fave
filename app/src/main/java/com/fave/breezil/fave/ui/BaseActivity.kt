package com.fave.breezil.fave.ui

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity

import com.fave.breezil.fave.R


open class BaseActivity : AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null

    private var themeMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        themeMode = sharedPreferences!!.getBoolean(getString(R.string.pref_theme_key), false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setAppTheme(themeMode)

    }

    override fun onResume() {
        super.onResume()
        val selectedTheme = sharedPreferences!!.getBoolean(getString(R.string.pref_theme_key), false)
        if(themeMode != selectedTheme)
            recreate()
    }

    private fun setAppTheme(currentTheme: Boolean){
        when(currentTheme){
            true -> setTheme(R.style.DarkTheme)
            else -> setTheme(R.style.AppTheme)
        }
    }




}
