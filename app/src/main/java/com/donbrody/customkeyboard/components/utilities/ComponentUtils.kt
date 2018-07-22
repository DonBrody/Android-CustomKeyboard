package com.donbrody.customkeyboard.components.utilities

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Don.Brody on 7/20/18.
 */
class ComponentUtils {
    companion object {
        fun hideSystemKeyboard(context: Context, view: View) {
            view.windowToken?.let{
                val imm: InputMethodManager = context.getSystemService(
                        Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        fun setBackgroundTint(view: View, color: Int) {
            val drawable: Drawable = DrawableCompat.wrap(view.background)
            DrawableCompat.setTint(drawable, color)
        }
    }
}