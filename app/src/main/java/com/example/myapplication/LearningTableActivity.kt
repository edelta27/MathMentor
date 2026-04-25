package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LearningTableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        LocaleHelper.applyLanguage(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_learning_table)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val selectedValue = intent.getStringExtra("SELECTED_VALUE")

        val tvHeader = findViewById<TextView>(R.id.tvHeader)
        val container = findViewById<LinearLayout>(R.id.container)

        if (selectedValue == getString(R.string.all)) {

            tvHeader.text = getString(R.string.full_board)

            for (i in 0..10) {

                val builder = StringBuilder()

                for (j in 0..10) {
                    builder.append("$i × $j = ${i * j}\n")
                }

                val card = createCard(builder.toString())
                container.addView(card)
            }

        } else {

            val number = selectedValue?.toIntOrNull()
            tvHeader.text = getString(R.string.multiplication_table, number)

            val builder = StringBuilder()

            for (i in 0..10) {
                builder.append("$number × $i = ${number!! * i}\n")
            }

            val card = createCard(builder.toString())
            container.addView(card)
        }
    }
    private fun createCard(textContent: String): CardView {

        val card = CardView(this)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 16, 0, 16)
        card.layoutParams = params

        card.radius = 24f
        card.cardElevation = 8f
        card.setCardBackgroundColor(Color.parseColor("#C8E6C9"))

        val text = TextView(this)
        text.text = textContent
        text.textSize = 18f
        text.setPadding(32, 32, 32, 32)
        text.gravity = Gravity.CENTER

        card.addView(text)

        return card
    }
}