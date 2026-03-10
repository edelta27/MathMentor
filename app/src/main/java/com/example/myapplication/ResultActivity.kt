package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.content.Intent
import android.widget.Button

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val tvFinalScore = findViewById<TextView>(R.id.tvFinalScore)
        val tvDetails = findViewById<TextView>(R.id.tvDetails)

        val score = intent.getIntExtra("score", 0)
        val results = intent.getStringArrayListExtra("results")

        val btnPlayAgain = findViewById<Button>(R.id.btnPlayAgain)
        val btnBackToMenu = findViewById<Button>(R.id.btnBackToMenu)

        tvFinalScore.text = getString(R.string.your_result, score)

        when (score) {
            10 -> tvFinalScore.append(getString(R.string.perfekt))
            in 7..9 -> tvFinalScore.append(getString(R.string.very_good_it_will_be))
            else -> tvFinalScore.append(getString(R.string.practice_makes_perfect))
        }

        tvDetails.text = results?.joinToString("\n")

        btnPlayAgain.setOnClickListener {
            val intent = Intent(this, PracticeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        btnBackToMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}