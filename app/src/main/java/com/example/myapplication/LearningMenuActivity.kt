package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LearningMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_learning_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)

// Liczby 0–10 + "Cała tabliczka"
        val items = (0..10).map { it.toString() } + getString(R.string.all)

        for (item in items) {

            val button = Button(this)
            button.text = item
            button.textSize = 20f

            val params = GridLayout.LayoutParams()
            params.width = 0
            params.height = 200
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            params.setMargins(8, 8, 8, 8)

            button.layoutParams = params

            button.setOnClickListener {
                openLearningTable(item)
            }

            gridLayout.addView(button)
        }
    }
    private fun openLearningTable(value: String) {

        val intent = Intent(this, LearningTableActivity::class.java)
        intent.putExtra("SELECTED_VALUE", value)
        startActivity(intent)
    }
}