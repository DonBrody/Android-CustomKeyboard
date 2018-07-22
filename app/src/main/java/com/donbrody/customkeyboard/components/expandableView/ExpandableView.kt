package com.donbrody.customkeyboard.components.expandableView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.donbrody.customkeyboard.components.ResizeableRelativeLayout

/**
 * Created by Don.Brody on 7/18/18.
 */
abstract class ExpandableView(
        context: Context, attr: AttributeSet) :
        ResizeableRelativeLayout(context, attr) {

    private var state: ExpandableState? = null
    private val stateListeners = ArrayList<ExpandableStateListener>()

    val isExpanded: Boolean
        get() = state === ExpandableState.EXPANDED

    init {
        state = ExpandableState.EXPANDED // view is expanded when initially created
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        translateLayout() // collapse view after initial inflation
    }

    fun registerListener(listener: ExpandableStateListener) {
        stateListeners.add(listener)
    }

    fun translateLayout() {
        // Ignore calls that occur during animation (prevents issues from wood-pecker'ing)
        if (state !== ExpandableState.EXPANDING && state !== ExpandableState.COLLAPSING) {
            val pixels = 1_000.toDp
            val millis : Long = (pixels / 2.0f).toLong() // translates layout 2px per millisecond
            val deltaY: Float
            when (state) {
                ExpandableState.EXPANDED -> {
                    updateState(ExpandableState.COLLAPSING)
                    deltaY = pixels.toFloat() // pushes layout down 1,000 device pixels
                    animate().translationY(deltaY).setDuration(millis).withEndAction {
                        updateState(ExpandableState.COLLAPSED)
                        visibility = View.INVISIBLE
                    }.start()
                }
                ExpandableState.COLLAPSED -> {
                    updateState(ExpandableState.EXPANDING)
                    visibility = View.VISIBLE
                    deltaY = 0.0f // pulls layout back to its original position=
                    animate().translationY(deltaY).setDuration(millis).withEndAction {
                        updateState(ExpandableState.EXPANDED)
                    }.start()
                }
                else -> return
            }
        }
    }

    private fun updateState(nextState: ExpandableState) {
        state = nextState
        for (listener in stateListeners) {
            listener.onStateChange(nextState)
        }
    }

    abstract override fun configureSelf()
}
