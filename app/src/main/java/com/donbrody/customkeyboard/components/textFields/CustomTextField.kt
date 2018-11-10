package com.donbrody.customkeyboard.components.textFields

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.EditText
import com.donbrody.customkeyboard.components.keyboard.CustomKeyboardView
import com.donbrody.customkeyboard.components.utilities.ComponentUtils

/**
 * Created by Don.Brody on 11/10/18.
 */
class CustomTextField(context: Context): EditText(context) {
    var keyboardType: CustomKeyboardView.KeyboardType =
            CustomKeyboardView.KeyboardType.QWERTY

    companion object {
        const val DEFAULT_TEXT_SIZE = 18.0f
        const val DEFAULT_MAX_CHARS = 25
    }

    init {
        layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ComponentUtils.dpToPx(context,
                        ComponentUtils.DEFAULT_COMPONENT_HEIGHT_DP)
        )

        val pad = ComponentUtils.dpToPx(context, 15)
        setPadding(pad, pad, pad, pad)

        setTextSize(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TEXT_SIZE)
        setTextColor(Color.BLACK)
        setBackgroundColor(Color.WHITE)
        setHintTextColor(Color.LTGRAY)
        gravity = (Gravity.TOP or Gravity.START)

        ComponentUtils.configureTextField(
                this, true, DEFAULT_MAX_CHARS)
    }
}