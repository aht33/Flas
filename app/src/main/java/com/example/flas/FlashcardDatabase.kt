package com.example.flas

import android.content.Context
import androidx.room.Room

class FlashcardDatabase internal constructor(context: Context) {

    private val db: AppDatabase

    fun initFirstCard() {
        if (db.flashcardDao().getAll().isEmpty()) {
            insertCard(Flashcard("Who was the president of the United States during 2016?", "Barack Obama", "Thomas the Train", "Benjamin Franklin"))
        }
    }

    fun getAllCards(): List<Flashcard> {
        return db.flashcardDao().getAll()
    }

    fun insertCard(flashcard: Flashcard) {
        db.flashcardDao().insertAll(flashcard)
    }

    fun deleteCard(flashcardQuestion: String) {
        val allCards = db.flashcardDao().getAll()
        for (card in allCards) {
            if (card.question == flashcardQuestion) {
                db.flashcardDao().delete(card)
            }
        }
    }

    fun updateCard(flashcard: Flashcard) {
        db.flashcardDao().update(flashcard)
    }

    fun deleteAll() {
        for (flashcard in db.flashcardDao().getAll()) {
            db.flashcardDao().delete(flashcard)
        }
    }

    init {
        db = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "flashcard-database"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}
