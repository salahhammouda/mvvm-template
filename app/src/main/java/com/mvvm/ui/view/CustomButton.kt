package com.mvvm.ui.view

import android.content.Context
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent

import androidx.appcompat.widget.AppCompatButton

import com.mvvm.global.utils.CLICK_OPACITY

/**
 * Created on 2/2/18.
 */

class CustomButton : AppCompatButton {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    override fun onTouchEvent(event: MotionEvent): Boolean {
        val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        val x = event.x
        val y = event.y
        if (rectF.contains(x, y) && (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) && isEnabled) {
            alpha = CLICK_OPACITY
        } else {
            alpha = 1f
        }
        return super.onTouchEvent(event)
    }
}

