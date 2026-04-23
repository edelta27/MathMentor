package com.example.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val spinner = findViewById<Spinner>(R.id.spinnerLanguage)
        val btnApply = findViewById<Button>(R.id.btnApply)

        val languages = arrayOf("Polski", "English")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val savedLang = prefs.getString("lang", "pl")

        spinner.setSelection(if (savedLang == "en") 1 else 0)

        var selectedLang = savedLang

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedLang = if (position == 0) "pl" else "en"
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnApply.setOnClickListener {
            if (selectedLang != savedLang) {
                showConfirmDialog(selectedLang!!)
            }
        }
    }

    private fun showConfirmDialog(langCode: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.change_language))
            .setMessage(getString(R.string.confirm_language_change))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                applyLanguage(langCode)
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }

    private fun applyLanguage(langCode: String) {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        prefs.edit().putString("lang", langCode).apply()

        setLocale(langCode)

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)

        resources.updateConfiguration(config, resources.displayMetrics)
    }
}