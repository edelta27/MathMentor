package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.content.Intent
import android.widget.ImageButton
import com.google.android.material.card.MaterialCardView
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        LocaleHelper.applyLanguage(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val cardLearning = findViewById<MaterialCardView>(R.id.cardLearning)
        val cardPractice = findViewById<MaterialCardView>(R.id.cardPractice)
        val cardTest = findViewById<MaterialCardView>(R.id.cardTest)

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val lang = prefs.getString("lang", "pl") ?: "pl"

        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)

        resources.updateConfiguration(config, resources.displayMetrics)

        cardLearning.setOnClickListener {
            startActivity(Intent(this, LearningMenuActivity::class.java))
        }

        cardPractice.setOnClickListener {
            startActivity(Intent(this, PracticeActivity::class.java))
        }

        cardTest.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSettings = findViewById<ImageButton>(R.id.btnSettings)

        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }



    }
}