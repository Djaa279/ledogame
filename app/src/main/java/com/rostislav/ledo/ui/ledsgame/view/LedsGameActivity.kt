package com.rostislav.ledo.ui.ledsgame.view

import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.rostislav.ledo.R
import com.rostislav.ledo.base.BaseActivity
import com.rostislav.ledo.ui.ledsgame.logic.Game
import com.rostislav.ledo.ui.ledsgame.presentation.LedsGamePresenter
import kotlinx.android.synthetic.main.activity_main.*

class LedsGameActivity : BaseActivity<LedsGamePresenter, LedsGameTarget>(), LedsGameTarget {

    override fun getViewResourceId(): Int = R.layout.activity_main

    override fun displayBtnValue(viewId: Int, value: String) {
        findViewById<Button>(viewId)?.text = value
    }

    /*
    * List always comes already sorted
    * here we just have to display it
    * */
    override fun displayLedsColor(ledsStateList: MutableList<Game.State>) {
        for (i in 0 until ledsStateList.size) {
            when (i) {
                0 -> setLedColor(led_3, ledsStateList[i].color)
                1 -> setLedColor(led_2, ledsStateList[i].color)
                2 -> setLedColor(led_1, ledsStateList[i].color)
            }
        }
    }

    private fun setLedColor(view: View?, color: Int) {
        view?.setBackgroundColor(ContextCompat.getColor(this, color))
    }

    override fun displayCurrentGuess(value: String) {
        current_guess?.text = getString(R.string.current_guess_label, value)
    }

    override fun displayRightAnswer(value: String) {
        right_answer?.text = getString(R.string.right_answer_label, value)
    }

    override fun displayGameLostToast() {
        Snackbar.make(led_1, R.string.game_lost_msg, Snackbar.LENGTH_SHORT)
            .show()

        btn_1?.postDelayed({
            presenter.resetTheGame()
        }, 600)
    }

    override fun displayGameWonDialog() {
        Snackbar.make(led_1, R.string.game_won_msg, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.lets_go) { presenter.startNewGame() }
            .show()
    }

    fun onLetterBtnClick(view: View?) {
        view?.let {
            presenter.onLetterBtnClick(it.id)
        }
    }

}