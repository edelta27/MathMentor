package com.example.myapplication

import android.content.Context
import java.util.Locale

object LocaleHelper {

    fun applyLanguage(context: Context) {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val lang = prefs.getString("lang", "pl") ?: "pl"

        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)

        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}