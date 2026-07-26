package com.edyta.mathmentor

import android.graphics.Color
import android.os.Bundle
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

        val tvHeader = findViewById<TextView>(R.id.tvHeader)
        val container = findViewById<LinearLayout>(R.id.container)
        val selectedValue = intent.getStringExtra("SELECTED_VALUE")
        val type = intent.getStringExtra("TYPE") ?: "multiplication"

        if (selectedValue == getString(R.string.all)) {

            tvHeader.text = getString(R.string.full_board)

            if (type == "multiplication") {

                for (i in 0..10) {

                    val builder = StringBuilder()

                    for (j in 0..10) {
                        builder.append(String.format("%2d × %2d = %3d\n", i, j, i * j))
                    }

                    val card = createCard(builder.toString())
                    container.addView(card)
                }

            } else {

                for (i in 1..10) {

                    val builder = StringBuilder()

                    for (j in 1..10) {
                        builder.append(String.format("%3d ÷ %2d = %2d\n", i * j, i, j))
                    }

                    val card = createCard(builder.toString())
                    container.addView(card)
                }
            }

        } else {

            val number = selectedValue?.toIntOrNull()

            tvHeader.text = if (type == "multiplication") {
                getString(R.string.multiplication_table, number)
            } else {
                getString(R.string.division_table, number)
            }

            val builder = StringBuilder()

            if (type == "multiplication") {

                for (i in 0..10) {
                    builder.append(String.format("%2d × %2d = %3d\n", number, i, number!! * i))
                }

            } else {

                for (i in 1..10) {
                    builder.append(String.format("%3d ÷ %2d = %2d\n", number!! * i, number, i))
                }
            }

            val card = createCard(builder.toString())
            container.addView(card)
        }
    }

    private fun createCard(content: String): CardView {

        val card = CardView(this)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 16, 0, 16)
        card.layoutParams = params

        card.radius = 24f
        card.cardElevation = 8f
        val color = if (content.contains("×")) "#C8E6C9" else "#D1C4E9"
        card.setCardBackgroundColor(Color.parseColor(color))

        val text = TextView(this)
        text.text = content   // 🔥 NAJWAŻNIEJSZA LINIA
        text.textSize = 22f
        text.setPadding(32, 32, 32, 32)
        text.gravity = android.view.Gravity.CENTER
        text.typeface = android.graphics.Typeface.MONOSPACE

        card.addView(text)

        return card
    }
}
