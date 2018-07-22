package com.donbrody.customkeyboard.components.keyboard.controllers

import android.view.inputmethod.InputConnection

/**
 * Created by Don.Brody on 7/20/18.
 */
class NumberDecimalKeyboardController(inputConnection: InputConnection):
    DefaultKeyboardController(inputConnection) {

    override fun handleKeyStroke(c: Char) {
        if (c == '.') {
            // decimal numbers can only have one decimal point
            if (!inputText().contains('.')) {
                addCharacter(c)
            }
        } else {
            addCharacter(c)
        }
    }
}