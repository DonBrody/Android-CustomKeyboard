package com.donbrody.customkeyboard.components

import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.support.annotation.CallSuper
import android.util.AttributeSet
import android.widget.RelativeLayout

/**
 * Created by Don.Brody on 7/18/18.
 */
abstract class ResizeableRelativeLayout(
        context: Context, attr: AttributeSet) :
        RelativeLayout(context, attr) {

    val Int.toPx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    val Int.toDp: Int
        get() = (this / Resources.getSystem().displayMetrics.density).toInt()

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        resetContent()
    }

    @CallSuper fun resetContent() {
        removeAllViews()
        // Adding a delay gives the parent activity time to handle its configuration change
        // prior to us handling ours. Otherwise we run into several issues, including the
        // screen size property of our parent window not updating prior to us accessing it
        Handler().postDelayed({ configureSelf() }, 50)
    }

    protected abstract fun configureSelf()
}
