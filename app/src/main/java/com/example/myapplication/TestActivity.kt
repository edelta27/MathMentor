package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class TestActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private val tasks = mutableListOf<Pair<Int, Int>>()
    private val correctAnswers = mutableListOf<Int>()
    private val editTexts = mutableListOf<EditText>()

    private var currentPage = 0
    private val tasksPerPage = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        LocaleHelper.applyLanguage(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        progressBar = findViewById(R.id.progressBar)

        val container = findViewById<LinearLayout>(R.id.testContainer)

        generateTasks()
        showPage(container)
    }

    private fun generateTasks() {
        tasks.clear()
        correctAnswers.clear()

        for (i in 1..20) {
            val a = (0..10).random()
            val b = (0..10).random()
            tasks.add(Pair(a, b))
            correctAnswers.add(a * b)
        }
    }

    private fun showPage(container: LinearLayout) {

        container.removeAllViews()

        progressBar.progress = currentPage + 1

        val start = currentPage * tasksPerPage
        val end = start + tasksPerPage

        for (i in start until end) {

            val (a, b) = tasks[i]

            val card = LinearLayout(this)
            card.orientation = LinearLayout.HORIZONTAL
            card.setPadding(24, 24, 24, 24)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 16, 0, 16)
            card.layoutParams = params

            val colors = listOf("#C8E6C9", "#D1C4E9", "#B3E5FC", "#FFF9C4")
            card.setBackgroundColor(android.graphics.Color.parseColor(colors.random()))

            val tvTask = TextView(this)
            tvTask.text = "$a × $b = "
            tvTask.textSize = 20f

            val etAnswer = EditText(this)
            etAnswer.layoutParams = LinearLayout.LayoutParams(
                200,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            etAnswer.inputType = InputType.TYPE_CLASS_NUMBER
            etAnswer.textSize = 18f

            if (editTexts.size <= i) {
                editTexts.add(etAnswer)
            } else {
                etAnswer.setText(editTexts[i].text.toString())
                editTexts[i] = etAnswer
            }

            card.addView(tvTask)
            card.addView(etAnswer)

            container.addView(card)
        }

        val btnNext = Button(this)

        if (currentPage < 3) {
            btnNext.text = "Dalej"
        } else {
            btnNext.text = "Sprawdź"
        }

        btnNext.setOnClickListener {
            if (currentPage < 3) {
                currentPage++
                showPage(container)
            } else {
                showResults(container)
            }
        }

        container.addView(btnNext)
    }

    private fun showResults(container: LinearLayout) {

        container.removeAllViews()

        var score = 0

        for (i in 0 until 20) {

            val (a, b) = tasks[i]
            val userAnswer = editTexts[i].text.toString().toIntOrNull()

            val tv = TextView(this)
            tv.textSize = 18f
            tv.setPadding(0, 8, 0, 8)

            if (userAnswer == correctAnswers[i]) {
                tv.text = "$a × $b = $userAnswer ✔"
                tv.setTextColor(Color.GREEN)
                score++
            } else {
                tv.text = "$a × $b = ${userAnswer ?: "-"} ✘ (${correctAnswers[i]})"
                tv.setTextColor(Color.RED)
            }

            container.addView(tv)
        }

        val percentage = (score * 100) / 20

        val tvResult = TextView(this)
        tvResult.textSize = 20f
        tvResult.setPadding(0, 24, 0, 0)

        tvResult.text = when {
            percentage == 100 -> getString(R.string.result_perfect)
            percentage >= 80 -> getString(R.string.result_very_good, percentage)
            percentage >= 50 -> getString(R.string.result_good, percentage)
            else -> getString(R.string.result_needs_practice, percentage)
        }

        container.addView(tvResult)
    }
}