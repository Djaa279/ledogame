package com.rostislav.ledo.ui.ledsgame.logic


/*
*
* */

class Led constructor(private val rightAnswer: Game.Letter, var nextLed: Led?) {

    private var guessedLetter: Game.Letter? = null
    var ledState: Game.State = Game.State.GRAY

    /*
    * Checks pressed letter with generated correct answer
    * and updates led state according to the spec.
    * Basically it is the core of game logic
    *
    * The sequence can contain any combination, e.g. BAC, CCB, AAA.
    * - The LEDs should always represent the result of the last 3 button presses.
    *   - LED 3 will always represent the most recent button press
    *   - LED 2 the one before that
    *   - LED 1 the one before that
    *
    * - LED color definition
    *   - Red indicates that the button pressed was wrong for this position, and does not appear in a different position.
    *   - Orange indicates that the button pressed was wrong for this position, but it DOES appear in a different position.
    *   - Green indicates that the button pressed was correct for this position.
    *   - Dark grey indicates that there is no button pressed for this position yet.
    *
    * After the right answer is found, show a dialog and then generate a new answer to start a new game.
    * */
    fun tryNewLetter(newLetter: Game.Letter, onGameFinishCallBack: () -> Unit) {

        if (ledState == Game.State.GRAY) {
            guessedLetter = newLetter

            ledState = if (rightAnswer == newLetter) {
                Game.State.GREEN
            } else {
                if (containThisLetter(newLetter)) Game.State.AMBER else Game.State.RED
            }

            /*
            * (nextLed == null) means that it is the last Led
            * and the game is finished with some result
            * */
            if (nextLed == null) {
                onGameFinishCallBack.invoke()
            }

        } else {
            nextLed?.tryNewLetter(newLetter, onGameFinishCallBack)
        }

    }

    private fun containThisLetter(newLetter: Game.Letter): Boolean {
        return nextLed?.containThisLetter(newLetter) ?: false || rightAnswer == newLetter
    }

    fun getRightAnswerAsString(): String {
        return rightAnswer.toString() + (nextLed?.getRightAnswerAsString() ?: "")
    }

    fun getCurrentGuessAsString(): String {
        return (guessedLetter?.toString() ?: "") + (nextLed?.getCurrentGuessAsString() ?: "")
    }

    fun allGreenStates(): Boolean {
        return ledState == Game.State.GREEN && (nextLed?.allGreenStates() ?: true)
    }

    /*
    * Erases all guessed letters
    * and sets all states to GRAY
    * */
    fun reset() {
        guessedLetter = null
        ledState = Game.State.GRAY
        nextLed?.reset()
    }

}