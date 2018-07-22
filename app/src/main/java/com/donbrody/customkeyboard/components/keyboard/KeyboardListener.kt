package com.donbrody.customkeyboard.components.keyboard

import com.donbrody.customkeyboard.components.keyboard.controllers.KeyboardController

/**
 * Created by Don.Brody on 7/19/18.
 */
interface KeyboardListener {
    fun characterClicked(c: Char)
    fun specialKeyClicked(key: KeyboardController.SpecialKey)
}