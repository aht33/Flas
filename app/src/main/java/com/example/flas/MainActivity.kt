package com.example.flas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
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

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->

            val data: Intent? = result.data

            if (data != null) {
                val questionString = data.getStringExtra("QUESTION")
                val answerString1 = data.getStringExtra("ANSWER1")
                val answerString2 = data.getStringExtra("ANSWER2")
                val answerString3 = data.getStringExtra("ANSWER3")

                flashcardQuestion.text = questionString
                answerChoice1.text = answerString1
                answerChoice2.text = answerString2
                answerChoice3.text = answerString3


                Log.i("Alex:Main Activity", "question: $questionString")
                Log.i("Alex:Main Activity", "question $answerString1")

                Snackbar.make(findViewById(R.id.flashcard_question), "Card successfully created",
                    Snackbar.LENGTH_SHORT).show()
            } else {
                Log.i("Alex:Main Activity", "Returned null data from AddCardActivity")
            }
        }

        val addFlashcardButton = findViewById<ImageView>(R.id.add_question_button)
        addFlashcardButton.setOnClickListener {
            val add = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(add)
        }

        val editFlashcardButton = findViewById<ImageView>(R.id.edit_button)
        editFlashcardButton.setOnClickListener {
            val editQuestionString1 = flashcardQuestion.text.toString()
            val answerString1 = answerChoice1.text.toString()
            val answerString2 = answerChoice2.text.toString()
            val answerString3 = answerChoice3.text.toString()
            Intent(this, AddCardActivity::class.java).also {
                it.putExtra("EQUESTION", editQuestionString1)
                it.putExtra("EANSWER1", answerString1)
                it.putExtra("EANSWER2", answerString2)
                it.putExtra("EANSWER3", answerString3)
                startActivity(it)
            }
        }
        }
}