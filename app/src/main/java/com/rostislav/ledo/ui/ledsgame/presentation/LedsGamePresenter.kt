package com.rostislav.ledo.ui.ledsgame.presentation

import android.view.View
import com.rostislav.ledo.R
import com.rostislav.ledo.base.BasePresenter
import com.rostislav.ledo.ui.ledsgame.logic.Game
import com.rostislav.ledo.ui.ledsgame.logic.Led
import com.rostislav.ledo.ui.ledsgame.logic.OnGameFinishCallBack
import com.rostislav.ledo.ui.ledsgame.logic.OnLedStateChangeListener
import com.rostislav.ledo.ui.ledsgame.view.LedsGameTarget
import javax.inject.Inject

class LedsGamePresenter @Inject constructor() : BasePresenter<LedsGameTarget>(), OnGameFinishCallBack, OnLedStateChangeListener {

    private var game = Game(this, this)
    private lateinit var btnsMap: HashMap<Int, Game.Letter>

    /*
    * Start new game on activity create
    * and assign random order for buttons
    * */
    override fun onCreate() {
        super.onCreate()
        startNewGame()
        assignBtnValues()
    }

    fun startNewGame() {
        game = Game(this, this)
        resetUi()
    }

    fun resetTheGame() {
        game.resetTheGame()
        resetUi()
    }

    /*
    * Display initial state of the game
    * */
    private fun resetUi() {
        target?.displayRightAnswer(game.getRightAnswerAsString())
        target?.displayCurrentGuess(game.getCurrentGuessAsString())
        target?.displayLedsColor(MutableList(3) {Game.State.GRAY})
    }

    /*
    * Displays current leds state in designed order
    * "LED 3 will always represent the most recent button press"
    * */
    private fun displayLedStates(headLed: Led) {
        val ledsStateList: MutableList<Game.State> = mutableListOf()
        var nextLed: Led? = headLed

        while (nextLed != null) {
            if (nextLed.ledState != Game.State.GRAY) {
                ledsStateList.add(nextLed.ledState)
            }
            nextLed = nextLed.nextLed
        }

        ledsStateList.reverse()

        target?.displayLedsColor(ledsStateList)
    }

    /*
    * Generates random sequence of letters
    * and uses it in the hashMap
    * */
    private fun assignBtnValues() {
        val lettersSequence = game.getRandomSequenceOfLetters()
        btnsMap = hashMapOf()

        for (i in lettersSequence.indices) {
            val buttonId: Int? = when (i) {
                0 -> R.id.btn_1
                1 -> R.id.btn_2
                2 -> R.id.btn_3
                else -> null
            }

            buttonId?.let {
                target?.displayBtnValue(it, lettersSequence[i].name)
                btnsMap[it] = lettersSequence[i]
            }
        }
    }

    /*
    * Looking for assigned letter in hashMap
    * to proceed correctly
    * */
    fun onLetterBtnClick(viewId: Int) {
        btnsMap[viewId]?.let {
            game.tryNewLetter(it)
        }
    }


    override fun onGameWon() {
        target?.displayGameWonDialog()
    }

    override fun onGameLost() {
        target?.displayGameLostToast()
    }

    override fun onLedNewState(headLed: Led) {
        target?.displayCurrentGuess(game.getCurrentGuessAsString())
        displayLedStates(headLed)
    }
}