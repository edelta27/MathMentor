package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class LanguageSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_language_selection)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnPolish = findViewById<Button>(R.id.btnPolish)
        val btnEnglish = findViewById<Button>(R.id.btnEnglish)

        btnPolish.setOnClickListener {
            setLocale("pl")
        }

        btnEnglish.setOnClickListener {
            setLocale("en")
        }
        val prefs = getSharedPreferences("Settings", MODE_PRIVATE)
        val savedLanguage = prefs.getString("language", null)

        if (savedLanguage != null) {
            setLocale(savedLanguage)
            return
        }
    }
    private fun setLocale(languageCode: String) {

        val prefs = getSharedPreferences("Settings", MODE_PRIVATE)
        prefs.edit().putString("language", languageCode).apply()

        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)

        resources.updateConfiguration(config, resources.displayMetrics)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}