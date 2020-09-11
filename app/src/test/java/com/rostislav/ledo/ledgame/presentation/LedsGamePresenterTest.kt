package com.rostislav.ledo.ledgame.presentation

import com.rostislav.ledo.PresenterTestCase
import com.rostislav.ledo.ui.ledsgame.presentation.LedsGamePresenter
import com.rostislav.ledo.ui.ledsgame.view.LedsGameTarget
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner
import com.nhaarman.mockito_kotlin.*
import com.rostislav.ledo.R

@RunWith(MockitoJUnitRunner::class)
class LedsGamePresenterTest : PresenterTestCase<LedsGamePresenter, LedsGameTarget>() {

    override lateinit var targetMock: LedsGameTarget
    override val targetClazz: Class<LedsGameTarget> = LedsGameTarget::class.java

    @InjectMocks
    override lateinit var presenter: LedsGamePresenter

    @Test
    fun `starts new game on create`() {
        presenter.onCreate()
        presenter.startNewGame()
    }

    @Test
    fun `assigns buttons random order on create`() {
        assignsNewOrder { presenter.onCreate() }
    }

    @Test
    fun `displays right answer after new game created`() {
        displaysRightAnswer { presenter.startNewGame() }
    }

    @Test
    fun `displays current guess after new game created`() {
        displaysCurrentGuess { presenter.startNewGame() }
    }

    @Test
    fun `displays leds color after new game created`() {
        displaysLedsColor { presenter.startNewGame() }
    }

    @Test
    fun `displays new state after btn 1 click`() {
        presenter.onCreate()
        displaysLedsColor { presenter.onLetterBtnClick(R.id.btn_1) }
    }

    @Test
    fun `displays new state after btn 2 click`() {
        presenter.onCreate()
        displaysLedsColor { presenter.onLetterBtnClick(R.id.btn_2) }
    }

    @Test
    fun `displays new state after btn 3 click`() {
        presenter.onCreate()
        displaysLedsColor { presenter.onLetterBtnClick(R.id.btn_3) }
    }

    @Test
    fun `displays snackbar after game lose`() {
        presenter.onCreate()
        presenter.onGameLost()
        verify(targetMock).displayGameLostToast()
    }

    @Test
    fun `resets the game after lose`() {
        presenter.onCreate()
        presenter.onGameLost()
        verify(targetMock).displayGameLostToast()
        presenter.resetTheGame()
    }

    @Test
    fun `displays snackbar after game won`() {
        presenter.onCreate()
        presenter.onGameWon()
        verify(targetMock).displayGameWonDialog()
    }

    @Test
    fun `game loses after 3 attempts`() {
        presenter.onCreate()
        presenter.onLetterBtnClick(R.id.btn_3)
        presenter.onLetterBtnClick(R.id.btn_3)
        presenter.onLetterBtnClick(R.id.btn_3)
        verify(targetMock).displayGameLostToast()
    }

    private fun displaysRightAnswer(callFunction: () -> Unit) {
        callFunction()
        verify(targetMock).displayRightAnswer(any())
    }

    private fun displaysLedsColor(callFunction: () -> Unit) {
        callFunction()
        verify(targetMock).displayRightAnswer(any())
    }

    private fun displaysCurrentGuess(callFunction: () -> Unit) {
        callFunction()
        verify(targetMock).displayCurrentGuess(any())
    }

    private fun assignsNewOrder(callFunction: () -> Unit) {
        callFunction()
        verify(targetMock, times(3)).displayBtnValue(any(), any())
    }


}