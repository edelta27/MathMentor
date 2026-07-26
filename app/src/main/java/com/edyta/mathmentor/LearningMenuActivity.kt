package com.edyta.mathmentor

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LearningMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        LocaleHelper.applyLanguage(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_learning_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val gridM = findViewById<GridLayout>(R.id.gridLayoutM)
        val gridD = findViewById<GridLayout>(R.id.gridLayoutD)

        val itemsM = (0..10).map { it.toString() } + getString(R.string.all)

        for (item in itemsM) {

            val button = createTile(item, "multiplication")

            button.setOnClickListener {
                openLearningTable(item, "multiplication")
            }

            gridM.addView(button)
        }

        val itemsD = (1..10).map { it.toString() } + getString(R.string.all)

        for (item in itemsD) {

            val button = createTile(item, "division")

            button.setOnClickListener {
                openLearningTable(item, "division")
            }

            gridD.addView(button)
        }
    }
    private fun openLearningTable(value: String, type: String) {
        val intent = Intent(this, LearningTableActivity::class.java)
        intent.putExtra("SELECTED_VALUE", value)
        intent.putExtra("TYPE", type)
        startActivity(intent)
    }

    private fun createTile(text: String, type: String): Button {
        val button = Button(this)
        button.text = text
        button.textSize = 20f

        val params = GridLayout.LayoutParams()
        params.width = 0
        params.height = 200
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
        params.setMargins(8, 8, 8, 8)

        button.layoutParams = params

        val color = if (type == "multiplication") {
            Color.parseColor("#C8E6C9") // zielony
        } else {
            Color.parseColor("#D1C4E9") // fioletowy
        }

        button.setBackgroundColor(color)


        return button
    }
}