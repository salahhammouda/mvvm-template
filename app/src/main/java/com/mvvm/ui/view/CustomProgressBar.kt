package com.mvvm.ui.view

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.mvvm.R

class CustomProgressBar : ProgressBar {

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize()
    }

    private fun initialize() {
        indeterminateDrawable
                .setColorFilter(ContextCompat.getColor(context, R.color.color_accent), PorterDuff.Mode.MULTIPLY)
    }

}