package com.example.flas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        val cancelButton = findViewById<ImageView>(R.id.cancel_button)
        cancelButton.setOnClickListener {
            this.finish()
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        val questionText = findViewById<EditText>(R.id.questionField)
        val answerText1 = findViewById<EditText>(R.id.answerField)
        val answerText2 = findViewById<EditText>(R.id.answerField2)
        val answerText3 = findViewById<EditText>(R.id.answerField3)

        val saveButton = findViewById<ImageView>(R.id.saveButton)
        saveButton.setOnClickListener {
            val questionString = questionText.text.toString()
            val answerString1 = answerText1.text.toString()
            val answerString2 = answerText2.text.toString()
            val answerString3 = answerText3.text.toString()
            val data = Intent()
            data.putExtra("QUESTION", questionString)
            data.putExtra("ANSWER1", answerString1)
            data.putExtra("ANSWER2", answerString2)
            data.putExtra("ANSWER3", answerString3)

            if(questionString == "" || answerString1 == "" || answerString2 == "" || answerString3 == "") {
               val toast = Toast.makeText(applicationContext, "Fill in all of the blanks!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                toast.show()
            } else {
                setResult(RESULT_OK, data)
                finish()
            }
        }

        val questionToEdit = intent.getStringExtra("EQUESTION")
        val answerToEdit1 = intent.getStringExtra("EANSWER1")
        val answerToEdit2 = intent.getStringExtra("EANSWER2")
        val answerToEdit3 = intent.getStringExtra("EANSWER3")
        val questionField1 = findViewById<EditText>(R.id.questionField)
        val answerField1 = findViewById<EditText>(R.id.answerField)
        val answerField2 = findViewById<EditText>(R.id.answerField2)
        val answerField3 = findViewById<EditText>(R.id.answerField3)
        questionField1.setText(questionToEdit)
        answerField1.setText(answerToEdit1)
        answerField2.setText(answerToEdit2)
        answerField3.setText(answerToEdit3)


    }
}