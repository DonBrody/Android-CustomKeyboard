package com.donbrody.customkeyboard.components.utilities

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.DrawableCompat
import android.text.InputFilter
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.util.TypedValue
import android.widget.EditText


/**
 * Created by Don.Brody on 7/20/18.
 */
class ComponentUtils {
    companion object {
        const val DEFAULT_COMPONENT_HEIGHT_DP = 80

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

        fun configureTextField(field: EditText, singleLine: Boolean, maxChars: Int) {
            if (singleLine) {
                field.maxLines = 1
                field.setSingleLine(true)
            }
            field.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxChars))
        }

        fun dpToPx(context: Context, dp: Int): Int {
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                context.resources.displayMetrics)

            val density = context.resources.displayMetrics.density
            return (px / density).toInt()
        }

        fun pxToDp(context: Context, px: Int): Int {
            val dp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_PX,
                    px.toFloat(),
                    context.resources.displayMetrics)

            val density = context.resources.displayMetrics.density
            return (dp * density).toInt()
        }
    }
}