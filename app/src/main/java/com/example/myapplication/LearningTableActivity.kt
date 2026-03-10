package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LearningTableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
        val tvTable = findViewById<TextView>(R.id.tvTable)

        if (selectedValue == getString(R.string.all)) {

            tvHeader.text = getString(R.string.full_board)

            val builder = StringBuilder()

            for (i in 0..10) {
                for (j in 0..10) {
                    builder.append("$i x $j = ${i * j}\n")
                }
                builder.append("\n")
            }

            tvTable.text = builder.toString()

        } else {

            val number = selectedValue?.toIntOrNull()

            tvHeader.text = getString(R.string.multiplication_table, number)

            val builder = StringBuilder()

            for (i in 0..10) {
                builder.append("$number x $i = ${number!! * i}\n")
            }

            tvTable.text = builder.toString()
        }
    }
}