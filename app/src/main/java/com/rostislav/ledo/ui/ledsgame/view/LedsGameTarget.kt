package com.rostislav.ledo.ui.ledsgame.view

import androidx.lifecycle.LifecycleOwner
import com.rostislav.ledo.ui.ledsgame.logic.Game

interface LedsGameTarget: LifecycleOwner {

    fun displayBtnValue(viewId: Int, value: String)
    fun displayLedsColor(ledsStateList: MutableList<Game.State>)
    fun displayCurrentGuess(value: String)
    fun displayRightAnswer(value: String)

    fun displayGameLostToast()
    fun displayGameWonDialog()

}