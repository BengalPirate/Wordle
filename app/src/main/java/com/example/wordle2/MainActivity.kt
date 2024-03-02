package com.example.wordle2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var wordToGuess: String
    private lateinit var editTextGuess: EditText
    private lateinit var buttonSubmit: Button
    private lateinit var buttonReset: Button
    private lateinit var textViewGuesses: TextView
    private var guessCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        editTextGuess = findViewById(R.id.editTextGuess)
        buttonSubmit = findViewById(R.id.buttonSubmit)
        buttonReset = findViewById(R.id.buttonReset)
        textViewGuesses = findViewById(R.id.textViewGuesses)

        buttonSubmit.setOnClickListener {
            if (guessCount < 3) {
                val guess = editTextGuess.text.toString().uppercase()
                val result = checkGuess(guess)
                textViewGuesses.append("\n$guess - $result")
                editTextGuess.text.clear()
                guessCount++

                if (guessCount == 3) {
                    buttonSubmit.isEnabled = false
                    textViewGuesses.append("\n\nThe correct word was: $wordToGuess")
                    buttonReset.visibility = Button.VISIBLE
                }
            } else {
                Toast.makeText(this, "You've exceeded your number of guesses!", Toast.LENGTH_SHORT).show()
            }
        }

        buttonReset.setOnClickListener {
            resetGame()
        }
    }

    private fun checkGuess(guess: String): String {
        var result = ""
        for (i in guess.indices) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            } else if (guess[i] in wordToGuess) {
                result += "+"
            } else {
                result += "X"
            }
        }
        return result
    }

    private fun resetGame() {
        wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        textViewGuesses.text = ""
        editTextGuess.text.clear()
        guessCount = 0
        buttonSubmit.isEnabled = true
        buttonReset.visibility = Button.INVISIBLE
    }
}
