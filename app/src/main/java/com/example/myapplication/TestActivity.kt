package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TestActivity : AppCompatActivity() {
    private val resultIcons = mutableListOf<TextView>()
    private val correctAnswerViews = mutableListOf<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val container = findViewById<LinearLayout>(R.id.testContainer)

        val correctAnswers = mutableListOf<Int>()
        val editTexts = mutableListOf<EditText>()

// Generujemy 20 przykładów
        for (i in 1..20) {

            val a = (0..10).random()
            val b = (0..10).random()

            correctAnswers.add(a * b)

            val rowLayout = LinearLayout(this)
            rowLayout.orientation = LinearLayout.HORIZONTAL
            rowLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            rowLayout.setPadding(0, 16, 0, 16)

            val tvTask = TextView(this)
            tvTask.text = "$a x $b = "
            tvTask.textSize = 18f

            val etAnswer = EditText(this)
            etAnswer.layoutParams = LinearLayout.LayoutParams(
                200,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            etAnswer.inputType = android.text.InputType.TYPE_CLASS_NUMBER

            val tvResultIcon = TextView(this)
            tvResultIcon.textSize = 18f
            tvResultIcon.setPadding(16, 0, 16, 0)

            val tvCorrectAnswer = TextView(this)
            tvCorrectAnswer.textSize = 16f

            rowLayout.addView(tvTask)
            rowLayout.addView(etAnswer)
            rowLayout.addView(tvResultIcon)
            rowLayout.addView(tvCorrectAnswer)

            container.addView(rowLayout)

            editTexts.add(etAnswer)
            resultIcons.add(tvResultIcon)
            correctAnswerViews.add(tvCorrectAnswer)
        }

        val btnCheck = Button(this)
        btnCheck.text = getString(R.string.check)
        btnCheck.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val tvResult = TextView(this)
        tvResult.textSize = 18f
        tvResult.setPadding(0, 24, 0, 0)

        container.addView(btnCheck)
        container.addView(tvResult)

        btnCheck.setOnClickListener {

            var score = 0

            for (i in 0 until 20) {

                val userAnswer = editTexts[i].text.toString().toIntOrNull()


                if (userAnswer == correctAnswers[i]) {
                    score++
                    resultIcons[i].text = "✔"
                    resultIcons[i].setTextColor(android.graphics.Color.GREEN)
                    correctAnswerViews[i].text = ""
                } else {
                    resultIcons[i].text = "✘"
                    resultIcons[i].setTextColor(android.graphics.Color.RED)
                    correctAnswerViews[i].text = " ${correctAnswers[i]}"
                }
            }

            val percentage = (score * 100) / 20
            when {
                percentage == 100 -> tvResult.text = getString(R.string.result_perfect)
                percentage >= 80 -> tvResult.text = getString(R.string.result_very_good, percentage)
                percentage >= 50 -> tvResult.text = getString(R.string.result_good, percentage)
                else -> tvResult.text = getString(R.string.result_needs_practice, percentage)
            }

        }
    }
}