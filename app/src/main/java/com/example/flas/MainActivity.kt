package com.example.flas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)
        flashcardQuestion.setOnClickListener {
                flashcardQuestion.visibility = View.INVISIBLE
                flashcardAnswer.visibility = View.VISIBLE
            }
        flashcardAnswer.setOnClickListener {
            flashcardAnswer.visibility = View.INVISIBLE
            flashcardQuestion.visibility = View.VISIBLE
        }

        val answerChoice1 = findViewById<TextView>(R.id.answer1)
        val answerChoice2 = findViewById<TextView>(R.id.answer2)
        val answerChoice3 = findViewById<TextView>(R.id.answer3)
        answerChoice1.setOnClickListener {
            answerChoice1.setBackgroundColor(resources.getColor(R.color.red))
            answerChoice3.setBackgroundColor(resources.getColor(R.color.green))
        }
        answerChoice2.setOnClickListener {
            answerChoice2.setBackgroundColor(resources.getColor(R.color.red))
            answerChoice3.setBackgroundColor(resources.getColor(R.color.green))
        }
        answerChoice3.setOnClickListener {
            answerChoice3.setBackgroundColor(resources.getColor(R.color.green))
        }
        var isShowingAnswers = true
        val toggleChoice = findViewById<ImageView>(R.id.toggle_choices_visibility)
        toggleChoice.setOnClickListener {
            if(isShowingAnswers) {
                toggleChoice.setImageResource(R.drawable.eye_off)
                answerChoice1.visibility=View.INVISIBLE
                answerChoice2.visibility=View.INVISIBLE
                answerChoice3.visibility=View.INVISIBLE
                isShowingAnswers = false
            } else {
                toggleChoice.setImageResource(R.drawable.eye_on)
                answerChoice1.visibility=View.VISIBLE
                answerChoice2.visibility=View.VISIBLE
                answerChoice3.visibility=View.VISIBLE
                isShowingAnswers = true
            }

        }

        }
}