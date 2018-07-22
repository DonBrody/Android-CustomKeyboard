package com.donbrody.customkeyboard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.donbrody.customkeyboard.components.keyboard.CustomKeyboardView

/**
 * Created by Don.Brody on 7/18/18.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var keyboard: CustomKeyboardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numberField: EditText = findViewById(R.id.testNumberField)
        val numberDecimalField: EditText = findViewById(R.id.testNumberDecimalField)
        val qwertyField: EditText = findViewById(R.id.testQwertyField)

        keyboard = findViewById(R.id.customKeyboardView)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER, numberField)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, numberDecimalField)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.QWERTY, qwertyField)
    }

    override fun onBackPressed() {
        if (keyboard.isExpanded) {
            keyboard.translateLayout()
        } else {
            super.onBackPressed()
        }
    }
}