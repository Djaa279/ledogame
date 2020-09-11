package com.rostislav.ledo.ui.ledsgame.logic

import androidx.lifecycle.MutableLiveData
import com.rostislav.ledo.R
import kotlin.random.Random

class Game constructor(
    private val onGameFinishCallBack: OnGameFinishCallBack,
    private val onLedStateChangeListener: OnLedStateChangeListener
    ) {

    private val headLed: Led

    init {
        val thirdLed = Led(getRandomLetter(), null)
        val secondLed = Led(getRandomLetter(), thirdLed)
        headLed = Led(getRandomLetter(), secondLed)
    }

    private fun getRandomLetter(): Letter {
        return when(Random.nextInt(0, 3)) {
            0 -> Letter.A
            1 -> Letter.B
            else -> Letter.C
        }
    }

    fun getCurrentGuessAsString(): String {
        return headLed.getCurrentGuessAsString()
    }

    fun getRightAnswerAsString(): String {
        return headLed.getRightAnswerAsString()
    }

    fun resetTheGame() {
        headLed.reset()
        onLedStateChangeListener.onLedNewState(headLed)
    }

    /*
    * Invokes on btn click with correct letter
    * calls the callback to let ui update the leds
    * and notifies presenter if game was lost/won
    * */
    fun tryNewLetter(newLetter: Letter) {
        headLed.tryNewLetter(newLetter) {
            if (headLed.allGreenStates()) {
                onGameFinishCallBack.onGameWon()
            } else {
                onGameFinishCallBack.onGameLost()
            }
        }
        onLedStateChangeListener.onLedNewState(headLed)
    }

    fun getRandomSequenceOfLetters(): List<Letter> {
        val lettersList = mutableListOf(Letter.A, Letter.B, Letter.C)
        lettersList.shuffle()
        return lettersList
    }

    /*
    * To have type restriction we are making letters as Enum class
    * */
    enum class Letter {
        A,
        B,
        C
    }

    /*
    * Red -> indicates that the button pressed was wrong for this position, and does not appear in a different position.
    * Orange -> indicates that the button pressed was wrong for this position, but it DOES appear in a different position.
    * Green -> indicates that the button pressed was correct for this position.
    * Dark grey -> indicates that there is no button pressed for this position yet.
    * */
    enum class State constructor(val color: Int) {
        GREEN (R.color.colorGreen),
        AMBER (R.color.colorAmber),
        RED (R.color.colorRed),
        GRAY (R.color.colorGray)
    }
}