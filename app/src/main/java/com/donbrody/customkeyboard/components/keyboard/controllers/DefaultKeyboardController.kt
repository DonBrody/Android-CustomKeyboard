package com.donbrody.customkeyboard.components.keyboard.controllers

import android.view.inputmethod.InputConnection

/**
 * Created by Don.Brody on 7/18/18.
 */
open class DefaultKeyboardController(inputConnection: InputConnection):
        KeyboardController(inputConnection) {

    companion object {
        // Default controller character lengths should be set as an attribute of their EditText
        private const val MAX_CHARACTERS: Int = Int.MAX_VALUE
    }

    override fun handleKeyStroke(c: Char) {
        addCharacter(c)
    }

    override fun handleKeyStroke(key: KeyboardController.SpecialKey) {
        when(key) {
            SpecialKey.DELETE -> {
                deleteNextCharacter()
            }
            SpecialKey.BACKSPACE -> {
                deletePreviousCharacter()
            }
            SpecialKey.CLEAR -> {
                clearAll()
            }
            SpecialKey.FORWARD -> {
                moveCursorForwardAction()
            }
            SpecialKey.BACK -> {
                moveCursorBackAction()
            }
            else -> {
                // If you need access to one of the SpecialKey's not listed here, override
                // this method in a child class and implement it there.
                return
            }
        }
    }

    override fun maxCharacters(): Int {
        return MAX_CHARACTERS
    }
}
