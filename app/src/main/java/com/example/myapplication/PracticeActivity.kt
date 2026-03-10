package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import android.widget.ProgressBar


class PracticeActivity : AppCompatActivity() {
    private var correctResult = 0
    private var score = 0
    private var questionCount = 0
    private val totalQuestions = 10
    private val resultsList = mutableListOf<String>()

    private lateinit var tvExample: TextView
    private lateinit var etAnswer: EditText
    private lateinit var btnCheck: Button
    private lateinit var tvScore: TextView

    private lateinit var progressBar: ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practice)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = findViewById(R.id.progressBar)
        tvExample = findViewById(R.id.tvExample)
        etAnswer = findViewById(R.id.etAnswer)
        btnCheck = findViewById(R.id.btnCheck)
        tvScore = findViewById(R.id.tvScore)

        fun generateTask() {
            val number1 = (0..10).random()
            val number2 = (0..10).random()
            correctResult = number1 * number2
            tvExample.text = "$number1 × $number2 ="
            etAnswer.text.clear()

        }

        generateTask()

        btnCheck.setOnClickListener {
            val userAnswerInt = etAnswer.text.toString().toIntOrNull()
            val exampleText = tvExample.text.toString()

            if (etAnswer.text.toString().isBlank()) {
                Toast.makeText(this, getString(R.string.enter_answer), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (userAnswerInt != null && userAnswerInt == correctResult) {
                score++
                resultsList.add(
                    getString(
                        R.string.result_correct,
                        exampleText,
                        userAnswerInt
                    )
                )
                Toast.makeText(
                    this,
                    getString(R.string.good),
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                val displayedAnswer = userAnswerInt?.toString()
                    ?: getString(R.string.lack)
                resultsList.add(
                    getString(
                        R.string.result_wrong,
                        exampleText,
                        displayedAnswer,
                        correctResult
                    )
                )
                Toast.makeText(
                    this,
                    getString(R.string.result_wrong_toast, correctResult),
                    Toast.LENGTH_SHORT
                ).show()
            }

            questionCount++
            progressBar.progress = questionCount
            tvScore.text = getString(R.string.points, score, questionCount)

            if (questionCount < totalQuestions) {
                generateTask()
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("score", score)
                intent.putStringArrayListExtra("results", ArrayList(resultsList))
                startActivity(intent)

                // reset gry
                score = 0
                questionCount = 0
                progressBar.progress = 0
                resultsList.clear()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}