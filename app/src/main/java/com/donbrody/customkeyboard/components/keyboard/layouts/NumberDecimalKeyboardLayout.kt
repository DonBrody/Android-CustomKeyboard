package com.donbrody.customkeyboard.components.keyboard.layouts

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.donbrody.customkeyboard.components.keyboard.controllers.KeyboardController

/**
 * Created by Don.Brody on 7/20/18.
 */
class NumberDecimalKeyboardLayout(context: Context, controller: KeyboardController?) :
        KeyboardLayout(context, controller) {

    constructor(context: Context): this(context, null)

    override fun createRows(): List<LinearLayout> {
        val columnWidth = 0.20f
        textSize = 22.0f

        val rowOne = ArrayList<View>()
        rowOne.add(createButton("1", columnWidth, '1'))
        rowOne.add(createButton("2", columnWidth, '2'))
        rowOne.add(createButton("3", columnWidth, '3'))
        rowOne.add(createButton("⌫", columnWidth, KeyboardController.SpecialKey.BACKSPACE))

        val rowTwo = ArrayList<View>()
        rowTwo.add(createButton("4", columnWidth, '4'))
        rowTwo.add(createButton("5", columnWidth, '5'))
        rowTwo.add(createButton("6", columnWidth, '6'))
        if (hasNextFocus) {
            rowTwo.add(createButton("Next", columnWidth, KeyboardController.SpecialKey.NEXT))
        } else {
            rowTwo.add(createButton("Done", columnWidth, KeyboardController.SpecialKey.DONE))
        }

        val rowThree = ArrayList<View>()
        rowThree.add(createButton("7", columnWidth, '7'))
        rowThree.add(createButton("8", columnWidth, '8'))
        rowThree.add(createButton("9", columnWidth, '9'))
        rowThree.add(createButton(".", columnWidth, '.'))

        val rowFour = ArrayList<View>()
        rowFour.add(createButton("⇦", columnWidth, KeyboardController.SpecialKey.BACK))
        rowFour.add(createButton("0", columnWidth, '0'))
        rowFour.add(createButton("⇨", columnWidth, KeyboardController.SpecialKey.FORWARD))
        rowFour.add(createButton("Clear", columnWidth, KeyboardController.SpecialKey.CLEAR))

        val rows = ArrayList<LinearLayout>()
        rows.add(createRow(rowOne))
        rows.add(createRow(rowTwo))
        rows.add(createRow(rowThree))
        rows.add(createRow(rowFour))

        return rows
    }
}