package com.example.flas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    lateinit var flashcardDatabase: FlashcardDatabase
    var allFlashcards = mutableListOf<Flashcard>()
    var currCardDisplayedIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flashcardDatabase = FlashcardDatabase(this)
        allFlashcards = flashcardDatabase.getAllCards().toMutableList()

        val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)
        val answerChoice1 = findViewById<TextView>(R.id.answer1)
        val answerChoice2 = findViewById<TextView>(R.id.answer2)
        val answerChoice3 = findViewById<TextView>(R.id.answer3)

        if (allFlashcards.size > 0) {
            flashcardQuestion.text = allFlashcards[0].question
            answerChoice1.text = allFlashcards[0].wrongAnswer1
            answerChoice2.text = allFlashcards[0].wrongAnswer2
            answerChoice3.text = allFlashcards[0].answer
        }
        flashcardQuestion.setOnClickListener {
                flashcardQuestion.visibility = View.INVISIBLE
                flashcardAnswer.visibility = View.VISIBLE
            }
        flashcardAnswer.setOnClickListener {
            flashcardAnswer.visibility = View.INVISIBLE
            flashcardQuestion.visibility = View.VISIBLE
        }

        var answerColor1 = false
        var answerColor2 = false
        var answerColor3 = false

        answerChoice1.setOnClickListener {
            if(answerColor1) {
                answerChoice1.setBackgroundColor(resources.getColor(R.color.border))
                answerColor1 = false
            } else {
                answerChoice1.setBackgroundColor(resources.getColor(R.color.red))
                answerChoice3.setBackgroundColor(resources.getColor(R.color.green))
                answerColor1 = true
            }
        }
        answerChoice2.setOnClickListener {
            if(answerColor2) {
                answerChoice2.setBackgroundColor(resources.getColor(R.color.border))
                answerColor2 = false
            } else {
                answerChoice2.setBackgroundColor(resources.getColor(R.color.red))
                answerChoice3.setBackgroundColor(resources.getColor(R.color.green))
                answerColor2 = true
            }
        }
        answerChoice3.setOnClickListener {
            if(answerColor3) {
                answerChoice3.setBackgroundColor(resources.getColor(R.color.border))
                answerColor1 = false
            } else {
                answerChoice3.setBackgroundColor(resources.getColor(R.color.green))
                answerColor3 = true
            }
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

                if(questionString != null && answerString1 != null && answerString2 != null && answerString3 != null) {
                    flashcardDatabase.insertCard(Flashcard(questionString, answerString1, answerString2, answerString3))
                    allFlashcards = flashcardDatabase.getAllCards().toMutableList()
                }

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
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
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

        var countDownTimer: CountDownTimer? = null
        countDownTimer = object : CountDownTimer(16000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                findViewById<TextView>(R.id.timer).text = "Timer: " + millisUntilFinished / 1000 + "s"
            }
            override fun onFinish() {}
        }

        fun startTimer() {
            countDownTimer?.cancel()
            countDownTimer?.start()
        }

        val nextButton = findViewById<ImageView>(R.id.next_button)
        nextButton.setOnClickListener {

            if(allFlashcards.isEmpty()) {
                //early return
                return@setOnClickListener
            }
            currCardDisplayedIndex++

            if(currCardDisplayedIndex >= allFlashcards.size) {
                currCardDisplayedIndex = 0
            }
            allFlashcards = flashcardDatabase.getAllCards().toMutableList()

            val question = allFlashcards[currCardDisplayedIndex].question
            val answer = allFlashcards[currCardDisplayedIndex].answer
            val wrongAnswer1 = allFlashcards[currCardDisplayedIndex].wrongAnswer1
            val wrongAnswer2 = allFlashcards[currCardDisplayedIndex].wrongAnswer2

            flashcardQuestion.text = question
            answerChoice1.text = wrongAnswer1
            answerChoice2.text = wrongAnswer2
            answerChoice3.text = answer

            val leftOutAnim = AnimationUtils.loadAnimation(this, R.anim.left_out)
            val rightInAnim = AnimationUtils.loadAnimation(this, R.anim.right_in)

            leftOutAnim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {

                }

                override fun onAnimationEnd(animation: Animation?) {
                    flashcardQuestion.startAnimation(rightInAnim)
                    answerChoice1.startAnimation(rightInAnim)
                    answerChoice2.startAnimation(rightInAnim)
                    answerChoice3.startAnimation(rightInAnim)
                }

                override fun onAnimationRepeat(animation: Animation?) {
                    TODO("Not yet implemented")
                }
            })
            flashcardQuestion.startAnimation(leftOutAnim)
            answerChoice1.startAnimation(leftOutAnim)
            answerChoice2.startAnimation(leftOutAnim)
            answerChoice3.startAnimation(leftOutAnim)

            startTimer()
            }



            startTimer()
        }
}