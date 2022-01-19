package com.donbrody.customkeyboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.donbrody.customkeyboard.components.keyboard.CustomKeyboardView
import com.donbrody.customkeyboard.components.textFields.CustomTextField
import com.donbrody.customkeyboard.components.utilities.ComponentUtils

/**
 * Created by Don.Brody on 11/10/18.
 */
class AdvancedFeaturesActivity: AppCompatActivity() {
    private lateinit var keyboard: CustomKeyboardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advanced_features)

        val customFieldWrapper: LinearLayout = findViewById(R.id.customFieldWrapper)
        customFieldWrapper.addView(createCustomTextField(
                "Custom Number Keyboard",
                CustomKeyboardView.KeyboardType.NUMBER))
        customFieldWrapper.addView(createCustomTextField(
                "Custom Number Decimal Keyboard",
                CustomKeyboardView.KeyboardType.NUMBER_DECIMAL))
        customFieldWrapper.addView(createCustomTextField(
                "Custom QWERTY Keyboard",
                CustomKeyboardView.KeyboardType.QWERTY))

        keyboard = findViewById(R.id.customKeyboardView)
        keyboard.autoRegisterEditTexts(customFieldWrapper)

        val switchActivitiesButton: Button = findViewById(R.id.switchActivitiesButton)
        switchActivitiesButton.setOnClickListener {
            startActivity(Intent(
                    this@AdvancedFeaturesActivity,
                    MainActivity::class.java))
        }
    }

    private fun createCustomTextField(
            hint: String, keyboardType: CustomKeyboardView.KeyboardType): CustomTextField {
        val field = CustomTextField(applicationContext)
        val lp = LinearLayout.LayoutParams(
                ComponentUtils.dpToPx(applicationContext, 400),
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val marginBottom = applicationContext.resources.
                getDimension(R.dimen.fieldMarginBottom).toInt()
        lp.setMargins(0, 0, 0, marginBottom)
        field.layoutParams = lp

        field.hint = hint
        field.keyboardType = keyboardType

        return field
    }

    override fun onBackPressed() {
        if (keyboard.isExpanded) {
            keyboard.translateLayout()
        } else {
            super.onBackPressed()
        }
    }
}